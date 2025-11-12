package entity.user;
import util.PasswordHasher;

import java.util.*;

public class UserFactory {

    private UserFactory() {} //prevent instantiation of class

	/**
	 * 
	 * @param role
	 * @param id
	 * @param name
	 * @param Password
	 * @param attributes containes other paramter required for users
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
