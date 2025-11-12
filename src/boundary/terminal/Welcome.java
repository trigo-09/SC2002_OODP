package boundary.terminal;

import controller.control.SystemController;
import util.exceptions.PageBackException;
import util.io.InputHelper;
import util.ui.ChangePage;
import util.ui.GraphicLogo;

/**
 * UI class to show welcome page of system
 */
public class Welcome {

    /**
     *  welcome page of the system
     * @param controller system controller
     */
    public static void welcome(SystemController controller) {
        ChangePage.changePage();
        System.out.println(GraphicLogo.WELCOME_LOGO);
        System.out.println("\nWelcome to Internship Placement Management System!");
        System.out.println(GraphicLogo.SEPARATOR);
        System.out.println("Please enter your choice to continue");
        boolean run = true;
        while (run) {
            InputHelper.printMenuItem(1,"Login");
            InputHelper.printMenuItem(2,"Register");
            InputHelper.printMenuItem(3,"Exit");
            System.out.print("Enter your choice (1-3): ");
            int choice = InputHelper.readInt();
            switch (choice) {
                case 1 -> {
                    try {
                        LoginPageUI.login(controller);
                    }catch (PageBackException e) {}
                }
                case 2 -> {
                    try {
                        LoginPageUI.register(controller);
                    }catch (PageBackException e) {}
                }
                case 3 -> {
                    try {
                        controller.shutdown();
                        run = false;
                    }catch (PageBackException e) {}
                }
                default -> Welcome.welcome(controller);
            }
        }
    }
}