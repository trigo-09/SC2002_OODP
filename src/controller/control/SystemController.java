package controller.control;

import boundary.terminal.Exit;
import boundary.terminal.Welcome;
import controller.control.user.RepController;
import controller.control.user.StaffController;
import controller.control.user.StudentController;
import controller.database.IResposistory;
import controller.database.SystemDataManager;
import controller.service.AuthenticationService;
import controller.service.RequestService;
import entity.user.*;
import util.exceptions.AlreadyApprovedException;
import util.exceptions.AuthenticationException;
import util.exceptions.RepNotApprovedException;
import java.util.Map;

public class SystemController {

	private final IResposistory repo;
	private final SystemDataManager dataManager;
    private final AuthenticationService auth;
    private final RequestService request;

	public SystemController() {
        dataManager = new SystemDataManager();
        repo = dataManager.load();
        auth = new AuthenticationService(repo);
        request = new RequestService(repo);
	}

	public void start() {
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
                case STUDENT -> new StudentController(auth,repo,request,(Student) user).launch();

                case STAFF -> new StaffController(auth,repo,request,(CareerStaff) user).launch();

                case REP -> new RepController(auth,repo,request,(CompanyRep) user).launch();
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
        if (!userId.matches("^[A-Za-z0-9+_.-]+@(.+)$}")) {
            throw new IllegalArgumentException("Invalid email format") ;
        }

        if (repo.getApprovedReps().containsKey(userId)){
            throw new AlreadyApprovedException("Account is already registered and approved");
        }
        if (repo.getPendingReps().containsKey(userId)){
            throw new RepNotApprovedException("Registration pending approval. Please wait for staff confirmation");
        }
        User user = UserFactory.createUser(UserRole.REP,userId,name,Password,attributes);
        request.createRegistrationRequest((CompanyRep) user);
        repo.registerCompanyRep((CompanyRep) user);
        dataManager.saveUpdate(repo);
    }

}