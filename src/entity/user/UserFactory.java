package entity.user;
import java.util.*;

public class UserFactory {

	/**
	 * 
	 * @param role
	 * @param id
	 * @param name
	 * @param hashedPassword
	 * @param attributes
	 */
	public User createUser(UserRole role, String id, String name, String hashedPassword, Map<String, String> attributes) {
        switch(role) {
            case STUDENT:
                int year = Integer.parseInt(attributes.get("year"));
                String major = attributes.get("major");
                Student tempStudent = new Student(name,id,hashedPassword,year,major);
                return tempStudent;
            case STAFF:
                String staffDepartment = attributes.get("department");
                CareerStaff tempStaff = new CareerStaff(name,id,hashedPassword,staffDepartment);
                return tempStaff;
            case REP:
                String company = attributes.get("company");
                String repDept = attributes.get("department");
                String position = attributes.get("position");
                CompanyRep tempRep = new CompanyRep(name,id,hashedPassword,company,repDept,position,RepStatus.PENDING);
                return tempRep;
        }
        return null;
    }

}
