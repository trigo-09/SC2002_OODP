package controller.database;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;
import entity.request.*;
import entity.user.*;
import entity.internship.InternshipOpportunity;
import entity.application.Application;


public class SystemResposistory implements IResposistory, Serializable {

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
        students.get(studentId).addApplication(app);
        findInternshipOpportunity(app.getInternshipId()).addPendingApplication(app);
	}

	/**
	 * 
	 * @param staff
	 */
	public void addCareerStaff(CareerStaff staff) {
        careerStaff.put(staff.getId(), staff);
	}

	/**
	 * 
	 * @param intern
	 */
	public void addInternship(String repId,InternshipOpportunity intern) {
        approvedReps.get(repId).addInternship(intern);
	}

	/**
	 * 
	 * @param student
	 */
	public void addStudent(Student student) {
        students.put(student.getId(), student);
	}

    /**
     *
     * @param withdrawalRequest
     */
    public void addWithdrawalRequest(WithdrawalRequest withdrawalRequest) {
        requests.put(withdrawalRequest.getId(), withdrawalRequest);
    }

    /**
     *
     * @param registrationRequest
     */
    public void addRegistrationRequest(RegistrationRequest registrationRequest) {
        requests.put(registrationRequest.getId(), registrationRequest);
    }

    /**
     *
     * @param internshipVetRequest
     */
    public void addInternshipVetRequest(InternshipVetRequest internshipVetRequest) {
        requests.put(internshipVetRequest.getId(), internshipVetRequest);
    }

    /**
     *
     * @param requestId
     */
    @Override
    public void removeWithdrawalRequest(String requestId) {
        requests.remove(requestId);
    }

    /**
     *
     * @param requestId
     */
    @Override
    public void removeInternshipVetRequest(String requestId) {
        requests.remove(requestId);
    }

    /**
     *
     * @param requestId
     */
    @Override
    public void removeRegistrationRequest(String requestId) {
        requests.remove(requestId);
    }

    /**
	 * 
	 * @param studentId
	 */
	public List<Application> applicationByStudent(String studentId) {
        Student student = students.get(studentId);
        return (student != null) ? student.getApplications() : new ArrayList<>();
	}

	/**
	 * 
	 * @param internId
	 */
	public List<Application> applicationForInternship(String internId) {
        return approvedReps.values().stream()
                .flatMap(rep -> rep.getInternships().stream())
                .filter(internship -> internship.getId().equals(internId))
                .findFirst()
                .map(InternshipOpportunity::getPendingApplications)
                .orElseGet(Collections::emptyList);
	}

	/**
	 * 
	 * @param repId
	 */
	public void approveCompanyRep(String repId) {
        CompanyRep rep = pendingReps.get(repId);
        if (rep != null) {
            pendingReps.remove(repId);
            approvedReps.put(repId, rep);
        }

	}

    public Request getRequest(String requestId) {
        return requests.get(requestId);
    }

	/**
	 * 
	 * @param userId
	 */
    // might change it
	public User findUser(String userId) {
        return Stream.of(students, careerStaff, approvedReps, pendingReps)
                .map(m -> m.get(userId))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
	}

    public InternshipOpportunity findInternshipOpportunity(String internshipId) {
        return approvedReps.values().stream()
                .flatMap(c -> c.getInternships().stream())
                .filter(intern -> intern.getId().equals(internshipId))
                .findFirst()
                .orElse(null);
    }

    public Application findApplication(String applicationId) {
        return students.values().stream()
                .flatMap(s -> s.getApplications().stream())
                .filter(app -> app.getApplicationId().equals(applicationId))
                .findFirst()
                .orElse(null);
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
        Optional.ofNullable(students.get(studentId))
                .ifPresent(student -> student.withdrawApplication(applicationId));
    }



	/**
	 * 
	 * @param rep
	 */
	public void registerCompanyRep(CompanyRep rep) {
        pendingReps.put(rep.getId(), rep);
	}

   public List<InternshipOpportunity> getAllInternshipsByRep(String repId) {
        return approvedReps.get(repId).getInternships();
   }

}