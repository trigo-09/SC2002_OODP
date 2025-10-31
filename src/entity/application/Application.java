package entity.application;
import entity.internship.InternshipOpportunity;
import java.io.Serializable;

import entity.user.Student;

public class Application implements Serializable {

	// Attributes
	private final String studentId;
	private final String internshipId;
	private ApplicationStatus status;

	// Constructor
	public Application(String studentId, String internshipId) {
		this.studentId = studentId;
		this.internshipId = internshipId;
		this.status = ApplicationStatus.PENDING;
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
			case PENDING -> next == ApplicationStatus.SUCCESSFUL || next == ApplicationStatus.UNSUCCESSFUL || next == ApplicationStatus.WITHDRAWN;
			case SUCCESSFUL -> next == ApplicationStatus.ACCEPTED || next == ApplicationStatus.WITHDRAWN;
			case ACCEPTED -> next == ApplicationStatus.WITHDRAWN;
			case UNSUCCESSFUL, WITHDRAWN -> false;
			default -> false;
		};
	}
}

