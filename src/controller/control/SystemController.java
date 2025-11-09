package controller.control;

import controller.control.user.RepController;
import controller.control.user.StaffController;
import controller.control.user.StudentController;
import controller.database.IResposistory;
import controller.database.SystemDataManager;

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
		// TODO - implement SystemController.start
		throw new UnsupportedOperationException();
	}

	public void mainMenu() {
		// TODO - implement SystemController.mainMenu
		throw new UnsupportedOperationException();
	}

	public void shutdown() {
		// TODO - implement SystemController.shutdown
		throw new UnsupportedOperationException();
	}

}