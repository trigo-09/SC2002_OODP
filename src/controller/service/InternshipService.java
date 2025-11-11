package controller.service;
import controller.database.IRepository;
import entity.internship.*;
import entity.user.CompanyRep;
import entity.user.Student;
import java.time.LocalDate;
import java.util.*;
import util.FilterCriteria;

public class InternshipService {

    private final IRepository repository;
    private final RequestService requestService;
    private static final int MAX_ACTIVE_INTERNSHIPS = 5;

    public InternshipService(IRepository repository, RequestService requestService) {
        this.repository = repository;
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
        repository.addInternship(createdBy, internship);
        return internship;
	}

	/**
	 * 
	 * @param internshipId
	 * @param visible
	 */
	public void setVisibility(String internshipId, boolean visible) {
        InternshipOpportunity internship = repository.findInternshipOpportunity(internshipId);
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

	public List<InternshipOpportunity> getFilteredInternship(List<InternshipOpportunity> internList, FilterCriteria filter) {
        return internList.stream()
                .filter(internship -> filter.getStatus() == null || filter.getStatus() == internship.getStatus())
                .filter(internship -> filter.getPreferredMajor().isEmpty() ||filter.getPreferredMajor().equals(internship.getPreferredMajors()))
                .filter(internship->filter.getClosingDate() == null || internship.getClosingDate().isBefore(filter.getClosingDate()))
                .sorted(Comparator.comparing(InternshipOpportunity::getTitle))
                .toList();
    }

        public List<InternshipOpportunity> getInternshipsByCompany(String companyName){
        return repository.getInternshipsByCompany(companyName);
	}

    // Get the list of eligible internships that a student can apply for
    public List<InternshipOpportunity> getEligibleInternships(Student s) {
        List<InternshipOpportunity> allInternships = resposistory.getAllInternships();
        return allInternships.stream().filter(internship -> isEligible(s, internship.getId())).collect(Collectors.toList());
    }

	/**
     *
     * @param internshipId
     */
	public InternshipOpportunity findInternshipById(String internshipId) {
        return repository.findInternshipOpportunity(internshipId);
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
        CompanyRep rep = (CompanyRep) repository.findUser(repId);
        return rep.getNumOfInternships() <  MAX_ACTIVE_INTERNSHIPS;
    }
    public boolean isEligible(CompanyRep rep) {
        return repository.getInternshipsByCompany(rep.getCompanyName()).stream().filter(i->i.getStatus() != InternStatus.REJECTED).count() < MAX_ACTIVE_INTERNSHIPS;
    }


	/**
     *
     * @param internshipId
     */
	public boolean isFilled(String internshipId) {
        return findInternshipById(internshipId).getStatus() == InternStatus.FILLED;
	}

}