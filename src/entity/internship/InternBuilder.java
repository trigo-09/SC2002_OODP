package entity.internship;

import java.time.LocalDate;
import java.util.UUID;

public class InternBuilder {
	private String id = UUID.randomUUID().toString();
	private String companyName;
	private String title;
	private String description;
	private InternshipLevel level;
	private String preferredMajors;
	private LocalDate openingDate;
	private LocalDate closingDate;
	private int numOfSlots;
	private InternStatus status = InternStatus.PENDING;
	private String createdBy;


    /**
     * set companyName
     * @param companyName
     * @return
     */
	public InternBuilder companyName(String companyName){
		this.companyName = companyName;
		return this;
	}

    /**
     * set title
     * @param title
     * @return
     */
	public InternBuilder title(String title){
		this.title =  title;
		return this;
	}

    /**
     * set description
     * @param description
     * @return
     */
	public InternBuilder description(String description){
		this.description = description;
		return this;
	}

    /**
     * set internship level
     * @param level
     * @return
     */
	public InternBuilder level(InternshipLevel level){
		this.level = level;
		return this;
	}

    /**
     * set preferredMajors
     * @param preferredMajors
     * @return
     */
	public InternBuilder preferredMajors(String preferredMajors){
		this.preferredMajors = preferredMajors;
		return this;
	}

    /**
     * set Opening date
     * @param openingDate
     * @return
     */
	public InternBuilder openingDate(LocalDate openingDate){
		this.openingDate = openingDate;
		return this;
	}

    /**
     * set Closing date
     * @param closingDate
     * @return
     */
	public InternBuilder closingDate(LocalDate closingDate){
		this.closingDate = closingDate;
		return this;
	}

    /**
     * set number of application slot
     * @param numOfSlots
     * @return
     */
	public InternBuilder numOfSlots(int numOfSlots){
		this.numOfSlots = numOfSlots;
		return this;
	}

    /**
     * set rep id
     * @param createdBy
     * @return
     */
	public InternBuilder createdBy(String createdBy){
		this.createdBy = createdBy;
		return this;
	}

    /**
     *
     * @return internship after creating object
     */
	public  InternshipOpportunity build(){
		return new InternshipOpportunity(
				id,
				companyName,
				title,
				description,
				level,
				preferredMajors,
				openingDate,
				closingDate,
				numOfSlots,
                status,
				createdBy
		);
	}


}