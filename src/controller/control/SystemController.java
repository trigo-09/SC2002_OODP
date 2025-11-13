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
import util.exceptions.*;
import util.io.InputHelper;

/**
 * Serve as pseudo singleton
 * coordinate the whole system
 */
public class SystemController {

	private final IRepository repo;
	private final SystemDataManager dataManager;
    private final AuthenticationService auth;
    private final RequestService request;
    private final InternshipService internshipService;
    private final ApplicationService applicationService;

    /**
     * Constructor for system controller
     * all services and repository are created
     */
	public SystemController() {
        dataManager = new SystemDataManager();
        repo = dataManager.load();
        auth = new AuthenticationService(repo);
        request = new RequestService(repo);
        internshipService = new InternshipService(repo);
        applicationService = new ApplicationService(repo);
	}

    /**
     * starts the system
     */
	public void start() {
        Scanner scanner = new Scanner(System.in);
        InputHelper.init(scanner);
        while (true) {
            mainMenu();
        }
	}

    /**
     * calls Welcome class which contain welcome page
     */
	public void mainMenu() {
        Welcome.welcome(this);
	}

    /**
     * calls authentication service to validate login
     * create and launch relevant user's controller
     * @param userId user's ID
     * @param password user's password
     * @throws AuthenticationException if there are issue during authentication
     */
    public void handleLogin(String userId, String password) throws AuthenticationException {
        User user = auth.authenticate(userId,password);
            switch (user.getRole()) {
                case STUDENT ->  new StudentController(auth, repo, request, internshipService, applicationService, (Student) user).launch(this);

                case STAFF -> new StaffController(auth, repo, request, internshipService, applicationService, (CareerStaff) user).launch(this);

                case REP ->   new RepController(auth, repo, request, (CompanyRep) user, internshipService, applicationService).launch(this);
            }
    }

    /**
     * saves repository and call exit page
     */
	public void shutdown() {
        dataManager.save(repo);
        Exit.exit();
	}

    /**
     * creates new company rep object and registration request for account approval
     * @param userId company rep email
     * @param name rep's name
     * @param Password rep's password
     * @param attributes rep's other attributes
     * @throws AuthenticationException if account exist or account still under processing
     * @throws IllegalArgumentException if the userId have invalid email format
     */
    public void registerRep(String userId, String name, String Password, Map<String, String> attributes) throws AuthenticationException, IllegalArgumentException {
        if (!userId.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format") ;
        }

        if (repo.getApprovedReps().containsKey(userId)){
            throw new AlreadyApprovedException("Account is already registered and approved");
        }
        if (repo.getPendingReps().containsKey(userId)){
            if (repo.getPendingReps().get(userId).getStatus() == RepStatus.PENDING) {
                throw new RepPendingApprovalException(userId);
            }
        }
        User user = UserFactory.createUser(UserRole.REP,userId,name,Password,attributes);
        request.createRegistrationRequest((CompanyRep) user);
        repo.registerCompanyRep((CompanyRep) user);
        dataManager.save(repo);
    }

}