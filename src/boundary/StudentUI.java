package boundary;

import java.util.*;
import controller.control.user.StudentController;
import controller.control.SystemController;
import entity.application.Application;
import entity.internship.InternStatus;
import entity.internship.InternshipLevel;
import entity.internship.InternshipOpportunity;
import util.FilterCriteria;
import util.io.InputHelper;
import util.ui.ChangePage;
import util.ui.UIHelper;
import java.time.LocalDate;

public class StudentUI {
    private final StudentController studentController;
    private final SystemController systemController;

    public StudentUI(SystemController systemController, StudentController studentController) {
        this.systemController = systemController;
        this.studentController = studentController;
    }

    public void menu(){
        ChangePage.changePage();
        boolean stay = true;
        while (stay){
            System.out.println("\n=== Student Menu ===");
            UIHelper.printMenuItem(1,"View / filter Eligible Internships");
            UIHelper.printMenuItem(2,"Apply for Internship");
            UIHelper.printMenuItem(3,"View Applications");
            UIHelper.printMenuItem(4,"Accept Internship Placement");
            UIHelper.printMenuItem(5,"Withdraw Internship Placement");
            UIHelper.printMenuItem(6,"Change Password");
            UIHelper.printMenuItem(7,"Change Filter Setting");
            UIHelper.printMenuItem(8,"Logout");
            System.out.print("Enter your choice (1-8): ");

            int choice = InputHelper.readInt();

            switch (choice){
                case 1 -> handleViewInternships();
                case 2 -> handleApplication();
                case 3 -> handleViewApplications();
                case 4 -> handleAcceptance();
                case 5 -> handleWithdrawal();
                case 6 -> handleChangePass();
                case 7 -> handleChangeFilter();
                case 8 -> {
                    System.out.println("Logging out...");
                    stay = false;
                    systemController.mainMenu();
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handleViewInternships(){
        List<InternshipOpportunity> internshipList = studentController.viewFilteredInternships(studentController.getFilter());
        DisplayableViewer.displayList(internshipList);
    }

    private void handleApplication(){
        System.out.println("Internship Application");
        List<InternshipOpportunity> internshipList = studentController.viewFilteredInternships(studentController.getFilter());

        for (int i = 0; i<internshipList.size(); i++){
            DisplayableViewer.displaySingle(internshipList.get(i), i+1);
        }

        System.out.println("Choose the index of the internship you want to apply");

        int index = InputHelper.readInt();

        if (index < 1 || index > internshipList.size()){
            System.out.println("Invalid index");
            return;
        }

        InternshipOpportunity internship = internshipList.get(index-1);
        studentController.applyInternship(internship.getId());
        return;
    }

    private void handleViewApplications(){
        System.out.println("Your Internship Applications");
        List<Application> appList = studentController.myApplications();
        DisplayeableViewer.displayList(appList);
    }

    private void handleAcceptance(){
        System.out.println("Accept Internship Placement");
        List<Application> appList = studentController.myApplications();

        for (int i = 0; i<appList.size(); i++){
            DisplayableViewer.displaySingle(appList.get(i), i+1);
        }

        System.out.println("Choose the index of the application you want to accept");

        int index = InputHelper.readInt();

        if (index < 1 || index > appList.size()){
            System.out.println("Invalid index");
            return;
        }

        Application app = appList.get(index-1);
        studentController.acceptPlacement(app.getApplicationId());
        return;
    }

    private void handleWithdrawal(){
        System.out.println("Withdraw Internship Placement");
        List<Application> appList = studentController.myApplications();

        for (int i = 0; i<appList.size(); i++){
            DisplayableViewer.displaySingle(appList.get(i), i+1);
        }

        System.out.println("Choose the index of the application you want to withdraw");
        int index = InputHelper.readInt();

        if (index < 1 || index > appList.size()){
            System.out.println("Invalid index");
            return;
        }

        String reason = AttributeGetter.getString("Please enter your reason for withdrawal.");

        Application app = appList.get(index-1);
        studentController.withdrawPlacement(app.getApplicationId(), reason);
        return;
    }

    private void handleChangePass(){
        String oldPass = AttributeGetter.getString("Enter old password");
        String newPass = AttributeGetter.getString("Enter new password");
        String confirmPass = AttributeGetter.getString("Confirm new password");

        try{
            studentController.changePassword(oldPass, newPass, studentController.getStudent(),  confirmPass);
        }
        catch (IllegalArgumentException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleChangeFilter(){
        System.out.println("Update Filter Criteria");
        FilterCriteria filter = studentController.getFilter();

        System.out.println("Current Filter Criteria: ");
        System.out.println("- Status: " + filter.getStatus());
        System.out.println("- Preferred Major: " + filter.getPreferredMajor());
        System.out.println("- Level:  " + filter.getLevel());
        System.out.println("- Closing Date: " + filter.getClosingDate());
        System.out.println();

        String statusRaw = AttributeGetter.getString("Status (PENDING/APPROVED/REJECTED/FILLED) [or CLEAR]: ");
        if (!statusRaw.isBlank()){
            String input = statusRaw.trim().toUpperCase();
            if (input.equals("CLEAR")){
                filter.setStatus(null);
                System.out.println("-> Status filter cleared");
            }
            else{
                try{
                    InternStatus status = InternStatus.valueOf(input);
                    filter.setStatus(status);
                } catch(IllegalArgumentException e){
                    System.out.println("!! Invalid status. Keeping previous");
                }
            }
        }

        String majorRaw = AttributeGetter.getString("Preferred Major contains [or CLEAR]: ");
        if (!majorRaw.isBlank()){
            String input = majorRaw.trim();
            if (input.equals("CLEAR")){
                filter.setPreferredMajor(null);
                System.out.println("-> Preferred major filter cleared");
            }
            else{
                filter.setPreferredMajor(input);
            }
        }

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
        System.out.println("Filter updated. It will stay for this login session.");
    }
}
