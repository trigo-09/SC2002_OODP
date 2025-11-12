package controller.database;

import entity.user.*;
import java.util.*;

/**
 * contain methods related to users for repository to implement
 */
public interface IRepository extends IApplicationRepo,IRequestRepo,IInternshipRepo {

    /**
     * get the hash map of all approved rep
     * @return
     */
    Map<String, CompanyRep> getApprovedReps();

    /**
     * get hash map of all careerstaff
     * @return
     */
    Map<String, CareerStaff> getCareerStaff();

    /**
     * get hash map of all pending rep
     * @return
     */
    Map<String, CompanyRep> getPendingReps();

    /**
     * get hash map of all students
     * @return
     */
    Map<String, Student> getStudents();

    /**
     * find user using their ID and return them
     * @param userId
     */
    User findUser(String userId);

    /**
     * add career Staff to hash map
     * @param staff
     */
    void addCareerStaff(CareerStaff staff);

    /**
     * add student to hash map
     * @param student
     */
    void addStudent(Student student);

    /**
     * move company rep from pending to approved
     * @param repId
     */
    void approveCompanyRep(String repId);

    /**
     * add company rep to pending map
     * @param rep
     */
    void registerCompanyRep(CompanyRep rep);
}