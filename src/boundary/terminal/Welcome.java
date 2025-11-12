package boundary.terminal;

import controller.control.SystemController;
import util.exceptions.PageBackException;
import util.io.InputHelper;
import util.ui.ChangePage;
import util.ui.GraphicLogo;

public class Welcome {
    public static void welcome(SystemController controller) {
        ChangePage.changePage();
        System.out.println(GraphicLogo.WELCOME_LOGO);
        System.out.println("\nWelcome to Internship Placement Management System!");
        System.out.println(GraphicLogo.SEPARATOR);
        System.out.println("Please enter your choice to continue");
        InputHelper.printMenuItem(1,"Login");
        InputHelper.printMenuItem(2,"Register");
        InputHelper.printMenuItem(3,"Exit");
        System.out.print("Enter your choice (1-3): ");
        try {
            while (true) {
                int choice = InputHelper.readInt();
                switch (choice) {
                    case 1 -> LoginPageUI.login(controller);
                    case 2 -> LoginPageUI.register(controller);
                    case 3 -> controller.shutdown();
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (PageBackException e) {
        }
    }
}