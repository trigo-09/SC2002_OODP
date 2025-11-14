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
	 * @param app application object
     * add application to both student and internship o
	 */
	public void addApplication(String studentId,Application app) {
        students.get(studentId.toLowerCase()).addApplication(app);
	}

	/**
	 *add staff to staff map
	 * @param staff staff object
	 */
	public void addCareerStaff(CareerStaff staff) {
        careerStaff.put(staff.getId().toLowerCase(), staff);
	}

	/**
	 * add internship to rep's list
	 * @param intern internship object
	 */
	public void addInternship(String repId,InternshipOpportunity intern) {
        CompanyRep rep = approvedReps.get(repId.toLowerCase());
        if(rep == null){
            System.out.println("rep not found");
        }
        if(intern == null){
            System.out.println("internship not found");
        }
        rep.addInternship(intern);
	}

	/**
	 * add student to student hash map
	 * @param student student object
	 */
	public void addStudent(Student student) {
        students.put(student.getId().toLowerCase(), student);
	}

    /**
     * add request to request map
     * @param request Request object
     */
    public void addRequest(Request request) {
        requests.put(request.getId().toLowerCase(), request);
    }

    /**
     *  remove request from request map using request ID
     * @param requestId request ID
     */
    public void removeRequest(String requestId) {
        requests.remove(requestId.toLowerCase());
    }


    /**
	 * 
	 * @param studentId student ID
	 */
	public List<Application> applicationByStudent(String studentId) {
        Student student = students.get(studentId.toLowerCase());
        return (student != null) ? student.getApplications() : new ArrayList<>();
	}

	/**
	 * 
	 * @param repId rep ID
	 */
	public void approveCompanyRep(String repId) {
        CompanyRep rep = pendingReps.get(repId.toLowerCase());
        if (rep != null) {
            pendingReps.remove(repId.toLowerCase());
            approvedReps.put(repId.toLowerCase(), rep);
        }
        else {
            System.out.println("Company rep cannot be swapped");
        }

	}

    /**
     *
     * @param requestId request ID
     * @return request
     */
    public Request getRequest(String requestId) {
        return requests.get(requestId.toLowerCase());
    }

	/**
	 * 
	 * @param userId user ID
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
     * @param internshipId internship ID
     * @return internship
     */
    public InternshipOpportunity findInternshipOpportunity(String internshipId) {
        return approvedReps.values().stream()
                .flatMap(c -> c.getInternships().stream())
                .filter(intern -> intern.getId().equalsIgnoreCase(internshipId))
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
     * @return Map of Approved Reps
     */
	public Map<String, CompanyRep> getApprovedReps() {
		return Collections.unmodifiableMap(approvedReps);
	}

    /**
     *
     * @return Map of Career Staff
     */
	public Map<String, CareerStaff> getCareerStaff() {
		return Collections.unmodifiableMap(careerStaff);
	}

    /**
     *
     * @return list of all internships
     */
	public List<InternshipOpportunity> getAllInternships() {
		return approvedReps.values().stream()
                .flatMap(rep->rep.getInternships().stream())
                .toList();
	}

    /**
     *
     * @return Map of Pending Reps
     */
	public Map<String, CompanyRep> getPendingReps() {
		return Collections.unmodifiableMap(pendingReps);
	}

    /**
     *
     * @return Map of Students
     */
	public Map<String, Student> getStudents() {
		return Collections.unmodifiableMap(students);
	}

    /**
     *
     * @param type Request type
     * @return list of requests of given type
     * @param <T> type of request class
     */
    public <T extends Request> List<T> getAllRequests(Class<T> type) {
        return requests.values().stream()
                .filter(type::isInstance)
                .map(type::cast)
                .toList();
    }

	/**
	 * 
	 * @param rep company rep object
	 */
	public void registerCompanyRep(CompanyRep rep) {
        pendingReps.put(rep.getId().toLowerCase(), rep);
	}

    /**
     *
     * @param companyName company name
     * @return list of internships of given company
     */
    @Override
    public List<InternshipOpportunity> getInternshipsByCompany(String companyName) {
        return approvedReps.values().stream()
                .filter(rep -> rep.getCompanyName().equals(companyName))
                .flatMap(rep -> rep.getInternships().stream())
                .toList();
    }
}