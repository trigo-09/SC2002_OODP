package controller.database;

import entity.internship.InternshipOpportunity;

import java.util.List;

public interface IInternshipRepo {

    List<InternshipOpportunity> getAllInternships();

    /**
     *
     * @param repId
     *
     */
    List<InternshipOpportunity> getAllInternshipsByRep(String repId);

    /**
     *
     * @param internshipId
     *
     */
    InternshipOpportunity findInternshipOpportunity(String internshipId);

    /**
     *
     * @param repId
     * @param intern
     */
    void addInternship(String repId, InternshipOpportunity intern);
}
