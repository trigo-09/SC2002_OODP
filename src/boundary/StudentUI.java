package boundary;

import boundary.viewer.DisplayableViewer;
import controller.control.SystemController;
import controller.control.user.StudentController;
import entity.application.Application;
import entity.internship.InternStatus;
import entity.internship.InternshipLevel;
import entity.internship.InternshipOpportunity;
import java.time.LocalDate;
import java.util.*;
import util.FilterCriteria;
import util.exceptions.PageBackException;
import util.io.InputHelper;
import util.ui.ChangePage;

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
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        }catch (PageBackException e){
            menu();
        }
    }
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

    private void handleApplication(){
        System.out.println("Internship Application");
        List<InternshipOpportunity> internshipList = studentController.viewFilteredInternships(studentController.getFilter());

        if  (internshipList.isEmpty()) {
            System.out.println("No internships found.");
            InputHelper.pause();
            throw new PageBackException();
        }

        for (int i = 0; i<internshipList.size(); i++){
            DisplayableViewer.displaySingle(internshipList.get(i));
        }

        System.out.println("Choose the index of the internship you want to apply");

        int index = InputHelper.readInt();

        if (index < 1 || index > internshipList.size()){
            System.out.println("Invalid index");
            InputHelper.pause();
            throw new PageBackException();
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

    private void handleAcceptance(){
        System.out.println("Accept Internship Placement");
        List<Application> appList = studentController.myApplications();
        if (appList.isEmpty()) {
            System.out.println("No applications found.");
            InputHelper.pause();
            throw new PageBackException();
        }

        for (int i = 0; i<appList.size(); i++){
            DisplayableViewer.displaySingle(appList.get(i));
        }

        System.out.println("Choose the index of the application you want to accept");

        int index = InputHelper.readInt();

        if (index < 1 || index > appList.size()){
            System.out.println("Invalid index");
            InputHelper.pause();
            throw new PageBackException();
        }

        Application app = appList.get(index-1);
        studentController.acceptPlacement(app.getApplicationId());
        System.out.println("Application has been accepted.");
        InputHelper.pause();
        throw new PageBackException();
    }

    private void handleWithdrawal(){
        System.out.println("Withdraw Internship Placement");
        List<Application> appList = studentController.myApplications();

        if (appList.isEmpty()) {
            System.out.println("No applications found.");
            InputHelper.pause();
            throw new PageBackException();
        }

        for (int i = 0; i<appList.size(); i++){
            DisplayableViewer.displaySingle(appList.get(i));
        }

        System.out.println("Choose the index of the application you want to withdraw");
        int index = InputHelper.readInt();

        if (index < 1 || index > appList.size()){
            System.out.println("Invalid index");
            InputHelper.pause();
            throw new PageBackException();
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

    private void handleChangePass(){
        String oldPass = AttributeGetter.getString("Enter old password");
        String newPass = AttributeGetter.getString("Enter new password");
        String confirmPass = AttributeGetter.getString("Confirm new password");

        try{
            studentController.changePassword(oldPass, newPass, studentController.getStudent(),  confirmPass);
        }
        catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        InputHelper.pause();
        throw new PageBackException();
    }

    private void handleChangeFilter(){
        System.out.println("Update Filter Criteria");
        FilterCriteria filter = studentController.getFilter();

        System.out.println("Current Filter Criteria: ");
        System.out.println("- Level:  " + filter.getLevel());
        System.out.println("- Closing Date: " + filter.getClosingDate());
        System.out.println();

        String levelRaw = AttributeGetter.getString("Level contains [or CLEAR]: ");
        if (!levelRaw.isBlank()){
            String input = levelRaw.trim();
            if (input.equals("CLEAR")){
                filter.setLevel(null);
                System.out.println("-> Level filter cleared");
            }
            else{
                try{
                    InternshipLevel level = InternshipLevel.valueOf(input);
                    filter.setLevel(level);
                }catch(IllegalArgumentException e){
                    System.out.println("!! Invalid level. Keeping previous");
                }
            }
        }

        String closeRaw = AttributeGetter.getString("Enter closing date: ");
        if (!closeRaw.isBlank()){
            String input = closeRaw.trim();
            if (input.equals("CLEAR")){
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
