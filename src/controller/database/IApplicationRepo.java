package controller.database;

import entity.application.Application;

import java.util.List;

/**
 * contain methods related to application for repository to implement
 */
public interface IApplicationRepo extends IUser{

    /**
     * find application using its ID
     * @param applicationId application ID
     */
    Application findApplication(String applicationId);

    /**
     * add application to student's application list and internship's list
     * @param studentId student id
     * @param app application object
     */
    void addApplication(String studentId, Application app);

    /**
     * get all application of student
     * @param studentId student id
     */
    List<Application> applicationByStudent(String studentId);



}
