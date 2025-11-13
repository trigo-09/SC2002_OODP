package boundary.terminal;

import boundary.AttributeGetter;
import controller.control.user.UserController;
import entity.user.User;
import util.exceptions.PageBackException;
import util.io.ChangePage;
import util.io.InputHelper;

public class ChangePasswordUI {
    public static void handleChangePassword(UserController userController, User user){
        boolean retry = true;

        while (retry) {
            ChangePage.changePage();
            String oldPass = AttributeGetter.getPassword("Enter old password: ");
            String newPass = AttributeGetter.getPassword("Enter new password: ");
            String confirmPass = AttributeGetter.getPassword("Confirm new password: ");

            try {
                userController.changePassword(oldPass, newPass, user, confirmPass);
                break;
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
                        case 2 -> {
                            validChoice = true;
                            throw new PageBackException();
                        }
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
