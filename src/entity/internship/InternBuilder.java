package entity.internship;

import java.time.LocalDate;
import java.util.UUID;

public class InternBuilder {
	private String id;
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

	public InternBuilder id(String id){
		this.id = id;
		return this;
	}

	public InternBuilder companyName(String companyName){
		this.companyName = companyName;
		return this;
	}

	public InternBuilder title(String title){
		this.title =  title;
		return this;
	}

	public InternBuilder description(String description){
		this.description = description;
		return this;
	}

	public InternBuilder level(InternshipLevel level){
		this.level = level;
		return this;
	}

	public InternBuilder preferredMajors(String preferredMajors){
		this.preferredMajors = preferredMajors;
		return this;
	}

	public InternBuilder openingDate(LocalDate openingDate){
		this.openingDate = openingDate;
		return this;
	}

	public InternBuilder closingDate(LocalDate closingDate){
		this.closingDate = closingDate;
		return this;
	}

	public InternBuilder numOfSlots(int numOfSlots){
		this.numOfSlots = numOfSlots;
		return this;
	}

	public InternBuilder createdBy(String createdBy){
		this.createdBy = createdBy;
		return this;
	}

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