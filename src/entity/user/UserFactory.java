package entity.user;
import util.PasswordHasher;

import java.util.*;

/**
 * The UserFactory class provides a static factory method to create
 * different types of users based on role. It encapsulates
 * This class is not intended to be instantiated
 */
public class UserFactory {

    /**
     * Creates a new user based on the provided role and attributes.
     * type of user to create (Student, CareerStaff, or CompanyRep)
     *
     * @param role       the role of the user
     * @param id         ID of user
     * @param name       the name of the user
     * @param Password   plain password which will be hashed
     * @param attributes a map containing additional details required to construct the user;
     * @return a User instance based on the provided role and attributes
     * @throws IllegalArgumentException if the attributes map is null or empty
     * @throws UnsupportedOperationException if the role is unsupported
     */
	public static User createUser(UserRole role, String id, String name, String Password, Map<String, String> attributes) {
        if(attributes == null || attributes.isEmpty()) {throw new IllegalArgumentException("attributes is null or empty");}
        String hashedPassword = PasswordHasher.hash(Password);
        return switch(role) {
            case STUDENT -> {
                int year = Integer.parseInt(attributes.get("year"));
                String major = attributes.get("major");
                yield new Student(name, id, hashedPassword, year, major);
            }
            case STAFF-> {
                String staffDepartment = attributes.get("department");
                yield new CareerStaff(name, id, hashedPassword, staffDepartment);
            }
            case REP-> {
                String company = attributes.get("company");
                String repDept = attributes.get("department");
                String position = attributes.get("position");
                yield new CompanyRep(name, id, hashedPassword, company, repDept, position);
            }
            default -> {throw new UnsupportedOperationException("Unsupported role");}
        };
    }

}
