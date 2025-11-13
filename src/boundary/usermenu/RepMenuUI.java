package boundary.usermenu;

import boundary.FilterUI;
import boundary.viewer.DisplayableViewer;
import controller.control.SystemController;
import controller.control.user.RepController;
import entity.application.Application;
import entity.internship.InternshipLevel;
import entity.internship.InternshipOpportunity;
import java.time.LocalDate;
import java.util.List;
import util.exceptions.*;
import util.io.ChangePage;
import util.io.GraphicLogo;
import util.io.InputHelper;

 /**
  * Console menu for company representatives to create and manage internship opportunities.
  */
public class RepMenuUI {
    private final RepController repController;
    private final SystemController systemController;

     /**
      * Creates a representative menu bound to the given controller.
      *
      * @param repController controller handling representative operations
      */
    public RepMenuUI(RepController repController, SystemController systemController) {
        this.repController = repController;
        this.systemController = systemController;
    }
    /**
     * Display the main menu and handle user actions until logout is chosen.
     * All business-rule and authorization errors are caught and shown to the user.
     */
    public void displayMenu() {
        ChangePage.changePage();
        int choice = -1;
        while (choice != 0) {
            System.out.println("Welcome to Company Representative Menu!");
            System.out.println(GraphicLogo.SEPARATOR);
            System.out.println("1) Create Internship Opportunity");
            System.out.println("2) Manage Your Internship Opportunities");
            System.out.println("3) Change Password");
            System.out.println("0) Logout");
            System.out.print("Enter your choice: ");
            choice = InputHelper.readInt();

            switch (choice) {
                case 1 -> createInternshipUI();
                case 2 -> manageInternshipsUI(repController);
                case 3 -> changePasswordUI();
                case 0 -> {
                    System.out.println("Logging out...");
                    systemController.mainMenu();
                }
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
        ChangePage.changePage();
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
        } catch (IllegalArgumentException | IllegalStateException | SecurityException | MaxExceedException | ObjectAlreadyExistsException e) {
            System.out.println("Error creating internship: " + e.getMessage());
        }
        InputHelper.pause();
    }

     /**
      * List the representative's internships and allow actions such as toggling visibility
      * and listing and accepting/rejecting applications. Errors are caught and shown.
      */
    private void manageInternshipsUI(RepController repController) {
        ChangePage.changePage();
        int choice = -1;
        while (choice != 0) {
            System.out.println("=== Your Internship Opportunities (Filtered) ===");
            List<InternshipOpportunity> filteredInternships = repController.getFilteredInternships(repController.getRep().getInternships(), repController.getFilter());

            if (filteredInternships.isEmpty()) {
                System.out.println("(No opportunities yet)");
                InputHelper.pause();
                return;

            } else {
                DisplayableViewer.displayList(filteredInternships);
            }

            System.out.println("Actions:");
            System.out.println("0) Back to Main Menu");
            System.out.println("1) Set Internship Visibility");
            System.out.println("2) Edit Internship");
            System.out.println("3) Manage Applications for an Internship");
            System.out.println("4) Manage Filter");

            while(true) {
                System.out.print("Enter your choice: ");
                choice = InputHelper.readInt();
                if (filteredInternships.isEmpty() && (choice == 1 || choice == 2 || choice == 3)) {
                    System.out.println("No internships available to manage. Please choose another option.");
                    continue;
                }
                if (choice < 0 || choice > 4) {
                    System.out.println("Invalid choice. Please try again.");
                    continue;
                }
                break;
            }

            switch (choice) {
                case 1 ->   handleVisibility(filteredInternships);
                case 2 ->   handleEditInternship(filteredInternships);
                case 3 ->   handleApplications(filteredInternships);
                case 4 ->   FilterUI.update(repController.getFilter());
                case 0 ->   System.out.println("Returning to main menu...");
                default ->  System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void changePasswordUI() {
        ChangePage.changePage();
        System.out.print("Enter your current password: ");
        String currentPassword = InputHelper.readLine();
        System.out.print("Enter your new password: ");
        String newPassword = InputHelper.readLine();
        System.out.print("Confirm your new password: ");
        String confirmPassword = InputHelper.readLine();

        try {
            repController.changePassword(currentPassword, newPassword, repController.getRep(), confirmPassword);
            System.out.println("Password changed successfully.");
        } catch (Exception e) {
            System.out.println("Error changing password: " + e.getMessage());
        }
        InputHelper.pause();
    }

    private void handleVisibility(List<InternshipOpportunity> filteredInternships) {
        ChangePage.changePage();
        DisplayableViewer.displayList(filteredInternships);
        int index;

        while (true){
            System.out.print("Enter index of internship to edit: ");
            index = InputHelper.readInt();
            if (index == 0){
                return;
            }
            else if (index<1 || index > filteredInternships.size()){
                System.out.println("PLease enter a valid index.");
                break;
            }
        }
        String internId = filteredInternships.get(index-1).getId();
        System.out.print("Set visibility to Visible (1) or Hidden (0): ");
        int visChoice = InputHelper.readInt();
        boolean visibility = visChoice == 1;

        try {
            repController.toggleVisibility(internId, visibility);
            System.out.println("Internship visibility updated successfully.");
        } catch (IllegalArgumentException | IllegalStateException | SecurityException e) {
            System.out.println("Error updating visibility: " + e.getMessage());
        }
        InputHelper.pause();
    }

    private void handleEditInternship(List<InternshipOpportunity> filteredInternships) {
        ChangePage.changePage();
        DisplayableViewer.displayList(filteredInternships);
        int index;

        while (true){
            System.out.print("Enter index of internship to edit: ");
            index = InputHelper.readInt();
            if (index == 0){
                return;
            }
            else if (index<1 || index > filteredInternships.size()){
                System.out.println("PLease enter a valid index.");
                break;
            }
        }

        String internId = filteredInternships.get(index-1).getId();

        // Check if internship exists before prompting for fields
        try {repController.validateInternshipId(internId);}
        catch (IllegalArgumentException | SecurityException e) {
            System.out.println("Error: " + e.getMessage());
            InputHelper.pause();
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

        System.out.print("New Opening Date (DD-MM-YYYY):");
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
        InputHelper.pause();
    }

    private void handleApplications(List<InternshipOpportunity> filteredInternships) {
        ChangePage.changePage();
        DisplayableViewer.displayList(filteredInternships);
        int index;

        while (true){
            System.out.print("Enter index of internship to edit: ");
            index = InputHelper.readInt();
            if (index == 0){
                return;
            }
            else if (index<1 || index > filteredInternships.size()){
                System.out.println("PLease enter a valid index.");
                break;
            }
        }
        String internId = filteredInternships.get(index-1).getId();

        try {
            List<Application> applications = repController.getApplications(internId);
            if (applications.isEmpty()) {
                System.out.println("(No applications yet)");
                InputHelper.pause();
                return;
            } else {
                applications.forEach(a -> System.out.println("- " + a));
            }
            InputHelper.pause();
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
            InputHelper.pause();
        }
    }


}
