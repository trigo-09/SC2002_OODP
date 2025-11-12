package entity.application;
import entity.Displayable;

import java.io.Serializable;
import java.util.UUID;

/**
 * Represents a student's application for a specific internship opportunity.
 * Each application has a unique ID, links to a student and an internship, and tracks its review status.
 */

public class Application implements Serializable, Displayable {

	private final String studentId;
	private final String internshipId;
  private final String applicationId;
	private ApplicationStatus status;

	/**
	 * Constructs a new Application with PENDING status.
	 *
	 * @param studentId    ID of the student applying
	 * @param internshipId ID of the internship opportunity
	 */
	public Application(String studentId, String internshipId) {
		this.studentId = studentId;
		this.internshipId = internshipId;
		this.status = ApplicationStatus.PENDING;
    this.applicationId = UUID.randomUUID().toString();
	}

	/**
 	 * Returns the unique ID of the student who submitted this application.
 	 *
 	 * @return the student ID
 	 */
	public String getStudentId() { return studentId; }

	/**
 	 * Returns the unique ID of the internship opportunity that this application was submitted for.
 	 *
 	 * @return the internship ID
 	 */
	public String getInternshipId() { return internshipId; }

	/**
 	 * Returns the unique ID of this application.
 	 *
 	 * @return the application ID
 	 */
	public String getApplicationId() { return applicationId; }

	/**
 	 * Returns the current review status of this application.
	 *
	 * @return the current {@link ApplicationStatus} of this application
	 */
	public ApplicationStatus getStatus() { return status; }


	/**
	 * Changes the status of this application to the specified new status.
	 * @param newStatus the new status to set
	 * @throws IllegalStateException if the status transition is invalid
	 */
	public void changeApplicationStatus(ApplicationStatus newStatus) {
		if (!validStatusTransition(this.status, newStatus)) {
			throw new IllegalStateException("Invalid status transition from " + this.status + " to " + newStatus);
		}
		this.status = newStatus;
	}

	/**
	 * Helper method to validate status transitions.
	 * @param current the current status
	 * @param next the desired new status
	 * @return true if the transition is valid, false otherwise
	 */
	private boolean validStatusTransition(ApplicationStatus current, ApplicationStatus next) {
		return switch (current) {
			case PENDING -> next == ApplicationStatus.APPROVED || next == ApplicationStatus.REJECTED || next == ApplicationStatus.WITHDRAWN;
			case APPROVED -> next == ApplicationStatus.ACCEPTED || next == ApplicationStatus.WITHDRAWN;
			case ACCEPTED -> next == ApplicationStatus.WITHDRAWN;
			case REJECTED, WITHDRAWN -> false;
			default -> false;
		};
	}

	@Override
	public String toString() {
		return String.format(
				"Application ID: %s%n" +
						"Student ID: %s%n" +
						"Internship ID: %s%n" +
						"Status: %s",
				applicationId,
				studentId,
				internshipId,
				status
		);
	}
}
