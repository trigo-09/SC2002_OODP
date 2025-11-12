package controller.service;
import controller.database.IRepository;
import entity.application.Application;
import entity.internship.*;
import entity.user.CompanyRep;
import entity.user.Student;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import util.FilterCriteria;
import util.exceptions.MaxExceedException;
import util.exceptions.ObjectNotFoundException;

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

    public InternshipOpportunity proposeInternship (String title,
                                                   String description,
                                                   InternshipLevel level,
                                                   String preferredMajors,
                                                   LocalDate openingDate,
                                                   LocalDate closingDate,
                                                   int numOfSlots,
                                                   String createdBy,
                                                   String companyName) throws MaxExceedException {
        if(!isEligibleToCreateInternship(createdBy)){
            throw new MaxExceedException("Max number of Internship created");
        }
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
	 * @param internship
	 *
	 */
	private boolean ableToEditInternship(InternshipOpportunity internship){
        return internship.getStatus() == InternStatus.PENDING;
    }

    public void editInternship(String internshipId,String title, String description, String preferredMajors, LocalDate openingDate, LocalDate closingDate,int slot, InternshipLevel level) throws ObjectNotFoundException {
        InternshipOpportunity internship = repository.findInternshipOpportunity(internshipId);
        if(internship == null){throw new ObjectNotFoundException("Internship not found");}
        if(!ableToEditInternship(internship)){throw new SecurityException("Internship can not be edited");}
        internship.setTitle(title);
        internship.setDescription(description);
        internship.setPreferredMajors(preferredMajors);
        internship.setOpeningDate(openingDate);
        internship.setClosingDate(closingDate);
        internship.setNumOfSlots(slot);
        internship.setLevel(level);
    }


    public void removeInternship(CompanyRep rep,String internshipId)  {
        boolean removed =rep.getInternships().removeIf(i->i.getId().equals(internshipId) && i.getStatus() != InternStatus.PENDING && i.getStatus() != InternStatus.FILLED );
        if(!removed){
            throw new SecurityException("Unable to remove internship");
        }
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
        List<InternshipOpportunity> allInternships = repository.getAllInternships();
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
	 * Checks if a student is eligible to apply for an internship.
	 * @param s The student applying
	 * @param internshipId The internship to apply for
	 */
	public boolean isEligible(Student s, String internshipId) {
                InternshipOpportunity i = findInternshipById(internshipId);
                if (!i.getVisibility()) {return false;}

                // Students year must match the internship level
                if (!i.getLevel().isEligible(s.getYear())) {return false;}

                // Students cannot apply if there is no slots left
                if(isFilled(i)){return false;}

                // Student cannot apply if the internship is past closing date
                if(i.getClosingDate().isBefore(LocalDate.now())){return false;}

                // Major must match (unless internship accepts "Any")
                String preferredMajors = i.getPreferredMajors();
                if (!Objects.equals(preferredMajors, "Any") && !Objects.equals(preferredMajors, s.getMajor())) {
                return false;
                }

                // Passed all checks
                return true;
	}

    public boolean isEligibleToCreateInternship(String repId) {
        CompanyRep rep = (CompanyRep) repository.findUser(repId);
        return repository.getInternshipsByCompany(rep.getCompanyName()).stream().filter(internshipOpportunity -> internshipOpportunity.getStatus() != InternStatus.REJECTED).count() < MAX_ACTIVE_INTERNSHIPS;
    }

    public void addApplicationToInternship(Application app) {
        InternshipOpportunity internshipOpportunity = repository.findInternshipOpportunity(app.getInternshipId());
        internshipOpportunity.addPendingApplication(app);
    }

    public void removeApplicationFromInternship(Application app) {
        InternshipOpportunity internshipOpportunity = repository.findInternshipOpportunity(app.getInternshipId());
        internshipOpportunity.addPendingApplication(app);
    }




	/**
        *
        * @param internshipId
        */
	public boolean isFilled(String internshipId) {
                return findInternshipById(internshipId).getStatus() == InternStatus.FILLED;
	}
    public boolean isFilled(InternshipOpportunity internship) {
        return internship.getStatus() == InternStatus.FILLED;
    }

}