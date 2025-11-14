package boundary.usermenu;

import boundary.FilterUI;
import boundary.terminal.ChangePasswordUI;
import boundary.viewer.DisplayableViewer;
import controller.control.SystemController;
import controller.control.user.StaffController;
import entity.application.Application;
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
        System.out.println("Welcome to Career Staff Menu!");
        System.out.println("Hello, " + staffController.getStaff().getUserName()+"!\n");
        System.out.println("\t1.  View / filter internships");

        if (staffController.viewPendingReg().isEmpty()) {
            System.out.println("\t2.  View pending rep registrations");
        } else {
            System.out.println("\t2.  View pending rep registrations " + GraphicLogo.NEW );
        }

        if (staffController.viewPendingInternshipVet().isEmpty()) {
            System.out.println("\t3.  View internships pending approval");
        } else {
            System.out.println("\t3.  View internships pending approval " + GraphicLogo.NEW);
        }

        if (staffController.viewPendingWithdrawal().isEmpty()) {
            System.out.println("\t4.  View pending withdrawal requests");
        } else {
            System.out.println("\t4.  View pending withdrawal requests " + GraphicLogo.NEW);
        }

        System.out.println("\t5.  Update internship filter settings");
        System.out.println("\t6.  Change password");
        System.out.println("\t0.  Logout");
        System.out.println(GraphicLogo.SEPARATOR + "\n");
        System.out.print("Enter your choice (0-6): ");

        try {
            int choice = InputHelper.readInt();
            switch (choice) {
                case 1 -> viewFilteredInternships();
                case 2 -> viewPendingReq("Rep Registration");
                case 3 -> viewPendingReq("Internship Vetting");
                case 4 -> viewPendingReq("Withdrawal");
                case 5 -> {
                    FilterUI.update(staffController.getFilter());
                    throw new PageBackException(); // so that it will loop
                }
                case 6 -> ChangePasswordUI.handleChangePassword(systemController, staffController, staffController.getStaff());
                case 0 -> {
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

    private void viewFilteredInternships(){
        int loop = 1;
        try {
            while (true) {
                ChangePage.changePage();
                System.out.println("All Internships(Filtered)");
                System.out.println(GraphicLogo.LONG_SEP);
                List<InternshipOpportunity> filteredList = staffController.viewInternshipsFiltered(staffController.getFilter());
                if (filteredList.isEmpty()) {
                    System.out.println("There is no internship opportunity");
                    InputHelper.pause();
                    throw new PageBackException();
                }
                DisplayableViewer.displayList(filteredList);
                System.out.println("Please select action");
                System.out.println("\t- Enter [1] if you would like to view applications of a certain internship opportunity");
                System.out.println("\t- Enter [0] to return to main menu");
                System.out.println(GraphicLogo.LONG_SEP + "\n");
                System.out.print("Your choice: ");

                int action = InputHelper.readInt();
                if (action == 1) {
                    loop = -1;
                    viewApplications(filteredList);
                } else if (action == 0) {
                    throw new PageBackException();
                }
                else {
                    System.out.println("Invalid choice. Please try again.");
                    InputHelper.pause();
                }
            }
        } catch (PageBackException e){
            if (loop == -1) {
                viewFilteredInternships();

            }
            else {
                throw new PageBackException();
            }
        }


    }

    private void viewApplications(List<InternshipOpportunity> filteredList){
        int index;
        while (true){
            ChangePage.changePage();
            System.out.println("All Internships(Filtered)");
            System.out.println(GraphicLogo.LONG_SEP);
            DisplayableViewer.displayList(filteredList);
            System.out.println("Please choose internship");
            System.out.println("\t- Enter the index of the internship you would like to view application");
            System.out.println("\t- Enter [0] to return");
            System.out.println(GraphicLogo.LONG_SEP + "\n");
            System.out.print("Your choice: ");

            index = InputHelper.readInt();
            if (index == 0){
                throw new PageBackException();
            }
            else if (index >= 1 && index <= filteredList.size()) {
                String internId = filteredList.get(index-1).getId();
                try {
                    List<Application> applications = staffController.getAcceptedApplications(internId);
                    if (applications.isEmpty()) {
                        System.out.println("(No applications yet)");
                    } else {
                        System.out.println("Approved applications");
                        System.out.println(GraphicLogo.LONG_SEP);
                        DisplayableViewer.displayList(applications);
                    }
                    InputHelper.pause();
                } catch (IllegalArgumentException | IllegalStateException | SecurityException e) {
                    System.out.println("Error retrieving applications: " + e.getMessage());
                    InputHelper.pause();
                }
            } else {
                System.out.println("Invalid Choice. Please enter a valid index.");
                InputHelper.pause();
            }
        }


    }

    private void viewPendingReq(String requestType){
        int loop = 1; //indicates to go back to staff menu
        try {
            ChangePage.changePage();
            while (true){
                ChangePage.changePage();
                List<? extends Request> pending = switch (requestType){
                    case "Rep Registration" -> staffController.viewPendingReg();
                    case "Internship Vetting" -> staffController.viewPendingInternshipVet();
                    case "Withdrawal" -> staffController.viewPendingWithdrawal();
                    default -> List.of();
                };
                System.out.println("All Pending " + requestType + " Requests");
                System.out.println(GraphicLogo.LONG_SEP);
                if (pending.isEmpty()) {
                    System.out.println("No " + requestType + " pending approval");
                    InputHelper.pause();
                    throw new PageBackException(); // goes back to the menu
                }
                DisplayableViewer.displayList(pending);
                System.out.println("Please select action");
                System.out.println("\t- Enter [1] to manage requests");
                System.out.println("\t- Enter [0] to return to main menu");
                System.out.println(GraphicLogo.LONG_SEP + "\n");
                System.out.print("Your choice: ");

                int action = InputHelper.readInt();
                if (action == 1){
                    loop = -1; // indicates to go back to req menu
                    approveRejectReq(requestType);
                }
                else if (action == 0){
                    throw new PageBackException();
                }
                else {
                    System.out.println("Invalid choice. Please try again.");
                    InputHelper.pause();
                }
            }

        } catch (PageBackException e){
            if (loop == -1) {
                viewPendingReq(requestType);

            }
            else {
                throw new PageBackException();
            }
        }
    }

    private void approveRejectReq(String requestType){
        int index;
        while (true){
            List<? extends Request> refreshedPending = switch (requestType){
                case "Rep Registration" -> staffController.viewPendingReg();
                case "Internship Vetting" -> staffController.viewPendingInternshipVet();
                case "Withdrawal" -> staffController.viewPendingWithdrawal();
                default -> List.of();
            };
            if (refreshedPending.isEmpty()){
                throw new PageBackException(); // goes back to the menu
            }

            ChangePage.changePage();
            System.out.println("All Pending " + requestType + " Requests");
            System.out.println(GraphicLogo.LONG_SEP);

            DisplayableViewer.displayList(refreshedPending);

            System.out.println("Please choose request");
            System.out.println("\t- Enter the index of the request to process");
            System.out.println("\t- Enter [0] to return");
            System.out.println(GraphicLogo.LONG_SEP + "\n");
            System.out.print("Your choice: ");

            index = InputHelper.readInt();
            if (index == 0){
                throw new PageBackException();
            }
            else if (index >= 1 && index <= refreshedPending.size()) {
                Request request = refreshedPending.get(index-1);
                String requestID = request.getId();

                while (true) {
                    ChangePage.changePage();
                    System.out.println("Your chosen request");
                    System.out.println(GraphicLogo.LONG_SEP);
                    DisplayableViewer.displaySingle(request);
                    System.out.println("Please select an action:");
                    System.out.println("\t- Enter [1] to Reject");
                    System.out.println("\t- Enter [2] to Approve");
                    System.out.println("\t- Enter [0] to return");
                    System.out.println(GraphicLogo.LONG_SEP + "\n");
                    System.out.print("Your choice: ");
                    int choice = InputHelper.readInt();

                    if (choice == 0) {
                        throw new PageBackException();
                    } else if (choice == 1 || choice == 2) {
                        try {
                            if (request instanceof InternshipVetRequest) {
                                if (choice == 1) {
                                    staffController.rejectInternship(requestID);
                                    systemController.update();
                                    ChangePage.changePage();
                                    System.out.println("Updated Request");
                                    System.out.println(GraphicLogo.LONG_SEP);
                                    DisplayableViewer.displaySingle(request);
                                }
                                else {
                                    staffController.approveInternship(requestID);
                                    systemController.update();
                                    ChangePage.changePage();
                                    System.out.println("Updated Request");
                                    System.out.println(GraphicLogo.LONG_SEP);
                                    DisplayableViewer.displaySingle(request);
                                }
                            } else if (request instanceof RegistrationRequest) {
                                if (choice == 1) {
                                    staffController.rejectRep(requestID);
                                    systemController.update();
                                    ChangePage.changePage();
                                    System.out.println("Updated Request");
                                    System.out.println(GraphicLogo.LONG_SEP);
                                    DisplayableViewer.displaySingle(request);
                                }
                                else {
                                    staffController.approveRep(requestID);
                                    systemController.update();
                                    ChangePage.changePage();
                                    System.out.println("Updated Request");
                                    System.out.println(GraphicLogo.LONG_SEP);
                                    DisplayableViewer.displaySingle(request);
                                }
                            } else if (request instanceof WithdrawalRequest) {
                                if (choice == 1) {
                                    staffController.rejectWithdrawal(requestID);
                                    systemController.update();
                                    ChangePage.changePage();
                                    System.out.println("Updated Request");
                                    System.out.println(GraphicLogo.LONG_SEP);
                                    DisplayableViewer.displaySingle(request);
                                }
                                else {
                                    staffController.approveWithdrawal(requestID);
                                    systemController.update();
                                    ChangePage.changePage();
                                    System.out.println("Updated Request");
                                    System.out.println(GraphicLogo.LONG_SEP);
                                    DisplayableViewer.displaySingle(request);
                                }
                            } else {
                                System.out.println("Unknown request type.");
                            }
                            InputHelper.pause();
                            break; // exit while loop if they approve/rej or unknown type then go to pause

                        } catch (Exception e) { //exceptions thrown by approve and reject methods
                            System.out.println("ERROR: " + e.getMessage()); // stay in the loop
                            InputHelper.pause();
                        }
                    } else {
                        System.out.println("Please Enter [1] (Reject), [2] (Approve), or [0] (Back).");
                        InputHelper.pause();
                    } // stay in loop
                }


            } else {
                System.out.println("Invalid Choice. Please enter a valid index.");
                InputHelper.pause();
            }
        }




    }

}

