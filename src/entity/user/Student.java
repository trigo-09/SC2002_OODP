package entity.user;
import entity.application.Application;
import entity.application.ApplicationStatus;

import java.util.*;

public class Student extends User {

	List<Application> applications;
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
	protected Student(String name, String id, String pass, int year, String major) {
        super(name, id, pass);
        this.year = year;
        this.major = major;
		// TODO - implement Student.Student
//		throw new UnsupportedOperationException();
	}

	public void createApp(Application app) {
        applications.add(app);
		//throw new UnsupportedOperationException();
	}

	public void withdrawApp(Application app) {// this only happen after the request is accepted by rep
       applications.remove(app);
	}

    public List<Application> getApplications() {
        return applications;
    }
    public int getNumOfApplications() {
        return applications.size();
    }


}