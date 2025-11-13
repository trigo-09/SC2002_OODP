package controller.control.user;

import boundary.usermenu.RepMenuUI;
import controller.control.SystemController;
import controller.database.IRepository;
import controller.service.ApplicationService;
import controller.service.AuthenticationService;
import controller.service.InternshipService;
import controller.service.RequestService;
import entity.FilterCriteria;
import entity.application.Application;
import entity.internship.InternshipLevel;
import entity.internship.InternshipOpportunity;
import entity.user.CompanyRep;
import java.time.LocalDate;
import java.util.List;


import util.exceptions.MaxExceedException;
import util.exceptions.ObjectAlreadyExistsException;
import util.exceptions.ObjectNotFoundException;


 /**
  * Controller for company representatives.
  * Acts as a middle layer between the UI and the service layer,
  * allowing representatives to create, view, and manage internship opportunities and applications.
  */
public class RepController extends UserController {

	private final CompanyRep rep;
	private final ApplicationService applicationService;
	private final InternshipService internshipService;

	 /**
	  * Constructs a representative controller bound to a specific company representative.
	  *
	  * @param auth           		authentication service for login verification
	  * @param repository     		shared repository for data persistence
	  * @param requestService 		shared request service for handling requests
	  * @param rep            		the company representative associated with this controller
	  * @param internshipService 	shared service for managing internships
	  * @param applicationService 	shared service for managing applications
	  */
	public RepController(AuthenticationService auth,
                         IRepository repository, 
						 RequestService requestService,
						 CompanyRep rep, 
						 InternshipService internshipService, 
						 ApplicationService applicationService) {
		super(auth,repository,requestService);
		this.rep = rep;
		this.internshipService = internshipService;
        this.applicationService = applicationService;
	}

     /**
      * create nw RepMenuUI and launch its Menu
      */
	public void launch(SystemController systemController) {
    	new RepMenuUI(this,systemController).displayMenu();
	}

	/**
	 * Creates a new internship opportunity on behalf of the company representative.
	 *
     * @param title           internship title
     * @param description     internship description
     * @param level           required internship level (Basic / Intermediate / Advanced)
     * @param preferredMajors comma-separated preferred majors or "Any"
     * @param openingDate     date when applications open
     * @param closingDate     date when applications close
     * @param numOfSlots      number of available slots (1–10)
     * @return                the created InternshipOpportunity entity
     * @throws IllegalStateException    if the representative has reached the internship limit
     * @throws IllegalArgumentException if any provided field is invalid
     */
	public void createInternship(String title,
                                                  String description,
                                                  InternshipLevel level,
                                                  String preferredMajors,
                                                  LocalDate openingDate,
                                                  LocalDate closingDate,
                                                  int numOfSlots) throws MaxExceedException, ObjectAlreadyExistsException{
        InternshipOpportunity internship =internshipService.proposeInternship(title, description, level, preferredMajors, openingDate, closingDate, numOfSlots, rep.getId(), rep.getCompanyName());
        requestService.createInternshipRequest(rep.getId(), internship);
		internshipService.addInternship(internship);
    }
	

	/**
     * Updates the visibility of an internship opportunity.
     *
     * @param internshipId unique ID of the internship
     * @param visibility   true to make visible, false to hide
     * @throws SecurityException        if the internship does not belong to this representative or internship status is pending
     * @throws IllegalArgumentException if the internship ID is invalid
     * @throws IllegalStateException    if visibility cannot be changed due to current state
     */
	public void toggleVisibility(String internshipId, boolean visibility) {
		internshipService.setVisibility(internshipId, visibility);
	}

	/**
     * Retrieves all pending applications for a given internship.
     *
     * @param internshipId unique ID of the internship
     * @return list of pending {@link Application} objects
     * @throws IllegalArgumentException if the internship ID is invalid
     * @throws SecurityException        if the internship does not belong to this representative
     */
	public List<Application> getApplications(String internshipId) {
		return internshipService.findInternshipById(internshipId).getPendingApplications();
	}

	/**
	 * Retrieves all internship opportunities for the company representative.
	 * @return list of {@link InternshipOpportunity} objects
	 */
	public List<InternshipOpportunity> getInternships() {
		return internshipService.getInternshipsByCompany(rep.getCompanyName());
	}

