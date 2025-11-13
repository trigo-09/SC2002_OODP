package boundary.usermenu;

import boundary.AttributeGetter;
import boundary.viewer.DisplayableViewer;
import controller.control.SystemController;
import controller.control.user.StudentController;
import entity.application.Application;
import entity.internship.InternshipLevel;
import entity.internship.InternshipOpportunity;
import java.time.LocalDate;
import java.util.*;
import entity.FilterCriteria;
import util.exceptions.PageBackException;
import util.io.InputHelper;
import util.io.ChangePage;

public class StudentUI {
    private final StudentController studentController;
    private final SystemController systemController;

    public StudentUI(SystemController systemController, StudentController studentController) {
        this.systemController = systemController;
        this.studentController = studentController;
    }

    public void menu() {
        ChangePage.changePage();
        System.out.println("=== Student Menu ===");
        InputHelper.printMenuItem(1, "View / Apply Internships");
        InputHelper.printMenuItem(2, "Manage Applications");
        InputHelper.printMenuItem(3, "Change Password");
        InputHelper.printMenuItem(4, "Change Filter Setting");
        InputHelper.printMenuItem(5, "Logout");
        System.out.print("Enter your choice (1-5): ");

        try {
            while (true) {
                int choice = InputHelper.readInt();
                switch (choice) {
                    case 1 -> handleInternships();
                    case 2 -> handleApplications();
                    case 3 -> handleChangePass();
                    case 4 -> handleChangeFilter();
                    case 5 -> {
                        System.out.println("Logging out...");
                        systemController.mainMenu();
                    }
                    default -> {
                        System.out.println("Invalid choice. Please try again.");
                        InputHelper.pause();
                        throw new PageBackException();
                    }

                }
            }
        } catch (PageBackException e){
            menu();
        }
    }

    /**
     *  Allow user to view internships based on their filter
     *  If no internships are available then will just print "No internships found"
     *
     */
    private void handleInternships(){
        ChangePage.changePage();
        List<InternshipOpportunity> internshipList = studentController.viewFilteredInternships(studentController.getFilter());
        if (internshipList.isEmpty()) {
            System.out.println("No internships found.");
            InputHelper.pause();
            throw new PageBackException();
        }

        DisplayableViewer.displayList(internshipList);
        int choice;

        while (true) {
            System.out.println("Please select an action:");
            System.out.println("0) Back to Main Menu");
            System.out.println("1) Apply for Internship");
            System.out.println("Enter your choice (0/1): ");
            choice = InputHelper.readInt();

            if  (choice == 0) {
                System.out.println("Returning to Main Menu...");
                throw new PageBackException();
            }
            else if (choice == 1) {
                break;
            }
            else{
                System.out.println("Invalid choice. Please try again.");
            }
        }

        int index;

        while (true){
            System.out.println("Select index of internship you want to apply for. (Enter [0] to go back to menu): ");
            index = InputHelper.readInt();
            if (index == 0) {
                throw new PageBackException();
            }
            else if(index >= 1 && index <= internshipList.size()){
                break;
            }
            else{
                System.out.println("Invalid choice. Please enter a valid index.");
            }
        }

        InternshipOpportunity internship = internshipList.get(index-1);

        try {
            studentController.applyInternship(internship.getId());
            System.out.println("Successfully applied.");
        }catch (Exception e){
            System.out.println("ERROR: "+e.getMessage());
        }

        InputHelper.pause();
        throw new PageBackException();
    }

    /**
     * Allows student to view/accept/withdraw their applications
     * If student has not applied to any internships, print "No applications found"
     */
    private void handleApplications(){
        System.out.println("Your Internship Applications");
        List<Application> appList = studentController.myApplications();
        if (appList.isEmpty()) {
            System.out.println("No applications found.");
            InputHelper.pause();
            throw new PageBackException();
        }
        DisplayableViewer.displayList(appList);
        int choice;

        while (true) {
            System.out.println("Please select an action:");
            System.out.println("0) Back to Main Menu");
            System.out.println("1) Accept/Withdraw an Application");
            System.out.println("Enter your choice (0/1/2): ");

            choice = InputHelper.readInt();
            if (choice == 0) {
                ChangePage.changePage();
                throw new PageBackException();
            }
            else if (choice == 1) {
                break;
            }
            else{
                System.out.println("Invalid choice. Please try again.");
            }
        }

        int index;

        while (true) {
            System.out.println("Select index of the application. (Enter [0] to go back to menu): ");
            index = InputHelper.readInt();
            if (index == 0) {
                ChangePage.changePage();
                throw new PageBackException();
            }
            else if(index >= 1 && index <= appList.size()){
                break;
            }
            else{
                System.out.println("Invalid choice. Please enter a valid index.");
            }
        }

        Application app = appList.get(index-1);
        int choice2;

        while (true){
            System.out.println("Please select an action:");
            System.out.println("1) Accept Application");
            System.out.println("2) Withdraw Application: ");
            System.out.println("3) Back to Main Menu");
            System.out.println("Enter your choice (1/2/3): ");
            choice2 = InputHelper.readInt();
            if (choice2 == 3) {
                ChangePage.changePage();
                throw new PageBackException();
            }
            else if (choice2 == 1) {
                try{
                    studentController.acceptPlacement(app.getApplicationId());
                    System.out.println("Application has been accepted.");
                    break;
                }catch (Exception e){
                    System.out.println("ERROR: "+e.getMessage());
                }
            }
            else if (choice2 == 2) {
                String reason = AttributeGetter.getString("Please enter your reason for withdrawal: ");
                try{
                    studentController.withdrawPlacement(app.getApplicationId(), reason);
                    System.out.println("Withdrawal reqeust has been sent.");
                    break;
                }catch (Exception e){
                    System.out.println("ERROR: "+e.getMessage());
                }
            }
            else{
                System.out.println("Invalid choice. Please enter a valid choice.");
            }
        }

        InputHelper.pause();
        throw new PageBackException();
    }

