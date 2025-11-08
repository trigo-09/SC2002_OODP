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
}
