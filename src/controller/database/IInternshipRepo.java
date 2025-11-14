package controller.database;

import entity.internship.InternshipOpportunity;

import java.util.List;

/**
 * contain methods related to internship for repository to implement
 */
public interface IInternshipRepo extends IUser {

    /**
     * get all internships
     * @return list of internship
     */
    List<InternshipOpportunity> getAllInternships();

    /**
     * get all internship of the company
     * @param companyName company name
     */
    List<InternshipOpportunity> getInternshipsByCompany(String companyName);

    /**
     * find internship by its ID
     * @param internshipId internship Id
     */
    InternshipOpportunity findInternshipOpportunity(String internshipId);

    /**
     * add internship to rep's internship list
     * @param repId rep id
     * @param intern internship object
     */
    void addInternship(String repId, InternshipOpportunity intern);
}
