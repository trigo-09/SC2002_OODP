package controller.service;
import controller.database.IRepository;
import entity.application.Application;
import entity.application.ApplicationStatus;
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
    private static final int MAX_ACTIVE_INTERNSHIPS = 5;

    public InternshipService(IRepository repository) {
        this.repository = repository;
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

        repository.addInternship(createdBy, internship);
        return internship;
	}

	/**
	 * 
	 * @param internshipId
	 * @param visible
	 */
	public void setVisibility(String internshipId, boolean visible) {
        InternshipOpportunity internship = findInternshipById(internshipId);
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

    /**
     * Edits an existing internship opportunity.
     * @param internshipId Unique ID of the internship to edit
     * @param title New title for the internship (null if not changing)
     * @param description New description for the internship (null if not changing)
     * @param preferredMajors New preferred majors for the internship (null if not changing)
     * @param openingDate New opening date for the internship (null if not changing)
     * @param closingDate New closing date for the internship (null if not changing)
     * @param slot New number of slots for the internship (null if not changing)
     * @param level New level for the internship (null if not changing)
     * @throws ObjectNotFoundException if the internship with the given ID does not exist
     * @throws SecurityException if the internship cannot be edited due to its current status
     */
    public void editInternship(String internshipId,String title, String description, String preferredMajors, LocalDate openingDate, LocalDate closingDate, Integer slot, InternshipLevel level) throws ObjectNotFoundException {
        InternshipOpportunity internship = repository.findInternshipOpportunity(internshipId);
        if(internship == null) {throw new ObjectNotFoundException("Internship not found");}
        if(!ableToEditInternship(internship)) {throw new SecurityException("Internship can not be edited");}
        
        // null options for fields that are not being edited / left blank
        if (title != null)           internship.setTitle(title);
        if (description != null)     internship.setDescription(description);
        if (preferredMajors != null) internship.setPreferredMajors(preferredMajors);
        if (openingDate != null)     internship.setOpeningDate(openingDate);
        if (closingDate != null)     internship.setClosingDate(closingDate);
        if (slot != null)            internship.setNumOfSlots(slot);
        if (level != null)           internship.setLevel(level);
    }


    public void removeInternship(CompanyRep rep,String internshipId)  {
        boolean removed =rep.getInternships().removeIf(i->i.getId().equals(internshipId) && i.getStatus() == InternStatus.PENDING && i.getStatus() == InternStatus.FILLED );
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

    public void addPendApplicationToInternship(Application app) {
        InternshipOpportunity internshipOpportunity = findInternshipById(app.getInternshipId());
        internshipOpportunity.addPendingApplication(app);
    }

    public void addAcceptedApplicationToInternship(Application app) {
        InternshipOpportunity internshipOpportunity = findInternshipById(app.getInternshipId());
        internshipOpportunity.addApprovedapplication(app);
        if(internshipOpportunity.getNumOfFilledSlots() == internshipOpportunity.getNumOfSlots()){
            internshipOpportunity.setStatus(InternStatus.FILLED);
        }
    }

    public void removeApplicationFromInternship(Application app) {
        InternshipOpportunity internshipOpportunity = findInternshipById(app.getInternshipId());
        if(app.getStatus() == ApplicationStatus.ACCEPTED){
            internshipOpportunity.removeApprovedapplication(app);
            if(isFilled(internshipOpportunity)){
                internshipOpportunity.setStatus(InternStatus.APPROVED);
            }
        }else {
            internshipOpportunity.removePendingApplication(app);
        }
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

        /**
         * Parses a string to an InternshipLevel enum.
         * Checks for valid input.
         * @param s The string representation of the internship level
         * @param allowNull Whether null values are allowed (for edit mode)
         * @return The corresponding {@link InternshipLevel} enum
         * @throws IllegalArgumentException if the input is invalid or null
         */
        public static InternshipLevel parseLevel(String s, boolean allowNull) {
                if (s == null && allowNull) { return null; }
                if (s == null) {throw new IllegalArgumentException("Level must be Basic / Intermediate / Advanced"); }
                return switch (s.trim().toLowerCase()) {
                        case "basic" -> InternshipLevel.BASIC;
                        case "intermediate" -> InternshipLevel.INTERMEDIATE;
                        case "advanced" -> InternshipLevel.ADVANCED;
                        default -> throw new IllegalArgumentException("Level must be Basic / Intermediate / Advanced");
                };
        }

        /**
         * Validates the number of slots for an internship.
         * @param slots The number of slots to validate
         * @param allowNull Whether null values are allowed (for edit mode)
         * @throws IllegalArgumentException if the number of slots is out of range or null when not allowed
         */
        public static void checkValidSlots(Integer slots, boolean allowNull) {
                if (slots == null && allowNull) { return; } // left blank in edit mode, skip validation
                if (slots == null) {
                        throw new IllegalArgumentException("Slots must be between 1 and 10");
                }
                if (slots < 1 || slots > 10) {
                        throw new IllegalArgumentException("Slots must be between 1 and 10");
                }
        }

        /**
         * Validates the opening and closing dates for an internship.
         * @param opening Opening date of internship
         * @param closing Closing date of internship
         * @throws IllegalArgumentException if the closing date is before the opening date
         */
        public static void checkValidDates(LocalDate opening, LocalDate closing) {
                if (opening != null && closing != null && closing.isBefore(opening)) {
                        throw new IllegalArgumentException("Closing date cannot be before opening date");
                }
        }
}