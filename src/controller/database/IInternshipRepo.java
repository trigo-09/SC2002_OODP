package controller.database;

import entity.internship.InternshipOpportunity;

import java.util.List;

/**
 * contain methods related to internship for repository to implement
 */
public interface IInternshipRepo extends IUser {

    /**
     * get all internships
     * @return
     */
    List<InternshipOpportunity> getAllInternships();

    /**
     * get all internship of the company
     * @param companyName
     */
    List<InternshipOpportunity> getInternshipsByCompany(String companyName);

    /**
     * find internship by its ID
     * @param internshipId
     */
    InternshipOpportunity findInternshipOpportunity(String internshipId);

    /**
     * add internship to rep's internship list
     * @param repId
     * @param intern
     */
    void addInternship(String repId, InternshipOpportunity intern);
}
