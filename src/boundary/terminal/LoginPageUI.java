package boundary.terminal;


import boundary.AttributeGetter;
import controller.control.SystemController;
import util.exceptions.AuthenticationException;
import util.exceptions.PageBackException;
import util.ui.ChangePage;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LoginPageUI {
    /**
     * Displays a login page
     *
     * @param controller
     * @throws PageBackException
     */
    public static void login(SystemController controller) throws PageBackException {
        ChangePage.changePage();
        Scanner sc = new Scanner(System.in);
        while (true) {
            String userId = AttributeGetter.getUserId();
            String password = AttributeGetter.getPassword();
            if (userId.isEmpty() || password.isEmpty()) {
                System.out.println("Please enter your credentials");
                continue;
            }

            try {
                controller.handleLogin(userId,password);
                return;
            }catch (AuthenticationException e){
                System.out.println("Login failed: "+e.getMessage());
            }

            System.out.println("\nEnter [b] to go back, or any other key to try again.");
            String choice = sc.nextLine().trim();
            if (choice.equalsIgnoreCase("b")) {
                throw new PageBackException(); // signal upper controller to go back
            }

            System.out.println("Please try again.");
        }
    }

    public static void register(SystemController controller) throws PageBackException  {
        ChangePage.changePage();
        Scanner sc = new Scanner(System.in);
        while (true) {
            String userId = AttributeGetter.getUserId();
            String password = AttributeGetter.getPassword();
            String name = AttributeGetter.getName();
            String company = AttributeGetter.getString("Enter your company name: ");
            String department = AttributeGetter.getString("Enter your department name: ");
            String position = AttributeGetter.getString("Enter your position: ");
            if(company.isEmpty() || department.isEmpty() || position.isEmpty() || userId.isEmpty() || password.isEmpty() || name.isEmpty() ) {
                System.out.println("Please enter all the fields");continue;}

            Map<String,String> attributes = new HashMap<>();
            attributes.put("company",company);
            attributes.put("department",department);
            attributes.put("position",position);

            try {
                controller.registerRep(userId,name,password,attributes);
            }catch (AuthenticationException | IllegalArgumentException e){
                System.out.println("Registration failed: "+e.getMessage());
            }

            System.out.println("\nEnter [b] to go back, or any other key to retry.");
            String choice = sc.nextLine().trim();
            if (choice.equalsIgnoreCase("b")) {
                throw new PageBackException();
            }

        }
    }
}
