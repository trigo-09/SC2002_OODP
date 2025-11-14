package boundary.terminal;

import controller.control.SystemController;
import util.exceptions.PageBackException;
import util.io.InputHelper;
import util.io.ChangePage;
import util.io.GraphicLogo;

/**
 * UI class to show welcome page of system
 * Displaying menu to login, register or exit the system
 * User is promopted for input and its validated before calling relevant methods
 */
public class Welcome {

    /**
     *  welcome page of the system
     * @param controller system controller
     */
    public static void welcome(SystemController controller) {
        ChangePage.changePage();
        System.out.println(GraphicLogo.WELCOME_LOGO);
        System.out.print("\n");
        System.out.println("\nWelcome to Internship Placement Management System!");
        System.out.print("\n");
        System.out.println(GraphicLogo.SEPARATOR);
        System.out.println("Please enter your choice to continue");
        boolean run = true;
        while (run) {
            System.out.println("\t1. Login");
            System.out.println("\t2. Register");
            System.out.println("\t3. Exit");
            System.out.print("Enter your choice (1-3): ");
            int choice = InputHelper.readInt();
            switch (choice) {
                case 1 -> {
                    try {
                        LoginPageUI.login(controller);
                    }catch (PageBackException e) {
                        Welcome.welcome(controller);
                    }
                }
                case 2 -> {
                    try {
                        LoginPageUI.register(controller);
                    }catch (PageBackException e) {
                        Welcome.welcome(controller);
                    }
                }
                case 3 -> {
                    try {
                        controller.shutdown();
                        run = false;
                    }catch (PageBackException e) {
                        Welcome.welcome(controller);
                    }
                }
                default -> Welcome.welcome(controller);
            }
        }
    }
}