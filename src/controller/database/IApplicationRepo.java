package controller.database;

import entity.application.Application;

import java.util.List;

/**
 * contain methods related to application for repository to implement
 */
public interface IApplicationRepo extends IUser{

    /**
     * find application using its ID
     * @param applicationId
     */
    Application findApplication(String applicationId);

    /**
     * add application to student's application list and internship's list
     * @param studentId
     * @param app
     */
    void addApplication(String studentId, Application app);

    /**
     * get all application of student
     * @param studentId
     */
    List<Application> applicationByStudent(String studentId);



}