	public List<InternshipOpportunity> getFilteredInternships(List<InternshipOpportunity> internshipOpportunities, FilterCriteria filter){
		return internshipService.getFilteredInternship(internshipOpportunities, filter);
	}

	public List<InternshipOpportunity> getPendingInternships(List<InternshipOpportunity> internshipOpportunities){
		return internshipService.getPendingInternships(internshipOpportunities);
	}

	public List<InternshipOpportunity> getApprovedInternships(List<InternshipOpportunity> internshipOpportunities){
		return internshipService.getApprovedInternships(internshipOpportunities);
	}

	/**
	 * Approves an internship application.
	 * @param appId application ID
	 * @throws IllegalArgumentException if the application ID is invalid
	 * @throws SecurityException        if the application does not belong to this representative
	 * @throws IllegalStateException    if the application has already been reviewed
	 */
	public void approveApp(String appId) throws ObjectNotFoundException,MaxExceedException {
        Application app = applicationService.findApplication(appId);
        InternshipOpportunity internship = internshipService.findInternshipById(app.getInternshipId());
        // Ensure internship exists
        if (internship == null) {
            throw new ObjectNotFoundException("Invalid internship ID associated with application: " + app.getInternshipId());
        }
        // Security check: ensure the internship belongs to the representative
        if (!internship.getCreatedBy().equalsIgnoreCase(rep.getId())) {
            throw new SecurityException("You can only review applications for your own internships.");
        }
        if (internshipService.isFilled(internship)) {
            throw new MaxExceedException("Max number of approved slot have be filled");
        }
		applicationService.reviewApplication(appId, true);
        internshipService.addAcceptedApplicationToInternship(applicationService.findApplication(appId));
	}

	/**
	 * Rejects an internship application.
	 * @param appId application ID
	 * @throws ObjectNotFoundException  if the application ID is invalid
	 * @throws SecurityException        if the application does not belong to this representative
	 * @throws IllegalStateException    if the application has already been reviewed
	 */
	public void rejectApp(String appId) throws ObjectNotFoundException, MaxExceedException {
		applicationService.reviewApplication(appId, false);
	}

    public void deleteInternship(String internshipId) throws ObjectNotFoundException{
        internshipService.removeInternship(rep.getId(), internshipId);
        requestService.deleteInternshipRequest(internshipId);
    }

    public void editInternship(String internshipId,
							   String title, 
							   String description, 
							   String preferredMajors, 
							   LocalDate openingDate, 
							   LocalDate closingDate,
							   Integer slot, 
							   InternshipLevel level) throws ObjectNotFoundException, SecurityException {
		internshipService.editInternship(internshipId,title, description, preferredMajors, openingDate, closingDate,slot, level);
    }

	public CompanyRep getRep() {
		return rep;
	}

	/**
	 * Validates that the internship ID exists and belongs to the representative.
	 * @param internshipId unique ID of the internship
	 * @throws IllegalArgumentException if the internship ID is invalid
	 * @throws SecurityException        if the internship does not belong to this representative
	 */
	public void validateInternshipId(String internshipId) {
		InternshipOpportunity internship = internshipService.findInternshipById(internshipId);
		if(internship == null) {
			throw new IllegalArgumentException("Internship not found");
		}
		if(!internship.getCompanyName().equalsIgnoreCase(rep.getCompanyName())) {
			throw new SecurityException("You do not have permission to manage this internship");
		}
	}

	/**
	 * Parses a string to an InternshipLevel enum.
	 * Checks for valid input.
	 * @param level The string representation of the internship level
	 * @param allowNull Whether null values are allowed (for edit mode)
	 * @return The corresponding {@link InternshipLevel} enum
	 * @throws IllegalArgumentException if the input is invalid or null
	 */
	public InternshipLevel parseLevel(String level, boolean allowNull) {
		return InternshipService.parseLevel(level, allowNull);
	}

	/**
	 * Validates the number of slots for an internship.
	 * @param slots The number of slots to validate
	 * @param allowNull Whether null values are allowed (for edit mode)
	 * @throws IllegalArgumentException if the number of slots is out of range or null when not allowed
	 */
	public void checkValidSlots(Integer slots, boolean allowNull) {
		InternshipService.checkValidSlots(slots, allowNull);
	}
}