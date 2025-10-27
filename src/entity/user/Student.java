package entity.user;

import entity.application.Application;

import java.util.*;

public class Student extends User {

	Collection<Application> applications;
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
	protected Student(String name, String id, String pass, int year, string major) {
		// TODO - implement Student.Student
		throw new UnsupportedOperationException();
	}

	public void acceptApp() {
		// TODO - implement Student.acceptApp
		throw new UnsupportedOperationException();
	}

	public void rejectApp() {
		// TODO - implement Student.rejectApp
		throw new UnsupportedOperationException();
	}

}