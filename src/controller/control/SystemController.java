package controller.control;

import controller.control.user.RepController;
import controller.control.user.StaffController;
import controller.control.user.StudentController;
import controller.database.IResposistory;
import controller.database.SystemDataManager;

public class SystemController {

	private final IResposistory repo;
	private SystemDataManager dataManager;
	private StudentController studentController;
	private StaffController staffController;
	private RepController repController;
	private boolean isRunning = false;

	public SystemController() {
		// TODO - implement SystemController.SystemController
		throw new UnsupportedOperationException();
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