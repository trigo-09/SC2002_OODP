package boundary.terminal;


import boundary.AttributeGetter;
import controller.control.SystemController;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import util.exceptions.AuthenticationException;
import util.exceptions.PageBackException;
import util.io.InputHelper;
import util.io.ChangePage;

/**
 * contain UI for login and registration
 */
public class LoginPageUI {

    /**
     * Displays a login page
     * @param controller system controller
     * @throws PageBackException if user wants to return
     */
    public static void login(SystemController controller) throws PageBackException {
        ChangePage.changePage();
        int count = 0;
        System.out.print("Please enter your credentials\n");
        InputHelper.readLine();
        while (true) {
            String userId = AttributeGetter.getUserId();
            String password = AttributeGetter.getPassword();
            count++;
            if (userId.isEmpty() || password.isEmpty()) {
                if (count > 1 ) {
                    System.out.println("\nEnter [b] to go back, or any other key to try again.");
                    String choice = InputHelper.readLine();
                    if (choice.equalsIgnoreCase("b")) {
                        throw new PageBackException(); // signal upper controller to go back
                    } else{
                        System.out.println("Please try again.");
                        login(controller);
                    }
                }
                ChangePage.changePage();
                System.out.println("Please fill up your credentials");
                continue;
            }

            try {
                controller.handleLogin(userId,password);
                return;
            }catch (AuthenticationException e){
                System.out.println("Login failed: "+e.getMessage());
            }

            System.out.println("\nEnter [b] to go back, or any other key to try again.");
            String choice = InputHelper.readLine();
            if (choice.equalsIgnoreCase("b")) {
                throw new PageBackException(); // signal upper controller to go back
            } else{
                System.out.println("Please try again.");
                login(controller);
            }
        }
    }

    /**
     * Display registration page
     * @param controller system controller
     * @throws PageBackException if user want to exit
     */
    public static void register(SystemController controller) throws PageBackException  {
        ChangePage.changePage();
        System.out.println("Please fill in the relevant information");
        InputHelper.readLine();
        int count = 0;
        while (true) {
            String name = AttributeGetter.getName();
            String userId = AttributeGetter.getUserId("Enter your UserId(email): ");
            String password = AttributeGetter.getPassword();
            String company = AttributeGetter.getString("Enter your company name: ");
            String department = AttributeGetter.getString("Enter your department name: ");
            String position = AttributeGetter.getString("Enter your position: ");
            count++;
            if(company.isEmpty() || department.isEmpty() || position.isEmpty() || userId.isEmpty() || password.isEmpty() || name.isEmpty() ) {
                if (count > 1 ) {
                    System.out.println("\nEnter [b] to go back, or any other key to try again.");
                    String choice = InputHelper.readLine();
                    if (choice.equalsIgnoreCase("b")) {
                        throw new PageBackException(); // signal upper controller to go back
                    } else{
                        System.out.println("Please try again.");
                        register(controller);
                    }
                }
                ChangePage.changePage();
                System.out.println("Please enter all the fields");
                continue;
            }

            Map<String,String> attributes = new HashMap<>();
            attributes.put("company",company);
            attributes.put("department",department);
            attributes.put("position",position);

            try {
                controller.registerRep(userId,name,password,attributes);
                System.out.println("Register successfully!");
                System.out.println("Please wait for account to be approved");
                try {
                    System.in.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                throw new PageBackException();

            }catch (AuthenticationException | IllegalArgumentException e){
                System.out.println("Registration failed: "+e.getMessage());
                System.out.println("\nEnter [b] to go back, or any other key to retry.");
                String choice = InputHelper.readLine();
                if (choice.equalsIgnoreCase("b")) {
                    throw new PageBackException();
                }else {
                    System.out.println("Please try again.");
                    register(controller);
                }
            }



        }
    }
}
