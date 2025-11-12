package entity.user;
import entity.Displayable;
import entity.application.Application;
import entity.application.ApplicationStatus;

import java.util.*;

public class Student extends User implements Displayable {

	private final List<Application> applications;
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
        super(name, id, pass,UserRole.STUDENT);
        this.year = year;
        this.major = major;
        this.applications = new ArrayList<>();
		// TODO - implement Student.Student
//		throw new UnsupportedOperationException();
	}

	public void addApplication(Application app) {
        applications.add(app);
		//throw new UnsupportedOperationException();
	}

	public void withdrawApplication(String applicationId) {// this only happen after the request is accepted by rep
        applications.stream()
                .filter(app -> app.getApplicationId().equals(applicationId))
                .findFirst()
                .ifPresent(app -> app.changeApplicationStatus(ApplicationStatus.WITHDRAWN));
	}

    public List<Application> getApplications() {
        return applications;
    }

    public int getNumOfApplications() {
        return (int)applications.stream()
                .filter(application -> application.getStatus() != ApplicationStatus.WITHDRAWN && application.getStatus() != ApplicationStatus.REJECTED)
                .count();
    }


	@Override
	public String toString() {
		return String.format(
				"Student ID: %s%n" +
						"Name: %s%n" +
						"Year: %d%n" +
						"Major: %s%n",
				getId(),
				getUserName(),
				year,
				major
		);
	}



}