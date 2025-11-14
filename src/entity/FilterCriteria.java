package entity;

import entity.internship.InternStatus;
import entity.internship.InternshipLevel;

import java.time.LocalDate;
/**
 * Represents a set of filtering criteria to filer internship opportunities
 * Stores internship status, major, internship level, closing date and company name
 * can construct FilterCriteria with attributes as null first 
 */
public class FilterCriteria {

	private InternStatus status;
	private String preferredMajor;
	private InternshipLevel level;
	private LocalDate closingDate;
    private String companyName;
	/**
	 * Constructs FilterCriteria obj
	 * @param status
	 * @param preferredMajor
	 * @param level
	 * @param closingDate
	 * @param companyName
	 */
    public FilterCriteria(InternStatus status, String preferredMajor, InternshipLevel level, LocalDate closingDate, String companyName) {
        this.status = status;
        this.preferredMajor = preferredMajor;
        this.level = level;
        this.closingDate = closingDate;
        this.companyName = companyName;

    }
	/**
	 * Constructs FilterCriteria obj with null attributes
	 */
    public FilterCriteria() {
        this(null,null,null,null,null);
    }
	/**
	* gets status
	* @return Status
	*/
    public InternStatus getStatus() {
        return status;
    }
	/**
	* sets status
	* @param status
	*/
    public void setStatus(InternStatus status) {
        this.status = status;
    }
	/**
	* gets preferredMajor
	* @return preferredMajor
	*/
    public String getPreferredMajor() {
        return preferredMajor;
    }
	/**
	* sets preferredMajor
	* @param preferredMajor
	*/
    public void setPreferredMajor(String preferredMajor) {
        this.preferredMajor = preferredMajor;
    }
	/**
	* gets level
	* @return level
	*/
    public InternshipLevel getLevel() {
        return level;
    }
	/**
	* sets level
	* @param level
	*/
    public void setLevel(InternshipLevel level) {
        this.level = level;
    }
	/**
	* gets closingDate
	* @return closingDate
	*/
    public LocalDate getClosingDate() {
        return closingDate;
    }
	/**
	* sets closingDate
	* @param closingDate
	*/
    public void setClosingDate(LocalDate closingDate) {
        this.closingDate = closingDate;
    }
	/**
	* gets companyName
	* @return companyName
	*/
    public String getCompanyName() {return companyName;}
	/**
	* sets companyName
	* @param companyName
	*/
    public void setCompanyName(String companyName) {this.companyName = companyName;}
}
