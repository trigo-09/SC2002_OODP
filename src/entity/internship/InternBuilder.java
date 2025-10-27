package entity.internship;

import entity.application.Application;
import entity.user.CompanyRep;
import java.time.LocalDate;

import java.util.*;

public class InternBuilder {

	private LocalDate closingDate;
	private int companyName;
	private CompanyRep createdBy;
	private String description;
	private InternshipLevel level;
	private LocalDate openingDate;
	private String preferredMajors;
	private List<Application> slots;
	private String title;

}