package entity.application;
import entity.Displayable;
import util.io.AsciiTableFormatter;

import java.io.Serializable;
import java.util.List;
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
	 */
	public void changeApplicationStatus(ApplicationStatus newStatus) {
		this.status = newStatus;
	}


    /**
     *
     * @return String for displaying
     */
	@Override
	public String toString() {
		List<AsciiTableFormatter.Row> rows = List.of(
				new AsciiTableFormatter.Row("Application ID", applicationId),
				new AsciiTableFormatter.Row("Student ID", studentId),
				new AsciiTableFormatter.Row("Internship ID", internshipId),
				new AsciiTableFormatter.Row("Status", String.valueOf(status))
		);

		String table = AsciiTableFormatter.formatTable(rows);
		return table.replace(String.valueOf(status), status.coloredString());
	}

}
