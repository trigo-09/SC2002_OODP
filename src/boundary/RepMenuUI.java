package boundary;

import controller.control.user.RepController;
import entity.application.Application;
import entity.internship.InternshipLevel;
import entity.internship.InternshipOpportunity;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import util.exceptions.*;
import util.io.InputHelper;

 /**
  * Console menu for company representatives to create and manage internship opportunities.
  */
public class RepMenuUI {
    private final RepController repController;

     /**
      * Creates a representative menu bound to the given controller.
      *
      * @param repController controller handling representative operations
      */
    public RepMenuUI(RepController repController) {
        this.repController = repController;
    }
    /**
     * Display the main menu and handle user actions until logout is chosen.
     * All business-rule and authorization errors are caught and shown to the user.
     */
    public void displayMenu() {
        int choice = -1;
        while (choice != 0) {
            System.out.println("\n=== Company Representative Menu ===");
            System.out.println("1) Create Internship Opportunity");
            System.out.println("2) Manage Your Internship Opportunities");
            System.out.println("3) Change Password");
            System.out.println("0) Logout");
            System.out.print("Enter your choice: ");
            choice = InputHelper.readInt();

            switch (choice) {
                case 1 -> createInternshipUI();
                case 2 -> manageInternshipsUI();
                case 3 -> changePasswordUI();
                case 0 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

     /**
      * Prompt the user for internship fields (title, description, level, majors, dates, slots),
      * validate inputs (e.g., closing date ≥ opening date, slots between 1 and 10), and create the opportunity.
      * Business-rule violations (e.g., max postings reached) are caught and shown.
      */
    private void createInternshipUI() {
        System.out.println("=== Create Internship Opportunity ===");
        System.out.print("Title: ");
        String title = InputHelper.readLine();
        System.out.print("Description: ");
        String description = InputHelper.readLine();

        System.out.print("Level (Basic / Intermediate / Advanced): ");
        // Re-prompt until valid level is entered
        // Convert user input to InternshipLevel enum
        InternshipLevel level;
        while(true) {
            try {
                level = repController.parseLevel(InputHelper.readLine(), false);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Error parsing internship level: " + e.getMessage());
                System.out.print("Please try again: ");
            }
        }
        
        // Currently only supports a single preferred major input (or 'Any')
        System.out.print("Preferred Major or 'Any': ");
        String preferredMajor = InputHelper.readLine();
        
        System.out.println("Enter Opening Date (DD-MM-YYYY):");
        LocalDate openingDate = InputHelper.readDate();
        System.out.print("Enter Closing Date (DD-MM-YYYY): ");

        // Re-prompt until closing date is after opening date
        LocalDate closingDate;
        while (true) {
            closingDate = InputHelper.readDate();
            if (!closingDate.isBefore(openingDate)) break;
            System.out.println("Closing date cannot be before opening date. Please try again.");
        }

        // Re-prompt until valid number of slots is entered
        int numOfSlots;
        while(true) {
            System.out.print("Number of Slots (1..10): ");
            numOfSlots = InputHelper.readInt();
            try {
                repController.checkValidSlots(numOfSlots, false);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.print("Please try again: ");
            }
        }

        try {
            repController.createInternship(
                title,
                description,
                level,
                preferredMajor,
                openingDate,
                closingDate,
                numOfSlots
            );
        System.out.println("Internship opportunity created successfully.");
        } catch (IllegalArgumentException | IllegalStateException | SecurityException | MaxExceedException e) {
            System.out.println("Error creating internship: " + e.getMessage());
        }
        pause();
    }

     /**
      * List the representative's internships and allow actions such as toggling visibility
      * and listing and accepting/rejecting applications. Errors are caught and shown.
      */
    private void manageInternshipsUI() {
        int choice = -1;
        while (choice != 0) {
            System.out.println("=== Your Internship Opportunities ===");
            List<InternshipOpportunity> internships = repController.getInternships();
            if (internships.isEmpty()) {
                System.out.println("(No opportunities yet)");
            } else {
                internships.forEach(i -> System.out.println("- " + i));
            }

            System.out.println("Actions:");
            System.out.println("0) Back to Main Menu");
            System.out.println("1) Set Internship Visibility");
            System.out.println("2) Edit Internship");
            System.out.println("3) Manage Applications for an Internship");

            while(true) {
                System.out.print("Enter your choice: ");
                choice = InputHelper.readInt();
                if (internships.isEmpty() && (choice == 1 || choice == 2 || choice == 3)) {
                    System.out.println("No internships available to manage. Please choose another option.");
                    continue;
                }
                if (choice < 0 || choice > 3) {
                    System.out.println("Invalid choice. Please try again.");
                    continue;
                }
                break;
            }

            switch (choice) {
                case 1 ->   handleVisibility();
                case 2 ->   handleEditInternship();
                case 3 ->   handleApplications(); 
                case 0 ->   System.out.println("Returning to main menu...");
                default ->  System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void changePasswordUI() {
        System.out.print("Enter your current password: ");
        String currentPassword = InputHelper.readLine();
        System.out.print("Enter your new password: ");
        String newPassword = InputHelper.readLine();
        System.out.print("Confirm your new password: ");
        String confirmPassword = InputHelper.readLine();

        try {
            repController.changePassword(currentPassword, newPassword, repController.getRep(), confirmPassword);
            System.out.println("Password changed successfully.");
        } catch (IllegalArgumentException | AuthenticationException e) {
            System.out.println("Error changing password: " + e.getMessage());
        }
        pause();
    }

    private void handleVisibility() {
        System.out.print("Enter Internship ID to change visibility: ");
        String internId = InputHelper.readLine();
        System.out.print("Set visibility to Visible (1) or Hidden (0): ");
        int visChoice = InputHelper.readInt();
        boolean visibility = visChoice == 1;

        try {
            repController.toggleVisibility(internId, visibility);
            System.out.println("Internship visibility updated successfully.");
        } catch (IllegalArgumentException | IllegalStateException | SecurityException e) {
            System.out.println("Error updating visibility: " + e.getMessage());
        }
        pause();
    }

    private void handleEditInternship() {
        System.out.print("Enter Internship ID to edit: ");
        String internId = InputHelper.readLine();

        // Check if internship exists before prompting for fields
        try {repController.validateInternshipId(internId);}
        catch (IllegalArgumentException | SecurityException e) {
            System.out.println("Error: " + e.getMessage());
            pause();
            return;
        }
        System.out.println("Leave fields blank to keep current values.");
        System.out.print("New Title: ");
        String title = InputHelper.readLine();
        if (title.isEmpty()) title = null;

        System.out.print("New Description: ");
        String description = InputHelper.readLine();
        if (description.isEmpty()) description = null;

        System.out.print("New Preferred Major: ");
        String preferredMajor = InputHelper.readLine();
        if (preferredMajor.isEmpty()) preferredMajor = null;

        System.out.println("New Opening Date (DD-MM-YYYY):");
        LocalDate openingDate = InputHelper.readOptionalDate();
        System.out.print("New Closing Date (DD-MM-YYYY): ");
        // Re-prompt until closing date is after opening date
        LocalDate closingDate;
        while (true) {
            closingDate = InputHelper.readOptionalDate();
            if (closingDate != null && openingDate != null && closingDate.isBefore(openingDate)) {
                System.out.println("Closing date cannot be before opening date. Please try again.");
            } else {
                break;
            }
        }

        System.out.print("New Number of Slots: ");
        // Re-prompt until valid number of slots is entered
        Integer slots;
        while(true) {
            slots = InputHelper.readOptionalInt();
            try {
                repController.checkValidSlots(slots, true);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.print("Please try again: ");
            }
        }

        System.out.print("New Level (Basic / Intermediate / Advanced, leave blank to keep current): ");
        // Re-prompt until valid level is entered
        InternshipLevel level;
        while(true) {
            try {
                level = repController.parseLevel(InputHelper.readLine(), true);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Error parsing internship level: " + e.getMessage());
                System.out.print("Please try again: ");
            }
        }

        try {
            repController.editInternship(
                internId,
                title,
                description,
                preferredMajor,
                openingDate,
                closingDate,
                slots,
                level
            );
            System.out.println("Internship updated successfully.");
        } catch (IllegalArgumentException | IllegalStateException | SecurityException | ObjectNotFoundException e) {
            System.out.println("Error updating internship: " + e.getMessage());
        }
        pause();
    }

    private void handleApplications() {
        System.out.print("Enter Internship ID to manage applications: ");
        String internId = InputHelper.readLine();

        try {
            List<Application> applications = repController.getApplications(internId);
            if (applications.isEmpty()) {
                System.out.println("(No applications yet)");
                pause();
                return;
            } else {
                applications.forEach(a -> System.out.println("- " + a));
            }
            pause();
        } catch (IllegalArgumentException | IllegalStateException | SecurityException e) {
            System.out.println("Error retrieving applications: " + e.getMessage());
        }

        System.out.print("1: Accept or Reject an Application, 0: back: ");
        int appChoice = InputHelper.readInt();

        if (appChoice == 1) {
            System.out.print("Enter Application ID: ");
            String appId = InputHelper.readLine();
            System.out.print("Accept (1) or Reject (0) the application? ");
            int decision = InputHelper.readInt();

            try {
                if (decision == 1) {
                    repController.approveApp(appId);
                    System.out.println("Application accepted.");
                } else {
                    repController.rejectApp(appId);
                    System.out.println("Application rejected.");
                }
            } catch (ObjectNotFoundException | IllegalStateException | SecurityException | MaxExceedException e) {
                System.out.println("Error approving/rejecting application: " + e.getMessage());
            }
            pause();
        }
    }

    /**
     * Pauses execution until the user presses Enter.
     * This is useful for allowing the user to read messages before continuing.
     */
    private void pause() {
        System.out.print("Press Enter to continue...");
        InputHelper.readLine();
    }
}
