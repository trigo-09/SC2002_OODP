package controller.database;

import entity.application.Application;
import entity.internship.InternshipOpportunity;
import entity.request.*;
import entity.user.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;


public class SystemRepository implements IRepository, Serializable {

    @Serial
	private static final long serialVersionUID = 1L;
	private final Map<String, Student> students = new HashMap<>();
	private final Map<String, CareerStaff> careerStaff = new HashMap<>();
	private final Map<String, CompanyRep> pendingReps = new HashMap<>();
	private final Map<String, CompanyRep> approvedReps = new HashMap<>();
    private final Map<String, Request> requests = new HashMap<>();

	/**
	 * 
	 * @param app
     * add application to both student and internship object
	 */
	public void addApplication(String studentId,Application app) {
        students.get(studentId.toLowerCase()).addApplication(app);
        findInternshipOpportunity(app.getInternshipId().toLowerCase()).addPendingApplication(app);
	}

	/**
	 * 
	 * @param staff
	 */
	public void addCareerStaff(CareerStaff staff) {
        careerStaff.put(staff.getId().toLowerCase(), staff);
	}

	/**
	 * 
	 * @param intern
	 */
	public void addInternship(String repId,InternshipOpportunity intern) {
        approvedReps.get(repId.toLowerCase()).addInternship(intern);
	}

	/**
	 * 
	 * @param student
	 */
	public void addStudent(Student student) {
        students.put(student.getId().toLowerCase(), student);
	}

    /**
     *
     * @param withdrawalRequest
     */
    public void addWithdrawalRequest(WithdrawalRequest withdrawalRequest) {
        requests.put(withdrawalRequest.getId().toLowerCase(), withdrawalRequest);
    }

    /**
     *
     * @param registrationRequest
     */
    public void addRegistrationRequest(RegistrationRequest registrationRequest) {
        requests.put(registrationRequest.getId().toLowerCase(), registrationRequest);
    }

    /**
     *
     * @param internshipVetRequest
     */
    public void addInternshipVetRequest(InternshipVetRequest internshipVetRequest) {
        requests.put(internshipVetRequest.getId().toLowerCase(), internshipVetRequest);
    }

    /**
     *
     * @param requestId
     */
    @Override
    public void removeWithdrawalRequest(String requestId) {
        requests.remove(requestId.toLowerCase());
    }

    /**
     *
     * @param requestId
     */
    @Override
    public void removeInternshipVetRequest(String requestId) {
        requests.remove(requestId.toLowerCase());
    }

    /**
     *
     * @param requestId
     */
    @Override
    public void removeRegistrationRequest(String requestId) {
        requests.remove(requestId.toLowerCase());
    }

    /**
	 * 
	 * @param studentId
	 */
	public List<Application> applicationByStudent(String studentId) {
        Student student = students.get(studentId.toLowerCase());
        return (student != null) ? student.getApplications() : new ArrayList<>();
	}

	/**
	 * 
	 * @param internId
	 */
	public List<Application> applicationForInternship(String internId) {
        return approvedReps.values().stream()
                .flatMap(rep -> rep.getInternships().stream())
                .filter(internship -> internship.getId().equals(internId.toLowerCase()))
                .findFirst()
                .map(InternshipOpportunity::getPendingApplications)
                .orElseGet(Collections::emptyList);
	}

	/**
	 * 
	 * @param repId
	 */
	public void approveCompanyRep(String repId) {
        CompanyRep rep = pendingReps.get(repId.toLowerCase());
        if (rep != null) {
            pendingReps.remove(repId);
            approvedReps.put(repId, rep);
        }

	}

    public void approveCompanyRep(CompanyRep rep) {
        if(rep != null) {
            pendingReps.remove(rep.getId().toLowerCase());
            approvedReps.put(rep.getId().toLowerCase(), rep);
        }
    }

    public Request getRequest(String requestId) {
        return requests.get(requestId.toLowerCase());
    }

	/**
	 * 
	 * @param userId
	 */
    // might change it
	public User findUser(String userId) {
        return Stream.of(students, careerStaff, approvedReps, pendingReps)
                .map(m -> m.get(userId.toLowerCase()))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
	}

    public InternshipOpportunity findInternshipOpportunity(String internshipId) {
        return approvedReps.values().stream()
                .flatMap(c -> c.getInternships().stream())
                .filter(intern -> intern.getId().equals(internshipId.toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Finds an application by its ID.
     * @param applicationId unique ID of the application
     * @return the {@link Application} object
     * @throws IllegalArgumentException if the application is not found
     */
    @Override
    public Application findApplication(String applicationId) {
        return students.values().stream()
            .flatMap(s -> s.getApplications().stream())
            .filter(app -> app.getApplicationId().equalsIgnoreCase(applicationId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Application not found: " + applicationId));
    }


	public List<Application> getAllApplications() {
        return students.values().stream()
                .flatMap(s -> s.getApplications().stream())
                .toList();
	}


	public Map<String, CompanyRep> getApprovedReps() {
		return this.approvedReps;
	}

	public Map<String, CareerStaff> getCareerStaff() {
		return this.careerStaff;
	}

	public List<InternshipOpportunity> getAllInternships() {
		return approvedReps.values().stream()
                .flatMap(rep->rep.getInternships().stream())
                .toList();
	}

	public Map<String, CompanyRep> getPendingReps() {
		return this.pendingReps;
	}

	public Map<String, Student> getStudents() {
		return this.students;
	}

    public <T extends Request> List<T> getAllRequests(Class<T> type) {
        return requests.values().stream()
                .filter(type::isInstance)
                .map(type::cast)
                .toList();
    }

    public void deleteApplication(String studentId, String applicationId) {
        Optional.ofNullable(students.get(studentId.toLowerCase()))
                .ifPresent(student -> student.withdrawApplication(applicationId.toLowerCase()));
    }



	/**
	 * 
	 * @param rep
	 */
	public void registerCompanyRep(CompanyRep rep) {
        pendingReps.put(rep.getId().toLowerCase(), rep);
	}


    @Override
    public List<InternshipOpportunity> getInternshipsByCompany(String companyName) {
        return approvedReps.values().stream()
                .filter(rep -> rep.getCompanyName().equals(companyName))
                .flatMap(rep -> rep.getInternships().stream())
                .toList();
    }

   public List<InternshipOpportunity> getAllInternshipsByRep(String repId) {
        return approvedReps.get(repId.toLowerCase()).getInternships();
   }

}