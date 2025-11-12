package controller.control;

import boundary.terminal.Exit;
import boundary.terminal.Welcome;
import controller.control.user.RepController;
import controller.control.user.StaffController;
import controller.control.user.StudentController;
import controller.database.IRepository;
import controller.database.SystemDataManager;
import controller.service.ApplicationService;
import controller.service.AuthenticationService;
import controller.service.InternshipService;
import controller.service.RequestService;
import entity.user.*;
import java.util.Map;
import java.util.Scanner;
import util.exceptions.AlreadyApprovedException;
import util.exceptions.AuthenticationException;
import util.exceptions.RepNotApprovedException;
import util.exceptions.RepPendingApprovalException;
import util.io.InputHelper;

public class SystemController {

	private final IRepository repo;
	private final SystemDataManager dataManager;
    private final AuthenticationService auth;
    private final RequestService request;
    private final InternshipService internshipService;
    private final ApplicationService applicationService;

	public SystemController() {
        dataManager = new SystemDataManager();
        repo = dataManager.load();
        auth = new AuthenticationService(repo);
        request = new RequestService(repo);
        internshipService = new InternshipService(repo);
        applicationService = new ApplicationService(repo);
	}

	public void start() {
        Scanner scanner = new Scanner(System.in);
        InputHelper.init(scanner);
        while (true) {
            mainMenu();
        }
	}

	public void mainMenu() {
        Welcome.welcome(this);
	}

    public void handleLogin(String userId, String password) throws AuthenticationException {
            User user = auth.authenticate(userId,password);
            switch (user.getRole()) {
                case STUDENT -> new StudentController(auth,repo,request,(Student) user).launch(this);

                case STAFF -> new StaffController(auth,repo,request,(CareerStaff) user).launch(this); // Pass SystemController instance to enable logout functionality from StaffController

                case REP ->  new RepController(auth, repo, request, (CompanyRep) user, internshipService, applicationService).launch();
                    
            }
        }


	public void shutdown() {
        dataManager.save(repo);
        Exit.exit();
	}


    public void registerRep(String userId, String name, String Password, Map<String, String> attributes) throws AuthenticationException, IllegalArgumentException {
        /**
         * ensure the UserId is in valid format
         */
        if (!userId.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format") ;
        }

        if (repo.getApprovedReps().containsKey(userId)){
            throw new AlreadyApprovedException("Account is already registered and approved");
        }
        if (repo.getPendingReps().containsKey(userId)){
            if (repo.getPendingReps().get(userId).getStatus() == RepStatus.REJECTED){
                throw new RepNotApprovedException(userId);
            }
            else {
                throw new RepPendingApprovalException(userId);
            }
        }
        User user = UserFactory.createUser(UserRole.REP,userId,name,Password,attributes);
        request.createRegistrationRequest((CompanyRep) user);
        repo.registerCompanyRep((CompanyRep) user);
        dataManager.saveUpdate(repo);
    }

}