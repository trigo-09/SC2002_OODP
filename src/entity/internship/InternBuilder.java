package entity.internship;

import entity.application.Application;
import entity.user.CompanyRep;
import java.time.LocalDate;

import java.util.*;

public class InternBuilder {

	public static InternshipOpportunity createInternship(String id, String companyName, String title, String description, InternshipLevel level,
														 String preferredMajors, LocalDate openingDate, LocalDate closingDate, int numOfSlots,
														 InternStatus status, CompanyRep createdBy){
		InternshipOpportunity internshipOpportunity = new InternshipOpportunity(id, companyName, title, description, level, preferredMajors, openingDate,
				closingDate, numOfSlots,createdBy);
		return internshipOpportunity;

	}

}