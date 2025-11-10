package entity.application;
import entity.Displayable;

import java.io.Serializable;
import java.util.UUID;

public class Application implements Serializable, Displayable {

	// Attributes
	private final String studentId;
	private final String internshipId;
    private final String applicationId;
	private ApplicationStatus status;

	// Constructor
	public Application(String studentId, String internshipId) {
		this.studentId = studentId;
		this.internshipId = internshipId;
		this.status = ApplicationStatus.PENDING;
        this.applicationId = UUID.randomUUID().toString();
	}

	// Getter methods
	public String getStudentId() { return studentId; }
    public String getInternshipId() { return internshipId; }
    public ApplicationStatus getStatus() { return status; }

	// Changing application status
	public void changeApplicationStatus(ApplicationStatus newStatus) {
		if (!validStatusTransition(this.status, newStatus)) {
			throw new IllegalStateException("Invalid status transition from " + this.status + " to " + newStatus);
		}
		this.status = newStatus;
	}

	// check valid status transition
	public boolean validStatusTransition(ApplicationStatus current, ApplicationStatus next) {
		return switch (current) {
			case PENDING -> next == ApplicationStatus.APPROVED || next == ApplicationStatus.REJECTED || next == ApplicationStatus.WITHDRAWN;
			case APPROVED -> next == ApplicationStatus.ACCEPTED || next == ApplicationStatus.WITHDRAWN;
			case ACCEPTED -> next == ApplicationStatus.WITHDRAWN;
			case REJECTED, WITHDRAWN -> false;
			default -> false;
		};
	}

    public String getApplicationId() { return applicationId; }

	@Override
	public String getSplitter() {
		return "----------------------------------";
	}

	public String getString() {
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

