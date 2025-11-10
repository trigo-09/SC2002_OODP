package controller.database;

import entity.user.*;
import java.util.*;
public interface IRepository extends IApplicationRepo,IRequestRepo,IInternshipRepo {

    Map<String, CompanyRep> getApprovedReps();

    Map<String, CareerStaff> getCareerStaff();

    Map<String, CompanyRep> getPendingReps();

    Map<String, Student> getStudents();

    /**
     *
     * @param userId
     */
    User findUser(String userId);

    /**
     *
     * @param staff
     */
    void addCareerStaff(CareerStaff staff);

    /**
     *
     * @param student
     */
    void addStudent(Student student);

    /**
     *
     * @param repId
     */
    void approveCompanyRep(String repId);

    /**
     *
     * @param rep
     */
    void registerCompanyRep(CompanyRep rep);
}