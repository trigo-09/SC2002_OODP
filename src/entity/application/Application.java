package entity.application;

public class Application {

	// Attributes
	private final String studentID;
	private final String internshipID;
	private ApplicationStatus status;

	// Constructor
	public Application(String studentID, String internshipID) {
		this.studentID = studentID;
		this.internshipID = internshipID;
		this.status = ApplicationStatus.PENDING;
	}

	// Getter methods
	public String getStudentId() { return studentID; }
    public String getInternshipId() { return internshipID; }
    public ApplicationStatus getStatus() { return status; }

	// Methods to change application status
	public void accept() {
		if (this.status != ApplicationStatus.PENDING) {
			throw new IllegalStateException("Only pending applications can be accepted.");
		}
		this.status = ApplicationStatus.APPROVED;
	}

	public void reject() {
		if (this.status != ApplicationStatus.PENDING) {
			throw new IllegalStateException("Only pending applications can be rejected.");
		}
		this.status = ApplicationStatus.REJECTED;
	}

	public void withdraw() {
		if (this.status == ApplicationStatus.REJECTED) {
			throw new IllegalStateException("Only pending or accepted applications can be withdrawn.");
		}
		this.status = ApplicationStatus.WITHDRAWN;
	}

}

