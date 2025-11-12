package util;

import entity.internship.InternStatus;
import entity.internship.InternshipLevel;

import java.time.LocalDate;

public class FilterCriteria {

	private InternStatus status;
	private String preferredMajor;
	private InternshipLevel level;
	private LocalDate closingDate;
    private String companyName;

    public FilterCriteria(InternStatus status, String preferredMajor, InternshipLevel level, LocalDate closingDate, String companyName) {
        this.status = status == null ? InternStatus.APPROVED : status;
        this.preferredMajor = preferredMajor == null ? "" : preferredMajor;
        this.level = level == null ? InternshipLevel.BASIC : level;
        this.closingDate = closingDate == null ? LocalDate.now() : closingDate;
        this.companyName = companyName == null ? "" : companyName;

    }
    public FilterCriteria() {
        this(null,null,null,null,null);
    }

    public InternStatus getStatus() {
        return status;
    }

    public void setStatus(InternStatus status) {
        this.status = status;
    }

    public String getPreferredMajor() {
        return preferredMajor;
    }

    public void setPreferredMajor(String preferredMajor) {
        this.preferredMajor = preferredMajor;
    }

    public InternshipLevel getLevel() {
        return level;
    }

    public void setLevel(InternshipLevel level) {
        this.level = level;
    }

    public LocalDate getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(LocalDate closingDate) {
        this.closingDate = closingDate;
    }
}