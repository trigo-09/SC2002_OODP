package boundary.usermenu;

import boundary.FilterUI;
import boundary.terminal.ChangePasswordUI;
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
        int choice = -1;
        while (choice != 0) {
            ChangePage.changePage();
            System.out.println(GraphicLogo.SEPARATOR);
            System.out.println("Welcome to Company Representative Menu!");
            System.out.println("Hello, "+repController.getRep().getUserName() +"!\n");
            System.out.println("\t1. Create Internship Opportunity");
            System.out.println("\t2. Manage Your Internship Opportunities");
            System.out.println("\t3. Change Password");
            System.out.println("\t4. Update Filter");
            System.out.println("\t0. Logout");
            System.out.println(GraphicLogo.SEPARATOR +"\n");
            System.out.print("Enter your choice: ");
            choice = InputHelper.readInt();

            try {
            switch (choice) {
                case 1 -> createInternshipUI();
                case 2 -> manageInternshipsUI();
                case 3 -> ChangePasswordUI.handleChangePassword(systemController, repController, repController.getRep());
                case 4 -> FilterUI.update(repController.getFilter());
                case 0 -> {
                    System.out.println("Logging out...");
                    systemController.mainMenu();
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
            }catch (PageBackException e){displayMenu();}
        }
    }

     /**
      * Prompt the user for internship fields (title, description, level, majors, dates, slots),
      * validate inputs (e.g., closing date ≥ opening date, slots between 1 and 10), and create the opportunity.
      * Business-rule violations (e.g., max postings reached) are caught and shown.
      */
    private void createInternshipUI() {
        ChangePage.changePage();
        System.out.println("Create Internship Opportunity");
        System.out.println(GraphicLogo.SEPARATOR + "\n");
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
                System.out.println("Please try again");
                System.out.printf("%-20s" ,"Level (Basic / Intermediate / Advanced):");
            }
        }
        
        // Currently only supports a single preferred major input (or 'Any')
        System.out.print("Preferred Major or 'Any': ");
        String preferredMajor = InputHelper.readLine();
        
        System.out.print("Enter Opening Date (DD-MM-YYYY):");
        LocalDate openingDate = InputHelper.readOpenDate();
        System.out.print("Enter Closing Date (DD-MM-YYYY): ");

        // Re-prompt until closing date is after opening date
        LocalDate closingDate;
        while (true) {
            closingDate = InputHelper.readCloseDate();
            if (!closingDate.isBefore(openingDate)) break;
            System.out.print("Closing date cannot be before opening date. Please try again: ");
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
        systemController.update();
        } catch (IllegalArgumentException | IllegalStateException | SecurityException | MaxExceedException | ObjectAlreadyExistsException e) {
            System.out.println("Error creating internship: " + e.getMessage());
        }
        InputHelper.pause();
    }

     /**
      * List the representative's internships and allow actions such as toggling visibility
      * and listing and accepting/rejecting applications. Errors are caught and shown.
      */
    private void manageInternshipsUI() {
        int choice = -1;
        List<InternshipOpportunity> filteredInternships = repController.getFilteredInternships(repController.getInternships(), repController.getFilter());
        while (choice != 0) {
            ChangePage.changePage();
            System.out.println("Your Internship Opportunities");
            System.out.println(GraphicLogo.LONG_SEP);
            if (filteredInternships.isEmpty()) {
                System.out.println("No internship opportunities yet");
                InputHelper.pause();
                return;
            }
            DisplayableViewer.displayList(filteredInternships);
            System.out.println("Actions:");
            System.out.println("\t0. Back to Main Menu");
            System.out.println("\t1. Set Internship Visibility");
            System.out.println("\t2. Edit Internship");
            System.out.println("\t3. Manage Applications for an Internship");
            System.out.println("\t4. Delete Internship");
            System.out.println(GraphicLogo.LONG_SEP + "\n");
            System.out.print("Enter your choice: ");
            choice = InputHelper.readInt();
            if (choice < 0 || choice > 4) {
                System.out.println("Invalid choice. Please try again.");
                InputHelper.pause();
                continue;
            }
            break;
        }


            switch (choice) {
                case 1 ->   handleVisibility();
                case 2 ->   handleEditInternship();
                case 3 ->   handleApplications();
                case 4 ->   manageDeleteInternship();
                case 0 ->   {
                    System.out.println("Returning to main menu...");
                }
                default ->  System.out.println("Invalid choice. Please try again.");
            }
    }


     /**
      * Handles toggling the visibility status of internship.
      * Display approved internship and then prompts for selection, and allows toggling visibility between visible or hidden.
      * Validations are performed and appropriate prompts are displayed for errors.
      * The method performs the following steps:
      * 1. Displays the approved internships after filtering.
      * 2. select an internship
      * 3. set the visibility to visible, hidden, or return
      * 4. Updates the visibility
      */
    private void handleVisibility() {
        ChangePage.changePage();
        List<InternshipOpportunity> filteredInternships = repController.getFilteredInternships(repController.getInternships(), repController.getFilter());
        System.out.println("Toggle Internship Opportunities Visibility");
        System.out.println(GraphicLogo.LONG_SEP);
        List<InternshipOpportunity> approved = repController.getApprovedInternships(filteredInternships);
        if (approved.isEmpty()){
            System.out.println("No approved Internships found to toggle visibility...");
            InputHelper.pause();
            manageInternshipsUI();
        }
        DisplayableViewer.displayList(approved);
        int index;
        while (true){
            System.out.print("Enter index of internship to edit (Enter [0] to return): ");
            index = InputHelper.readInt();
            if (index == 0){
                manageInternshipsUI();
                return;
            }
            else if (index<1 || index > approved.size()){
                System.out.println("PLease enter a valid index.");
            }
            else {
                break;
            }
        }
        ChangePage.changePage();
        DisplayableViewer.displaySingle(approved.get(index-1));
        String internId = approved.get(index-1).getId();
        System.out.print("Set visibility to Visible [1] or Hidden [0], (Enter [2] to return): ");
        boolean run = true;
        while (run) {
            int visChoice = InputHelper.readInt();
            if (visChoice == 2){
                handleVisibility();
                return;
            }
            else if (visChoice == 0 || visChoice == 1){
                boolean visibility = (visChoice == 1);

                try {
                    repController.toggleVisibility(internId, visibility);
                    System.out.println("Internship visibility updated successfully.");
                    systemController.update();
                    run = false;
                } catch (IllegalArgumentException | IllegalStateException | SecurityException e) {
                    System.out.println("Error updating visibility: " + e.getMessage());
                    InputHelper.pause();
                }
            }
            else {
                System.out.println("Invalid input");
            }
        }
        InputHelper.pause();
        handleVisibility();

    }

     /**
      * Handles UI for editing internship
      *
      * The method allows users to view, select, and edit specific internship opportunities from
      * a filtered list of pending internships.
      * the user can modify multiple
      * fields such as the internship title, description, preferred major, opening date, closing date,
      * available slots, or internship level.
      * Changes are saved upon confirmation, and the updated
      * internship data is validated and persisted.
      *
      */
    private void handleEditInternship() {
        ChangePage.changePage();
        List<InternshipOpportunity> filteredInternships = repController.getFilteredInternships(repController.getInternships(), repController.getFilter());
        System.out.println("Edit Internship Opportunities");
        System.out.println(GraphicLogo.LONG_SEP);
        List<InternshipOpportunity> pending = repController.getPendingInternships(filteredInternships);
        if (pending.isEmpty()){
            System.out.println("No pending Internships found for editing...");
            InputHelper.pause();
            manageInternshipsUI();
            return;
        }
        DisplayableViewer.displayList(pending);

        int index;
        while (true){
            System.out.print("Enter index of internship to edit (Enter [0] to return): ");
            index = InputHelper.readInt();
            if (index == 0){
                manageInternshipsUI();
                return;
            }
            else if (index<1 || index > pending.size()){
                System.out.println("PLease enter a valid index.");
            }
            else {
                break;
            }
        }

        String internId = pending.get(index-1).getId();
        ChangePage.changePage();
        DisplayableViewer.displaySingle(pending.get(index-1));
        // Check if internship exists before prompting for fields
        try {repController.validateInternshipId(internId);}
        catch (IllegalArgumentException | SecurityException e) {
            System.out.println("Error: " + e.getMessage());
            InputHelper.pause();
            return;
        }

        String title =null;String description=null;String preferredMajor = null;LocalDate openingDate = null;
        LocalDate closingDate = null;Integer slot =null;InternshipLevel level =null;
        boolean done = false;
        while (!done){
            ChangePage.changePage();
            System.out.println("Edit Internship Opportunities");
            System.out.println(GraphicLogo.SEPARATOR + "\n");
            DisplayableViewer.displaySingle(pending.get(index-1));
            System.out.println("\t1. Title");
            System.out.println("\t2. Description");
            System.out.println("\t3. Preferred Major");
            System.out.println("\t4. Opening Date");
            System.out.println("\t5. Closing Date");
            System.out.println("\t6. Slots" );
            System.out.println("\t7. Level");
            System.out.println("\t0. Save and return");
            System.out.println(GraphicLogo.SEPARATOR + "\n");
            System.out.print("Choice: ");
            int choice = InputHelper.readInt();
            switch (choice) {
                case 1 -> {
                    System.out.print("New Title (Blank to keep): ");
                    String t = InputHelper.readLine();
                    if (!t.isEmpty()) title = t;
                    System.out.println("saved");
                    InputHelper.pause();
                }
                case 2 -> {
                    System.out.print("New Description (Blank to keep): ");
                    String describe = InputHelper.readLine();
                    if (!describe.isEmpty()) description = describe;
                    System.out.println("saved");
                    InputHelper.pause();
                }
                case 3 -> {
                    System.out.print("New Preferred Major (Blank to keep): ");
                    String major = InputHelper.readLine();
                    if (!major.isEmpty()) preferredMajor = major;
                    System.out.println("saved");
                    InputHelper.pause();
                }
                case 4 -> {
                    LocalDate temp = InputHelper.readOptionalOpenDate();
                    if(temp != null) openingDate = temp;
                    System.out.println("saved");
                    InputHelper.pause();
                }
                case 5 -> {
                    LocalDate temp;
                    while (true) {
                        temp = InputHelper.readOptionalCloseDate();
                        if (temp != null && openingDate != null && temp.isBefore(openingDate)) {
                            System.out.println("Closing date cannot be before opening date. Please try again.");
                        }
                        else {
                            break;
                        }
                    }
                    if (temp !=null) closingDate = temp;
                    System.out.println("saved");
                    InputHelper.pause();
                }
                case 6 -> {
                    Integer slots;
                    while(true) {
                        System.out.print("New Number of Slots(Blank to keep): ");
                        slots = InputHelper.readOptionalInt();
                        try {
                            repController.checkValidSlots(slots, true);
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                            System.out.print("Please try again: ");
                        }
                    }
                    if (slots !=null) slot = slots.intValue();
                    System.out.println("saved");
                    InputHelper.pause();
                }
                case 7 -> {

                    // Re-prompt until valid level is entered
                    InternshipLevel lvl;
                    while(true) {
                        try {
                            System.out.print("New Level (Basic / Intermediate / Advanced, leave blank to keep current): ");
                            String inputLevel = InputHelper.readLine();
                            if (inputLevel.isEmpty()){
                                lvl = null;
                            }
                            else {
                                lvl = repController.parseLevel(inputLevel, true);
                            }
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error parsing internship level: " + e.getMessage());
                            System.out.print("Please try again");
                        }
                    }
                    if(lvl != null){level=lvl;}
                    System.out.println("saved");
                    InputHelper.pause();
                }
                case 0 -> {
                    try {
                        repController.editInternship(
                                internId,
                                title,
                                description,
                                preferredMajor,
                                openingDate,
                                closingDate,
                                slot,
                                level
                        );
                        System.out.println("Internship updated successfully.");
                        systemController.update();
                        done = true;
                    } catch (IllegalArgumentException | IllegalStateException | SecurityException | ObjectNotFoundException e) {
                        System.out.println("Error updating internship: " + e.getMessage());
                    }
                }
            }
        }
        InputHelper.pause();
        handleEditInternship();
    }

     /**
      * manage internship applications, including displaying filtered internships, select an internship for application management, and processing
      * applications
      * 1. Displays a list of approved internships
      * 2. select an internship
      * 3.  view approved or pending applications
      * 4. For pending applications, staff can process applications
      * 5. Displays error messages for invalid inputs or any exceptions
      */
     private void handleApplications() {
         ChangePage.changePage();
         List<InternshipOpportunity> filteredInternships = repController.getFilteredInternships(repController.getInternships(), repController.getFilter());
         List<InternshipOpportunity> approved = repController.getApprovedInternships(filteredInternships);
         if (approved.isEmpty()) {
             System.out.println("No Internship with application found.");
             InputHelper.pause();
             manageInternshipsUI();
             return;
         }
         DisplayableViewer.displayList(approved);
         int index;

         while (true) {
             System.out.print("Enter index of internship to manage applications (Enter 0 to go back to menu): ");
             index = InputHelper.readInt();
             if (index == 0) {
                 manageInternshipsUI();
                 return;
             } else if (index < 1 || index > approved.size()) {
                 System.out.println("PLease enter a valid index.");
             } else {
                 break;
             }
         }

         int choice;
         while (true) {
             ChangePage.changePage();
             System.out.println(GraphicLogo.LONG_SEP);
             InternshipOpportunity intern = approved.get(index - 1);
             DisplayableViewer.displaySingle(intern);
             System.out.println("Actions:");
             System.out.println("\t0. Back to Main Menu");
             System.out.println("\t1. View Approved Applications");
             System.out.println("\t2. View Pending Applications");
             System.out.println(GraphicLogo.LONG_SEP + "\n");
             System.out.print("Enter your choice: ");
             choice = InputHelper.readInt();
             if (choice < 0 || choice > 2) {
                 System.out.println("Please enter a valid choice.");
             } else {
                 break;
             }
         }
         if (choice == 0) {
             handleApplications();
             return;
         } else if (choice == 1) {
             ChangePage.changePage();
             String internId = approved.get(index - 1).getId();
             List<Application> applications = repController.getApproveApplications(internId);
             try {
                 if (applications.isEmpty()) {
                     System.out.println("No Internship with approved application found.");
                     InputHelper.pause();
                     handleApplications();
                     return;
                 }
                 DisplayableViewer.displayList(applications, repController);
             } catch (IllegalArgumentException | IllegalStateException | SecurityException e) {
                 System.out.println("Error retrieving applications: " + e.getMessage());
                 InputHelper.pause();
                 handleApplications();
             }
             InputHelper.pause();
             handleApplications();

         } else {

             ChangePage.changePage();
             String internId = approved.get(index - 1).getId();
             List<Application> applications = repController.getPendApplications(internId);
             try {
                 if (applications.isEmpty()) {
                     System.out.println("(No applications yet)");
                     InputHelper.pause();
                     handleApplications();
                     return;
                 }
                 DisplayableViewer.displayList(applications, repController);

             } catch (IllegalArgumentException | IllegalStateException | SecurityException e) {
                 System.out.println("Error retrieving applications: " + e.getMessage());
                 InputHelper.pause();
                 handleApplications();
             }

             System.out.print("1: Process Application, 0: back: ");
             int appChoice = InputHelper.readInt();
             int applicationIndex;
             if (appChoice == 1) {
                 while (true) {
                     System.out.print("Enter index of the Application ID (Enter [0] to return): ");
                     applicationIndex = InputHelper.readInt();

                     if (applicationIndex == 0) {
                         handleApplications();
                         return;
                     } else if (applicationIndex < 1 || applicationIndex > repController.getPendApplications(internId).size()) {
                         System.out.println("Please enter a valid index");
                     } else {
                         break;
                     }
                 }

                 ChangePage.changePage();
                 DisplayableViewer.displaySingle(applications.get(applicationIndex - 1), repController);
                 String appId = applications.get(applicationIndex - 1).getApplicationId();
                 System.out.print("Accept (1) or Reject (0) the application? ");
                 int decision = InputHelper.readInt();

                 try {
                     if (decision == 1) {
                         repController.approveApp(appId);
                         System.out.println("Application accepted.");
                         systemController.update();
                     } else {
                         repController.rejectApp(appId);
                         System.out.println("Application rejected.");
                         systemController.update();
                     }
                 } catch (ObjectNotFoundException | IllegalStateException | SecurityException | MaxExceedException e) {
                     System.out.println("Error approving/rejecting application: " + e.getMessage());
                 }
                 InputHelper.pause();
                 handleApplications();
             }
         }
     }

     /**
      * Handles the deletion process for internship opportunities.
      *
      * 1. Checks if there are any internships available for deletion
      * 2. Displays the list of delete-able internship
      * 3. confirm the internship deletion or return to the menu.
      *    - If confirmed, the internship is deleted
      */
     private void manageDeleteInternship() {
        try{
            ChangePage.changePage();
            List<InternshipOpportunity> filteredInternships = repController.getFilteredInternships(repController.getInternships(), repController.getFilter());
            System.out.println("Internship Opportunities for Deletion");
            System.out.println(GraphicLogo.LONG_SEP);
            List<InternshipOpportunity> pending = repController.getCanDeleteInternships(filteredInternships);
            if (pending.isEmpty()){
                System.out.println("No pending Internships found for deletion...");
                InputHelper.pause();
                manageInternshipsUI();
                throw new PageBackException();
            }
            DisplayableViewer.displayList(pending);
            int index;
            while (true){
                System.out.print("Enter index of internship to delete (Enter [0] to return): ");
                index = InputHelper.readInt();
                if (index == 0){
                    manageInternshipsUI();
                    throw new PageBackException();
                }
                else if (index<1 || index > pending.size()){
                    System.out.println("PLease enter a valid index.");
                }
                else {
                    break;
                }
            }

            String internId = pending.get(index-1).getId();
            ChangePage.changePage();
            System.out.println("Internship Opportunity for Deletion");
            System.out.println(GraphicLogo.LONG_SEP);
            DisplayableViewer.displaySingle(pending.get(index-1));
            // Check if internship exists before prompting for fields
            try {repController.validateInternshipId(internId);}
            catch (IllegalArgumentException | SecurityException e) {
                System.out.println("Error: " + e.getMessage());
                InputHelper.pause();
                return;
            }
            int choice;
            while (true) {
                System.out.print("1: Confirm Delete Internship, 0: back: ");
                choice = InputHelper.readInt();
                if (choice == 0) {
                    manageDeleteInternship();
                    break;
                }
                else if (choice == 1) {
                    try {
                        repController.deleteInternship(internId);
                        System.out.println("Internship Deleted.");
                        systemController.update();
                        InputHelper.pause();
                        manageDeleteInternship();
                    }catch (ObjectNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                        InputHelper.pause();
                    }
                }
                else{
                    System.out.println("Please enter a valid index.");
                }
            }
        } catch(PageBackException e){
            displayMenu();
        }


    }
}
