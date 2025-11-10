package controller.service;
import controller.database.IResposistory;
import entity.internship.*;
import entity.user.CompanyRep;
import entity.user.Student;
import java.time.LocalDate;
import java.util.*;
import util.FilterCriteria;

public class InternshipService {

    private final IResposistory resposistory;
    private final RequestService requestService;
    private static final int MAX_ACTIVE_INTERNSHIPS = 5;

    public InternshipService(IResposistory resposistory, RequestService requestService) {
        this.resposistory = resposistory;
        this.requestService = requestService;
    }

	/**
	 * @param title
     * @param description
     * @param level
     * @param preferredMajors
     * @param closingDate
     * @param openingDate
     * @param numOfSlots
     * @param createdBy
     * @param companyName
	 */

	public InternshipOpportunity proposeInternship(String title,
                                                   String description,
                                                   InternshipLevel level,
                                                   String preferredMajors,
                                                   LocalDate openingDate,
                                                   LocalDate closingDate,
                                                   int numOfSlots,
                                                   String createdBy,
                                                   String companyName){
        InternshipOpportunity internship = new InternBuilder()
                .title(title)
                .description(description)
                .level(level)
                .preferredMajors(preferredMajors)
                .openingDate(openingDate)
                .closingDate(closingDate)
                .numOfSlots(numOfSlots)
                .createdBy(createdBy)
                .companyName(companyName)
                .build();

        requestService.createInternshipRequest(createdBy, internship);
        resposistory.addInternship(createdBy, internship);
        return internship;
	}

	/**
	 * 
	 * @param internshipId
	 * @param visible
	 */
	public void setVisibility(String internshipId, boolean visible) {
        InternshipOpportunity internship = resposistory.findInternshipOpportunity(internshipId);
        internship.setVisibility(visible);
	}

	/**
	 * 
	 * @param id
	 * @param status
	 */
	public void updateStatus(String id, InternStatus status) {
		// TODO - implement InternshipService.updateStatus
		throw new UnsupportedOperationException();
	}

	public List<InternshipOpportunity> getFilteredInternship(FilterCriteria filter) {
        return resposistory.getAllInternships().stream()
                .filter(internship -> filter.getStatus() == null || filter.getStatus() == internship.getStatus())
                .filter(internship -> filter.getPreferredMajor().isEmpty() ||filter.getPreferredMajor().equals(internship.getPreferredMajors()))
                .filter(internship->filter.getClosingDate() == null || internship.getClosingDate().isBefore(filter.getClosingDate()))
                .sorted(Comparator.comparing(InternshipOpportunity::getTitle))
                .toList();
}

        public List<InternshipOpportunity> getInternshipsByCompany(String companyName){
        return resposistory.getInternshipsByCompany(companyName);
	}

	/**
     *
     * @param internshipId
     */
	public InternshipOpportunity findInternshipById(String internshipId) {
        return resposistory.findInternshipOpportunity(internshipId);
	}

	/**
	 * 
	 * @param s
	 * @param internshipId
	 */
	public boolean isEligible(Student s, String internshipId) {
        InternshipOpportunity i = findInternshipById(internshipId);
        if (!i.getVisibility()) {return false;}

        // Students year must match the internship level
        if (!i.getLevel().isEligible(s.getYear())) {return false;}

        // Major must match (unless internship accepts "Any")
        String preferredMajors = i.getPreferredMajors();
        if (!Objects.equals(preferredMajors, "Any") && !Objects.equals(preferredMajors, s.getMajor())) {
            return false;
        }
        // Passed all checks
        return true;
	}

    public boolean isEligible(String repId) {
        CompanyRep rep = (CompanyRep) resposistory.findUser(repId);
        return rep.getNumOfInternships() <  MAX_ACTIVE_INTERNSHIPS;
    }


	/**
     *
     * @param internshipId
     */
	public boolean isFilled(String internshipId) {
        return findInternshipById(internshipId).getStatus() == InternStatus.FILLED;
	}

}