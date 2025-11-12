package entity.user;
import entity.Displayable;
import entity.application.Application;
import entity.application.ApplicationStatus;

import java.util.*;

public class Student extends User implements Displayable {

	private final List<Application> applications;
	private int year;
	private String major;

    /**
     *
     * @return year of student
     */
	public int getYear() {
		return this.year;
	}

    /**
     *
     * @param year set the year of student
     */
	public void setYear(int year) {
		this.year = year;
	}

    /**
     *
     * @return major of student
     */
	public String getMajor() {
		return this.major;
	}

    /**
     *
     * @param major set the major of student
     */
	public void setMajor(String major) {
		this.major = major;
	}

	/**
	 * constructor of student
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
	}

    /**
     *
     * @param app add new application to list
     */
	public void addApplication(Application app) {
        applications.add(app);
	}

    /**
     *
     * @return list of applications of student
     */
    public List<Application> getApplications() {
        return applications;
    }

    /**
     *
     * @return number of valid application meaning they are in pending,approved,accepted state
     */
    public int getNumOfApplications() {
        return (int)applications.stream()
                .filter(application -> application.getStatus() != ApplicationStatus.WITHDRAWN && application.getStatus() != ApplicationStatus.REJECTED)
                .count();
    }


    /**
     *
     * @return toString for printing
     */
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