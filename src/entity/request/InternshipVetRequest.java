package entity.request;

import controller.database.IRepository;
import entity.internship.InternStatus;
import entity.internship.InternshipOpportunity;
import util.io.AsciiTableFormatter;

import java.util.List;

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
        List<AsciiTableFormatter.Row> rows = List.of(
                new AsciiTableFormatter.Row("Request Type", "Internship Vetting"),
                new AsciiTableFormatter.Row("Request ID", getId()),
                new AsciiTableFormatter.Row("Requester ID", getRequesterId()),
                new AsciiTableFormatter.Row("Internship ID", internship.getId()),
                new AsciiTableFormatter.Row("Title", internship.getTitle()),
                new AsciiTableFormatter.Row("Company", internship.getCompanyName()),
                new AsciiTableFormatter.Row("Level", String.valueOf(internship.getLevel())),
                new AsciiTableFormatter.Row("Opening Date", String.valueOf(internship.getOpeningDate())),
                new AsciiTableFormatter.Row("Closing Date", String.valueOf(internship.getClosingDate())),
                new AsciiTableFormatter.Row("Current Status", String.valueOf(internship.getStatus()))
        );

        String table = AsciiTableFormatter.formatTable(rows);
        return table.replace(String.valueOf(internship.getStatus()), internship.getStatus().coloredString());
    }


}
