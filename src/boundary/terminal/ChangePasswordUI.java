package boundary.terminal;

import boundary.AttributeGetter;
import controller.control.SystemController;
import controller.control.user.UserController;
import entity.user.User;
import util.exceptions.PageBackException;
import util.io.ChangePage;
import util.io.InputHelper;

/**
 * This class is UI for change of user's password.
 */
public class ChangePasswordUI {

    /**
     * prompts user for old password, new password and confirm password.
     * work with controller to validate old password and update new
     * Display any exception or errors
     * @param systemController the system controller to navigate back to the main menu if necessary
     * @param userController the user controller used to handle the password change functionality
     * @param user the user who is requesting the password change
     */
    public static void handleChangePassword(SystemController systemController, UserController userController, User user){
        boolean retry = true;

        while (retry) {
            ChangePage.changePage();
            String oldPass = AttributeGetter.getPassword("Enter old password: ");
            String newPass = AttributeGetter.getPassword("Enter new password: ");
            String confirmPass = AttributeGetter.getPassword("Confirm new password: ");

            try {
                userController.changePassword(oldPass, newPass, user, confirmPass);
                systemController.mainMenu();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                boolean validChoice = false;

                while (!validChoice) {
                    System.out.println();
                    System.out.println("1. Try again");
                    System.out.println("2. Return to main menu");
                    System.out.print("Enter choice ([1] or [2]): ");
                    int choice = InputHelper.readInt();

                    switch (choice) {
                        case 1 -> validChoice = true; // retry outer loop
                        case 2 -> throw new PageBackException();
                        default -> {
                            System.out.println("Invalid choice. Please enter [1] or [2].");
                            System.out.println();
                        }
                    }
                }
            }
        }
        InputHelper.pause();
        throw new PageBackException(); // return to main menu
    }
}
