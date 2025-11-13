package boundary.usermenu;

import boundary.AttributeGetter;
import boundary.FilterUI;
import boundary.viewer.DisplayableViewer;
import controller.control.SystemController;
import controller.control.user.StaffController;
import entity.internship.InternshipOpportunity;
import entity.request.InternshipVetRequest;
import entity.request.RegistrationRequest;
import entity.request.Request;
import entity.request.WithdrawalRequest;
import util.exceptions.PageBackException;
import util.io.GraphicLogo;
import util.io.InputHelper;
import util.io.ChangePage;

import java.util.List;

public class StaffUI {

    private final SystemController systemController;
    private final StaffController staffController;

    public StaffUI(SystemController systemController, StaffController staffController){
        this.systemController = systemController;
        this.staffController = staffController;
    }

    public void menuLoop() {
        ChangePage.changePage();
        System.out.println(GraphicLogo.SEPARATOR);
        System.out.println("Welcome to Career Center Staff Menu!");
        System.out.println(GraphicLogo.SEPARATOR);
        System.out.println("1.  View / filter internships");
        System.out.println("2.  View pending rep registrations");
        System.out.println("3.  Rep registration (Approve/Reject)");
        System.out.println("4.  View internships pending approval");
        System.out.println("5.  Internship (Approve/Reject)");
        System.out.println("6.  View pending withdrawal requests");
        System.out.println("7.  Withdrawal (Approve/Reject)");
        System.out.println("8.  Update internship filter settings");
        System.out.println("9.  Change password");
        System.out.println("10. Logout");
        System.out.print("Enter your choice (1-10): ");

        try {
            int choice = InputHelper.readInt();
            switch (choice) {
                case 1 -> ViewFilteredInternships();
                case 2 -> viewPendingReq("Rep Registration", staffController.viewPendingReg());
                case 3 -> approveRejectReq("Rep Registration", staffController.viewPendingReg());               // merged
                case 4 -> viewPendingReq("Internship Vetting", staffController.viewPendingInternshipVet());
                case 5 -> approveRejectReq("Internship Vetting", staffController.viewPendingInternshipVet());     // merged
                case 6 -> viewPendingReq("Withdrawal", staffController.viewPendingWithdrawal());
                case 7 -> approveRejectReq("Withdrawal", staffController.viewPendingWithdrawal());        // merged
                case 8 -> {
                    FilterUI.update(staffController.getFilter());
                    throw new PageBackException(); // so that it will loop
                }
                case 9 -> handleChangePassword();
                case 10 -> {
                    System.out.println("Logging out...");
                    systemController.mainMenu(); // return to Welcome screen
                }
                default -> {
                    System.out.println("Invalid choice. Please try again.");
                InputHelper.pause();
                throw new PageBackException();
                }
            }
        } catch (PageBackException e) {
            menuLoop();
        }

    }

    private void approveRejectReq(String requestType, List<? extends Request> pending){
        ChangePage.changePage();
        System.out.println("All " + requestType + " Requests");
        System.out.println(GraphicLogo.SEPARATOR);
        if (pending.isEmpty()){
            System.out.println("No " + requestType + " pending approval");
            InputHelper.pause();
            throw new PageBackException(); // goes back to the menu
        }

        DisplayableViewer.displayList(pending);

        int index;
        while (true){
            System.out.println("Please choose request");
            System.out.println("- Enter the index of the request to continue");
            System.out.println("- Enter [0] to go back to main menu");
            System.out.print("Your choice: ");
            index = InputHelper.readInt();
            if (index == 0){
                throw new PageBackException();
            }
            else if (index >= 1 && index <= pending.size()) {
                break;
            } else {
                System.out.println("Invalid Choice. Please enter a valid index.");
            }
        }

        Request request = pending.get(index-1);
        String requestID = request.getId();

        while (true) {
            System.out.println("Please select an action:");
            System.out.println("- Enter 1 to Reject");
            System.out.println("- Enter 2 to Approve");
            System.out.println("- Enter 0 to return to the menu");
            System.out.print("Your choice: ");
            int choice = InputHelper.readInt();

            if (choice == 0) {
                throw new PageBackException();
            } else if (choice == 1 || choice == 2) {
                try {
                    if (request instanceof InternshipVetRequest) {
                        if (choice == 1) {
                            staffController.rejectInternship(requestID);
                            DisplayableViewer.displaySingle(request);
                        }
                        else {
                            staffController.approveInternship(requestID);
                            DisplayableViewer.displaySingle(request);
                        }
                    } else if (request instanceof RegistrationRequest) {
                        if (choice == 1) {
                            staffController.rejectRep(requestID);
                            DisplayableViewer.displaySingle(request);
                        }
                        else {
                            staffController.approveRep(requestID);
                            DisplayableViewer.displaySingle(request);
                        }
                    } else if (request instanceof WithdrawalRequest) {
                        if (choice == 1) {
                            staffController.rejectWithdrawal(requestID);
                            DisplayableViewer.displaySingle(request);
                        }
                        else {
                            staffController.approveWithdrawal(requestID);
                            DisplayableViewer.displaySingle(request);
                        }
                    } else {
                        System.out.println("Unknown request type.");
                    }
                    break; // exit while loop if they approve/rej or unknown type then go to pause

                } catch (Exception e) { //exceptions thrown by approve and reject methods
                    System.out.println("ERROR: " + e.getMessage()); // stay in the loop
                }
            } else {
                System.out.println("Please Enter [1] (Reject), [2] (Approve), or [0] (Back).");
            } // stay in loop
        }

        InputHelper.pause();
        throw new PageBackException();
    }


    private void ViewFilteredInternships(){
        ChangePage.changePage();
        System.out.println("All Internships(Filtered)");
        System.out.println(GraphicLogo.SEPARATOR);
        List<InternshipOpportunity> filteredList = staffController.viewInternshipsFiltered(staffController.getFilter());
        DisplayableViewer.displayList(filteredList);
        InputHelper.pause();
        throw new PageBackException();
    }

    private void viewPendingReq(String requestType, List<? extends Request> pending){
        ChangePage.changePage();
        System.out.println("All " + requestType + " Requests");
        System.out.println(GraphicLogo.SEPARATOR);
        if (pending.isEmpty()){
            System.out.println("No " + requestType + " pending approval");
            InputHelper.pause();
            throw new PageBackException(); // goes back to the menu
        }

        DisplayableViewer.displayList(pending);
        InputHelper.pause();
        throw new PageBackException();
    }

    private void handleChangePassword(){
        boolean retry = true;

        while (retry) {
            ChangePage.changePage();
            String oldPass = AttributeGetter.getPassword("Enter old password: ");
            String newPass = AttributeGetter.getPassword("Enter new password: ");
            String confirmPass = AttributeGetter.getPassword("Confirm new password: ");

            try {
                staffController.changePassword(oldPass, newPass, staffController.getStaff(), confirmPass);
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

