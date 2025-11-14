package controller.database;

import entity.user.*;
import java.util.*;

/**
 * contain methods related to users for repository to implement
 */
public interface IRepository extends IApplicationRepo,IRequestRepo,IInternshipRepo {

    /**
     * get the hash map of all approved rep
     * @return map of approved reps
     */
    Map<String, CompanyRep> getApprovedReps();

    /**
     * get hash map of all career staff
     * @return map of career staff
     */
    Map<String, CareerStaff> getCareerStaff();

    /**
     * get hash map of all pending rep
     * @return map of pending reps
     */
    Map<String, CompanyRep> getPendingReps();

    /**
     * get hash map of all students
     * @return map of students
     */
    Map<String, Student> getStudents();

    /**
     * add career Staff to hash map
     * @param staff career staff
     */
    void addCareerStaff(CareerStaff staff);

    /**
     * add student to hash map
     * @param student student object
     */
    void addStudent(Student student);

    /**
     * move company rep from pending to approved
     * @param repId rep id
     */
    void approveCompanyRep(String repId);

    /**
     * add company rep to pending map
     * @param rep company rep object
     */
    void registerCompanyRep(CompanyRep rep);
}