    /**
     * Allow student to accept an internship application which has been offered to them
     * Display a list of their applications first
     * Prompts them for the index of the application they want to accept
     */

    /**
     * Allow student to change their password
     * Prompts them for old, new and confirm new password
     */
    private void handleChangePass(){
        ChangePage.changePage();
        boolean retry = true;

        while (retry) {
            ChangePage.changePage();
            String oldPass = AttributeGetter.getPassword("Enter old password: ");
            String newPass = AttributeGetter.getPassword("Enter new password: ");
            String confirmPass = AttributeGetter.getPassword("Confirm new password: ");

            try {
                studentController.changePassword(oldPass, newPass, studentController.getStudent(), confirmPass);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                boolean validChoice = false;

                while (validChoice) {
                    System.out.println();
                    System.out.println("1. Try again");
                    System.out.println("2. Return to main menu");
                    System.out.print("Enter choice (1 or 2): ");
                    int choice = InputHelper.readInt();

                    switch (choice) {
                        case 1 -> validChoice = true; // retry outer loop
                        case 2 -> {
                            validChoice = true;
                            throw new PageBackException();
                        }
                        default -> {
                            System.out.println("Invalid choice. Please enter 1 or 2.");
                            System.out.println();
                        }
                    }
                }
            }
        InputHelper.pause();
        throw new PageBackException();
        }
    }
    /**
     * Allow student to change their filter options
     * Display their current filter options
     * Only can filter based on level and closing date as students are not allowed to see other internships which they are not eligible for
     */
    private void handleChangeFilter(){
        ChangePage.changePage();
        System.out.println("===Update Filter Criteria===");
        FilterCriteria filter = studentController.getFilter();

        System.out.println("\nCurrent Filter Criteria: ");
        System.out.println("- Level:  " + filter.getLevel());
        System.out.println("- Closing Date: " + filter.getClosingDate());
        System.out.println("- Company Name: " + filter.getCompanyName());
        System.out.println();

        boolean back = false;
        while (!back) {
            System.out.println("1) Change Internship Level");
            System.out.println("2) Change Closing Date");
            System.out.println("3) Change Company Name");
            System.out.println("4) Clear All Filters");
            System.out.println("0) Back");
            System.out.print("Enter choice: ");
            int choice = InputHelper.readInt();

            if (choice == 1) {
                System.out.println("Enter Internship Level: ");
                System.out.println("1) BASIC\n2) INTERMEDIATE\n3) ADVANCED\n4) CLEAR");
                System.out.println("Enter choice: ");
                int choice2 = InputHelper.readInt();

                switch (choice2) {
                    case 1 -> filter.setLevel(InternshipLevel.BASIC);
                    case 2 -> filter.setLevel(InternshipLevel.INTERMEDIATE);
                    case 3 -> filter.setLevel(InternshipLevel.ADVANCED);
                    case 4 -> filter.setLevel(null);
                    default -> System.out.println("Invalid choice. Keeping previous level.");
                }
                System.out.println("Level updated -> " + filter.getLevel());
            }

            else if (choice == 2) {
                String closeRaw = AttributeGetter.getString("Enter closing date (yyyy-MM-dd) or 0 to clear: ");
                if (!closeRaw.isBlank()){
                    String input = closeRaw.trim();
                    if (input.equals("0")){
                        filter.setClosingDate(null);
                        System.out.println("-> Closing date filter cleared");
                    }
                    else{
                        try{
                            filter.setClosingDate(LocalDate.parse(input));
                            System.out.println("Closing date updated -> " + filter.getClosingDate());
                        }catch(Exception e){
                            System.out.println("!! Invalid closing date. Keeping previous");
                        }
                    }
                }
            }

            else if (choice == 3) {
                String companyNameRaw = AttributeGetter.getString("Enter company name or 0 to clear: ");
                if (!companyNameRaw.isBlank()){
                    String input = companyNameRaw.trim();
                    if (input.equals("0")){
                        filter.setCompanyName(null);
                        System.out.println("-> Company name filter cleared");
                    }
                    else{
                        try{
                            filter.setCompanyName(companyNameRaw);
                            System.out.println("Company name updated -> " + filter.getCompanyName());
                        }catch(Exception e){
                            System.out.println("!! Invalid company name. Keeping previous");
                        }
                    }
                }
            }

            else if (choice == 4) {
                filter.setLevel(null);
                filter.setClosingDate(null);
                filter.setCompanyName(null);
                System.out.println("All Filters Cleared.");
            }

            else if (choice == 0) {
                throw new PageBackException();
            }

            else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
        InputHelper.pause();
        throw new PageBackException();
    }
}
