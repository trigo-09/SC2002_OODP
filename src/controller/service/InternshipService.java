package controller.service;
import controller.database.IInternshipRepo;
import controller.database.IRepository;
import entity.application.Application;
import entity.application.ApplicationStatus;
import entity.internship.*;
import entity.user.CompanyRep;
import entity.user.Student;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import entity.FilterCriteria;
import util.exceptions.MaxExceedException;
import util.exceptions.ObjectNotFoundException;

/**
 * Service class for managing internship
 * Handles internship creation, deletion, edit and changes
 */
public class InternshipService {

    private final IInternshipRepo repository;
    private static final int MAX_ACTIVE_INTERNSHIPS = 5;

    /**
     * Constructor for internship service
     * @param repository system repository
     */
    public InternshipService(IRepository repository) {
        this.repository = repository;
    }

    /**
     * create new internship
    * @param title title
    * @param description description of internship
    * @param level level of internship
    * @param preferredMajors preferred major
    * @param closingDate closing date
    * @param openingDate opening date
    * @param numOfSlots number of approved application slot
    * @param createdBy rep's ID
    * @param companyName company name
     * @throws MaxExceedException if the company creates max number of allocated internship
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
        if(!isEligibleToCreateInternship(companyName)){
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
        return internship;
	}

    public void addInternship(InternshipOpportunity internship){
        repository.addInternship(internship.getCreatedBy(),internship);
    }

	/**
	 * changes the visibility of given internship
	 * @param internshipId internship id
	 * @param visible on or off visibility
     * @throws SecurityException if the internship yet to be approved by career staff
	 */
	public void setVisibility(String internshipId, boolean visible) {
        InternshipOpportunity internship = findInternshipById(internshipId);
        if(internship.getStatus() == InternStatus.PENDING){
            throw new SecurityException("Internship visibility cannot be changed while in pending status");
        }
        internship.setVisibility(visible);
	}

	/**
	 * checks whether internship can be edited
	 * @param internship internship object
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

    /**
     * deleted internship
     * @param repId rep's ID
     * @param internshipId internship's ID
     * @throws ObjectNotFoundException if internship can not be found
     * @throws SecurityException if internship is not in pending state or wrong person accessing it
     */
    public void removeInternship(String repId,String internshipId) throws ObjectNotFoundException {
        CompanyRep rep = (CompanyRep) repository.findUser(repId);
        InternshipOpportunity internship = findInternshipById(internshipId);
        if (internship == null) {
            throw new ObjectNotFoundException("Internship not found");
        }
        if(!internship.getCompanyName().equals(rep.getCompanyName())) {
            throw new SecurityException("Internship can not be removed");
        }
        if(internship.getStatus() == InternStatus.PENDING || internship.getStatus() == InternStatus.REJECTED) {
            rep.removeInternship(internship);
        }
        boolean removed =rep.getInternships().removeIf(i->i.getId().equals(internshipId) && i.getStatus() == InternStatus.PENDING && i.getStatus() == InternStatus.FILLED );
        if(!removed){
            throw new SecurityException("Unable to remove internship");
        }
    }

    /**
     * help to get internship after applying filter
     * @param internList list of internship
     * @param filter filter
     * @return list of internship
     */
	public List<InternshipOpportunity> getFilteredInternship(List<InternshipOpportunity> internList, FilterCriteria filter) {
        return internList.stream()
                .filter(internship -> filter.getStatus() == null || filter.getStatus() == internship.getStatus())
                .filter(internship -> filter.getPreferredMajor() == null ||filter.getPreferredMajor().equals(internship.getPreferredMajors()))
                .filter(internship->filter.getClosingDate() == null || internship.getClosingDate().isBefore(filter.getClosingDate()) || filter.getClosingDate().equals(internship.getClosingDate()))
                .filter(internship->filter.getCompanyName() == null ||filter.getCompanyName().equals(internship.getCompanyName()))
                .sorted(Comparator.comparing(InternshipOpportunity::getTitle))
                .toList();
    }

    public List<InternshipOpportunity> getPendingInternships(List<InternshipOpportunity> internList){
        return internList.stream()
                .filter(internship -> internship.getStatus() == InternStatus.PENDING)
                .sorted(Comparator.comparing(InternshipOpportunity::getTitle))
                .toList();
    }

    public List<InternshipOpportunity> getApprovedInternships(List<InternshipOpportunity> internList){
        return internList.stream()
                .filter(internship -> internship.getStatus() == InternStatus.APPROVED)
                .sorted(Comparator.comparing(InternshipOpportunity::getTitle))
                .toList();
    }

    /**
     * get all the internship of the company
     * @param companyName company name
     * @return list of internship
     */
    public List<InternshipOpportunity> getInternshipsByCompany(String companyName){
        return repository.getInternshipsByCompany(companyName);
	}

    // Get the list of eligible internships that a student can apply for

    /**
     * get all the internships that students can view
     * @param s student
     * @return list of internship
     */
    public List<InternshipOpportunity> getEligibleInternships(Student s) {
        List<InternshipOpportunity> allInternships = repository.getAllInternships();
        return allInternships.stream().filter(internship -> isEligible(s, internship.getId())).collect(Collectors.toList());
    }

	/**
    * finding internship via ID
    * @param internshipId internship ID
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

        return preferredMajors.equalsIgnoreCase("any") || preferredMajors.equalsIgnoreCase(s.getMajor());

                // Passed all checks
    }

    /**
     * ensure that the company does not create more than allocated internship slots
     * @param companyName company name
     * @return boolean
     */
    public boolean isEligibleToCreateInternship(String companyName) {
        return getInternshipsByCompany(companyName).stream().filter(i->i.getStatus() != InternStatus.REJECTED).count() < MAX_ACTIVE_INTERNSHIPS;
    }

    /**
     * add application to internship pending list
     * @param app application object
     */
    public void addPendApplicationToInternship(Application app) {
        InternshipOpportunity internshipOpportunity = findInternshipById(app.getInternshipId());
        internshipOpportunity.addPendingApplication(app);
    }

    /**
     * move approved application to approved list and change internship status to filled if application slot filled up
     * @param app application object
     */
    public void addAcceptedApplicationToInternship(Application app) {
        InternshipOpportunity internshipOpportunity = findInternshipById(app.getInternshipId());
        internshipOpportunity.addApprovedapplication(app);
        if(internshipOpportunity.getNumOfFilledSlots() == internshipOpportunity.getNumOfSlots()){
            internshipOpportunity.setStatus(InternStatus.FILLED);
        }
    }

    /**
     * remove application from internship
     * if internship status was filled, change to approved
     * @param app application object
     */
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
    * checks if internship is FILLED
    * @param internshipId internship ID
    */
	public boolean isFilled(String internshipId) {
                return findInternshipById(internshipId).getStatus() == InternStatus.FILLED;
	}

    /**
     * checks if internship is FILLED
     * @param internship internship Object
     */
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