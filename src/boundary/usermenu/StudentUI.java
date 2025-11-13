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

        System.out.println("\n=== Student Menu ===");
        InputHelper.printMenuItem(1, "View / filter Eligible Internships");
        InputHelper.printMenuItem(2, "Apply for Internship");
        InputHelper.printMenuItem(3, "View Applications");
        InputHelper.printMenuItem(4, "Accept Internship Placement");
        InputHelper.printMenuItem(5, "Withdraw Internship Placement");
        InputHelper.printMenuItem(6, "Change Password");
        InputHelper.printMenuItem(7, "Change Filter Setting");
        InputHelper.printMenuItem(8, "Logout");
        System.out.print("Enter your choice (1-8): ");

        try {
            while (true) {
                int choice = InputHelper.readInt();
                switch (choice) {
                    case 1 -> handleViewInternships();
                    case 2 -> handleApplication();
                    case 3 -> handleViewApplications();
                    case 4 -> handleAcceptance();
                    case 5 -> handleWithdrawal();
                    case 6 -> handleChangePass();
                    case 7 -> handleChangeFilter();
                    case 8 -> {
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
        }catch (PageBackException e){
            menu();
        }
    }

    /**
     *  Allow user to view internships based on their filter
     *  If no internships are available then will just print "No internships found"
     *
     */
    private void handleViewInternships(){
        List<InternshipOpportunity> internshipList = studentController.viewFilteredInternships(studentController.getFilter());
        if (internshipList.isEmpty()) {
            System.out.println("No internships found.");
            InputHelper.pause();
            throw new PageBackException();
        }
        DisplayableViewer.displayList(internshipList);
        InputHelper.pause();
        throw new PageBackException();
    }

    /**
     * Allow student to apply for a specific internship
     * Will display list of internships based on filter
     * Prompts student to give the index of the internship which he wants to apply for
     * Maps the index to the internship and get the ID
     * Calls the studentController to apply for that internship
     */
    private void handleApplication(){
        System.out.println("Internship Application");
        List<InternshipOpportunity> internshipList = studentController.viewFilteredInternships(studentController.getFilter());

        if  (internshipList.isEmpty()) {
            System.out.println("No internships found.");
            InputHelper.pause();
            throw new PageBackException();
        }

        DisplayableViewer.displayList(internshipList);
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
        }catch (Exception e){
            System.out.println("ERROR: "+e.getMessage());
        }

        InputHelper.pause();
        throw new PageBackException();
    }

    /**
     * Allows student to view their applications
     * If student has not applied to any internships, print "No applications found"
     */
    private void handleViewApplications(){
        System.out.println("Your Internship Applications");
        List<Application> appList = studentController.myApplications();
        if (appList.isEmpty()) {
            System.out.println("No applications found.");
            InputHelper.pause();
            throw new PageBackException();
        }
        DisplayableViewer.displayList(appList);
        InputHelper.pause();
        throw new PageBackException();
    }

    /**
     * Allow student to accept an internship application which has been offered to them
     * Display a list of their applications first
     * Prompts them for the index of the application they want to accept
     */
    private void handleAcceptance(){
        System.out.println("Accept Internship Placement");
        List<Application> appList = studentController.myApplications();
        if (appList.isEmpty()) {
            System.out.println("No applications found.");
            InputHelper.pause();
            throw new PageBackException();
        }

        DisplayableViewer.displayList(appList);
        int index;

        while (true){
            System.out.println("Select index of application you want to accept. (Enter [0] to go back to menu): ");
            index = InputHelper.readInt();
            if (index == 0) {
                throw new PageBackException();
            }
            else if(index >= 1 && index <= appList.size()){
                break;
            }
            else{
                System.out.println("Invalid choice. Please enter a valid index.");
            }
        }
        try {
            Application app = appList.get(index-1);
            studentController.acceptPlacement(app.getApplicationId());
            System.out.println("Application has been accepted.");
        }catch (Exception e){
            System.out.println("ERROR: "+e.getMessage());
        }
        InputHelper.pause();
        throw new PageBackException();

    }

    /**
     * Allow student to withdraw an internship application
     * Display a list of their applications first
     * Prompts them for the index of the application they want to withdraw
     */
    private void handleWithdrawal(){
        System.out.println("Withdraw Internship Placement");
        List<Application> appList = studentController.myApplications();

        if (appList.isEmpty()) {
            System.out.println("No applications found.");
            InputHelper.pause();
            throw new PageBackException();
        }

        DisplayableViewer.displayList(appList);
        int index;
        while (true){
            System.out.println("Select index of application you want to withdraw. (Enter [0] to go back to menu): ");
            index = InputHelper.readInt();
            if (index == 0) {
                throw new PageBackException();
            }
            else if(index >= 1 && index <= appList.size()){
                break;
            }
            else{
                System.out.println("Invalid choice. Please enter a valid index.");
            }
        }

        String reason = AttributeGetter.getString("Please enter your reason for withdrawal.");

        Application app = appList.get(index-1);
        try {
            studentController.withdrawPlacement(app.getApplicationId(), reason);
        }catch (Exception e){
            System.out.println("ERROR: "+e.getMessage());
        }
        InputHelper.pause();
        throw new PageBackException();
    }

    /**
     * Allow student to change their password
     * Prompts them for old, new and confirm new password
     */
    private void handleChangePass(){
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
        System.out.println("Update Filter Criteria");
        FilterCriteria filter = studentController.getFilter();

        System.out.println("Current Filter Criteria: ");
        System.out.println("- Level:  " + filter.getLevel());
        System.out.println("- Closing Date: " + filter.getClosingDate());
        System.out.println();

        System.out.println("Enter Internship Level: ");
        System.out.println("1) BASIC\n2) INTERMEDIATE\n3) ADVANCED\n4) CLEAR");
        System.out.println("Enter choice: ");
        int choice = InputHelper.readInt();

        switch (choice) {
            case 1 -> filter.setLevel(InternshipLevel.BASIC);
            case 2 -> filter.setLevel(InternshipLevel.INTERMEDIATE);
            case 3 -> filter.setLevel(InternshipLevel.ADVANCED);
            case 4 -> filter.setLevel(null);
            default -> System.out.println("Invalid choice. Keeping previous level.");
        }

        System.out.println("Level updated: " + filter.getLevel());


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
                }catch(Exception e){
                    System.out.println("!! Invalid closing date. Keeping previous");
                }
            }
        }

        System.out.println();
        System.out.println("Filter updated.");
        InputHelper.pause();
        throw new PageBackException();
    }
}
