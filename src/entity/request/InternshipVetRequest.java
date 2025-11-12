package entity.request;

import controller.database.IRepository;
import entity.internship.InternStatus;
import entity.internship.InternshipOpportunity;

public class InternshipVetRequest extends Request {
    private final InternshipOpportunity internship;

    /**
     * constructor for InternshipVetRequest
     * @param internship
     * @param requesterId
     */
    public InternshipVetRequest(InternshipOpportunity internship, String requesterId) {
        super(requesterId);
        this.internship = internship;
    }

    /**
     *
     * @return internship
     */
    public InternshipOpportunity getInternship() { return internship; }

    /**
     * set internship to APPROVED and on visibility
     */
    public void approve() {
        internship.setStatus(InternStatus.APPROVED);
        internship.setVisibility(true);
    }

    /**
     * set internship to REJECTED
     */
    public void reject() {
        internship.setStatus(InternStatus.REJECTED);
    }

    /**
     *
     * @return string for displaying
     */
    @Override
    public String toString() {
        return String.format(
                "Request Type: Internship Vetting%n" +
                        "Request ID: %s%n" +
                        "Requester ID: %s%n" +
                        "Internship ID: %s%n" +
                        "Title: %s%n" +
                        "Company: %s%n" +
                        "Level: %s%n" +
                        "Opening Date: %s%n" +
                        "Closing Date: %s%n" +
                        "Current Status: %s%n",
                getId(),
                getRequesterId(),
                internship.getId(),
                internship.getTitle(),
                internship.getCompanyName(),
                internship.getLevel(),
                internship.getOpeningDate(),
                internship.getClosingDate(),
                internship.getStatus()
        );
    }

}
