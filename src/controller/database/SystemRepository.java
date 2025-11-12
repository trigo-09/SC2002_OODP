package controller.database;

import entity.application.Application;
import entity.internship.InternshipOpportunity;
import entity.request.*;
import entity.user.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;

/**
 * Class serve as Data base and contains all the system's data
 * serve as single source of truth
 */
public class SystemRepository implements IRepository, Serializable {

    @Serial
	private static final long serialVersionUID = 1L;
	private final Map<String, Student> students = new HashMap<>();
	private final Map<String, CareerStaff> careerStaff = new HashMap<>();
	private final Map<String, CompanyRep> pendingReps = new HashMap<>();
	private final Map<String, CompanyRep> approvedReps = new HashMap<>();
    private final Map<String, Request> requests = new HashMap<>();

	/**
	 * @param app
     * add application to both student and internship o
	 */
	public void addApplication(String studentId,Application app) {
        students.get(studentId.toLowerCase()).addApplication(app);
        findInternshipOpportunity(app.getInternshipId().toLowerCase()).addPendingApplication(app);
	}

	/**
	 *add staff to staff map
	 * @param staff
	 */
	public void addCareerStaff(CareerStaff staff) {
        careerStaff.put(staff.getId().toLowerCase(), staff);
	}

	/**
	 * add internship to rep's list
	 * @param intern
	 */
	public void addInternship(String repId,InternshipOpportunity intern) {
        approvedReps.get(repId.toLowerCase()).addInternship(intern);
	}

	/**
	 * add student to student hash map
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
	 * @param repId
	 */
	public void approveCompanyRep(String repId) {
        CompanyRep rep = pendingReps.get(repId.toLowerCase());
        if (rep != null) {
            pendingReps.remove(repId);
            approvedReps.put(repId, rep);
        }

	}

    /**
     *
     * @param requestId
     * @return
     */
    public Request getRequest(String requestId) {
        return requests.get(requestId.toLowerCase());
    }

	/**
	 * 
	 * @param userId
	 */
	public User findUser(String userId) {
        return Stream.of(students, careerStaff, approvedReps, pendingReps)
                .map(m -> m.get(userId.toLowerCase()))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
	}

    /**
     *
     * @param internshipId
     * @return
     */
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

    /**
     *
     * @return
     */
	public Map<String, CompanyRep> getApprovedReps() {
		return Collections.unmodifiableMap(approvedReps);
	}

    /**
     *
     * @return
     */
	public Map<String, CareerStaff> getCareerStaff() {
		return Collections.unmodifiableMap(careerStaff);
	}

    /**
     *
     * @return
     */
	public List<InternshipOpportunity> getAllInternships() {
		return approvedReps.values().stream()
                .flatMap(rep->rep.getInternships().stream())
                .toList();
	}

    /**
     *
     * @return
     */
	public Map<String, CompanyRep> getPendingReps() {
		return Collections.unmodifiableMap(pendingReps);
	}

    /**
     *
     * @return
     */
	public Map<String, Student> getStudents() {
		return Collections.unmodifiableMap(students);
	}

    /**
     *
     * @param type
     * @return
     * @param <T>
     */
    public <T extends Request> List<T> getAllRequests(Class<T> type) {
        return requests.values().stream()
                .filter(type::isInstance)
                .map(type::cast)
                .toList();
    }

	/**
	 * 
	 * @param rep
	 */
	public void registerCompanyRep(CompanyRep rep) {
        pendingReps.put(rep.getId().toLowerCase(), rep);
	}

    /**
     *
     * @param companyName
     * @return
     */
    @Override
    public List<InternshipOpportunity> getInternshipsByCompany(String companyName) {
        return approvedReps.values().stream()
                .filter(rep -> rep.getCompanyName().equals(companyName))
                .flatMap(rep -> rep.getInternships().stream())
                .toList();
    }
}