package controller.service;

import entity.internship.InternshipOpportunity;
import entity.user.CompanyRep;
import entity.user.Student;

public class EligibilityService {

	/**
	 * 
	 * @param s
	 * @param i
	 */
	public static boolean canViewInternship(Student s, InternshipOpportunity i) {
		// TODO - implement EligibilityService.canViewInternship
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param s
	 * @param i
	 */
	public static boolean canApplyInternships(Student s, InternshipOpportunity i) { // this check, if the student' app count is below 3 and if there is still available slot for internship
                                                                                    // student shld not be allowed to apply if all the intership slot is filled
		// TODO - implement EligibilityService.canApplyInternships
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param s
	 * @param i
	 */
	public static boolean isEligibleForInternship(Student s, InternshipOpportunity i) {  // this just check if the student qualify for the internship
		// TODO - implement EligibilityService.isEligibleForInternship
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param rep
	 */
	public static boolean canCreateInternship(CompanyRep rep) {
		// TODO - implement EligibilityService.canCreateInternship
		throw new UnsupportedOperationException();
	}

}