package entity.request;

import controller.database.IResposistory;
import entity.internship.InternStatus;
import entity.internship.InternshipOpportunity;

public class InternshipVetRequest extends Request {
    private final InternshipOpportunity internship;

    public InternshipVetRequest(InternshipOpportunity internship, String requesterId) {
        super(requesterId);
        this.internship = internship;
    }

    public InternshipOpportunity getInternship() { return internship; }

    public void approve(IResposistory repo) {
        internship.setStatus(InternStatus.APPROVED);
        repo.findUser(super.getRequesterId()).addNotification("Internship is approved");
    }

    public void reject(IResposistory repo) {
        internship.setStatus(InternStatus.REJECTED);
        repo.findUser(super.getRequesterId()).addNotification("Internship is rejected");
    }

    @Override
    public String getSplitter() {
        return "----------------------------------";
    }

    @Override
    public String getString() {
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
