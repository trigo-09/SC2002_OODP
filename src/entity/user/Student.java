package entity.user;

import entity.application.Application;
import entity.application.ApplicationStatus;

import java.util.*;

public class Student extends User {

	ArrayList<Application> applications;
	private int year;
	private String major;

	public int getYear() {
		return this.year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getMajor() {
		return this.major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	/**
	 * 
	 * @param name
	 * @param id
	 * @param pass
	 * @param year
	 * @param major
	 */
	public Student(String name, String id, String pass, int year, String major) {
        super(name, id, pass);
		// TODO - implement Student.Student
		this.year = year;
        this.major = major;
	}

    //Added function to add application to student profile
    public void addApp(Application app) {
        // adds application only if it doesn't already exist
        if (!applications.contains(app)) {
            applications.add(app);
        }
    }

	public void acceptApp(Application app) {
		// TODO - implement Student.acceptApp
        if (app.status == ApplicationStatus.APPROVED) {
            app.changeApplicationStatus(ApplicationStatus.ACCEPTED);
            }
        }

	}

	public void rejectApp() {
		// TODO - implement Student.rejectApp
        if (app.status == ApplicationStatus.APPROVED) {
            app.changeApplicationStatus(ApplicationStatus.REJECTED);
        }
	}

}
