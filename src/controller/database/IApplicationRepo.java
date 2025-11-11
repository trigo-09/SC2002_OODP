package controller.database;

import entity.application.Application;

import java.util.List;

public interface IApplicationRepo {

    List<Application> getAllApplications();

    /**
     *
     * @param applicationId
     *
     */
    Application findApplication(String applicationId);

    /**
     *
     * @param studentId
     * @param app
     */
    void addApplication(String studentId, Application app);

    /**
     *
     * @param studentId
     * @param applicationId
     */
    void deleteApplication(String studentId, String applicationId);

    /**
     *
     * @param studentId
     */
    List<Application> applicationByStudent(String studentId);

    /**
     *
     * @param internId
     */
    List<Application> applicationForInternship(String internId);


}
