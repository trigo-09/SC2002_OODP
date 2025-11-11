package boundary;

import boundary.viewer.DisplayableViewer;
import controller.control.SystemController;
import controller.control.user.StaffController;
import entity.internship.InternStatus;
import entity.internship.InternshipLevel;
import entity.internship.InternshipOpportunity;
import entity.request.InternshipVetRequest;
import entity.request.RegistrationRequest;
import entity.request.WithdrawalRequest;
import util.FilterCriteria;
import util.exceptions.PageBackException;
import util.io.InputHelper;
import util.ui.ChangePage;

import java.time.LocalDate;
import java.util.List;

public class StaffUI {

    private SystemController systemController;
    private StaffController staffController;

    public StaffUI(SystemController systemController, StaffController staffController){
        this.systemController = systemController;
        this.staffController = staffController;
    }

    public void menuLoop() {
        ChangePage.changePage();
        boolean stay = true;
        while (stay) {
            System.out.println();
            System.out.println("=== Career Center Staff Menu ===");
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
                    case 2 -> ViewPendingReps();
                    case 3 -> ApproveRejectRep();               // merged
                    case 4 -> ViewPendingInternships();
                    case 5 -> ApproveRejectInternshipVet();     // merged
                    case 6 -> ViewPendingWithdrawals();
                    case 7 -> ApproveRejectWithdrawal();        // merged
                    case 8 -> UpdateFilter();
                    case 9 -> handleChangePassword();
                    case 10 -> {
                        System.out.println("Logging out...");
                        stay = false;                // stop loop
                        systemController.mainMenu(); // return to Welcome screen
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Input error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void ApproveRejectInternshipVet(){
        ChangePage.changePage();
        System.out.println("All Internship Vetting Request");
        List<InternshipVetRequest> pendingInternships = staffController.viewPendingInternshipVet();
        if (pendingInternships.isEmpty()){
            System.out.println("No internships pending approval");
            return;
        }

        for (int i = 0; i < pendingInternships.size(); i++){
            DisplayableViewer.displaySingle(pendingInternships.get(i), i+1);
        }

        int index = -1;
        while (true) {
            System.out.println("Choose the index of the internship");
            index = InputHelper.readInt();
            if (index >= 1 && index <= pendingInternships.size()) {
                break;
            } else {
                System.out.println("Invalid Choice. Please enter a valid index.");
            }
        }

        InternshipVetRequest choice1 = pendingInternships.get(index - 1);

        int choice2 = -1;
        while (true) {
            System.out.print("Enter 0 to Reject, 1 to Approve: ");
            choice2 = InputHelper.readInt();
            if (choice2 == 1) {
                try {
                    staffController.approveInternship(choice1.getId());
                }catch (Exception e){
                    System.out.println("ERROR: " + e.getMessage());
                }
                break;
            } else if (choice2 == 0) {
                try {
                    staffController.rejectInternship(choice1.getId());
                }catch (Exception e){
                    System.out.println("ERROR: " + e.getMessage());
                }
                break;
            } else {
                System.out.println("Please Enter 0 to Reject or 1 to Approve");
            }
        }
    }

    public void ApproveRejectRep(){
        ChangePage.changePage();
        System.out.println("All Rep Registration Request");
        List<RegistrationRequest> pendingReg = staffController.viewPendingReg();
        if (pendingReg.isEmpty()){
            System.out.println("No registration pending approval");
            return;
        }

        for (int i = 0; i < pendingReg.size(); i++){
            DisplayableViewer.displaySingle(pendingReg.get(i), i+1);
        }

        System.out.println("Choose the index of the Rep");
        int index = InputHelper.readInt();
        if (index < 1 || index > pendingReg.size()){
            System.out.println("Invalid Choice"); // need error catching
            return;
        }

        RegistrationRequest choice1 = pendingReg.get(index - 1);

        System.out.print("Enter 0 to Reject, 1 to Approve: ");
        int choice2 = InputHelper.readInt();
        if (choice2 == 1){
            try {
                staffController.approveRep(choice1.getId());
            }catch (Exception e){
                System.out.println("ERROR: " + e.getMessage());
            }
        }
        else if (choice2 == 0){
            try {
                staffController.rejectRep(choice1.getId());
            }catch (Exception e){
                System.out.println("ERROR: " + e.getMessage());
            }
        }
        else{
            System.out.println("Please Enter 0 to Reject or 1 to Approve");
        }
    }

    public void ApproveRejectWithdrawal(){
        ChangePage.changePage();
        System.out.println("All Withdrawal Requests");
        List<WithdrawalRequest> pendingWithdrawal = staffController.viewPendingWithdrawal();
        if (pendingWithdrawal.isEmpty()){
            System.out.println("No withdrawal pending approval");
            return;
        }

        for (int i = 0; i < pendingWithdrawal.size(); i++){
            DisplayableViewer.displaySingle(pendingWithdrawal.get(i), i+1);
        }

        System.out.println("Choose the index of the Withdrawal");
        int index = InputHelper.readInt();
        if (index < 1 || index > pendingWithdrawal.size()){
            System.out.println("Invalid Choice"); // need error catching
            return;
        }

        WithdrawalRequest choice1 = pendingWithdrawal.get(index - 1);

        System.out.print("Enter 0 to Reject, 1 to Approve: ");
        int choice2 = InputHelper.readInt();
        if (choice2 == 1){
            try {
                staffController.approveWithdrawal(choice1.getId());
            }catch (Exception e){
                System.out.println("ERROR: " + e.getMessage());
            }
        }
        else if (choice2 == 0){
            try {
                staffController.rejectWithdrawal(choice1.getId());
            }catch (Exception e){
                System.out.println("ERROR: " + e.getMessage());
            }
        }
        else{
            System.out.println("Please Enter 0 to Reject or 1 to Approve");
        }
    }


    public void ViewFilteredInternships(){
        ChangePage.changePage();
        System.out.println("All Internships(Filtered)");
        List<InternshipOpportunity> filteredList = staffController.viewInternshipsFiltered(staffController.getFilter());
        DisplayableViewer.displayList(filteredList);
    }

    public void ViewPendingReps(){
        ChangePage.changePage();
        System.out.println("Pending Rep Registration Requests");
        List<RegistrationRequest> pendingReg = staffController.viewPendingReg();
        DisplayableViewer.displayList(pendingReg);
    }

    public void ViewPendingInternships(){
        ChangePage.changePage();
        System.out.println("Pending Internship Vetting Requests");
        List<InternshipVetRequest> pendingInternships = staffController.viewPendingInternshipVet();
        DisplayableViewer.displayList(pendingInternships);
    }


    private void ViewPendingWithdrawals(){
        ChangePage.changePage();
        System.out.println("Pending Withdrawal Requests");
        List<WithdrawalRequest> pendingWithdrawal = staffController.viewPendingWithdrawal();
        DisplayableViewer.displayList(pendingWithdrawal);
    }


    private void handleChangePassword(){
        ChangePage.changePage();
        String oldPass = AttributeGetter.getPassword("Enter old password: ");
        String newPass = AttributeGetter.getPassword("Enter new password: ");
        String confirmPass = AttributeGetter.getPassword("Confirm new password: ");

        try {
            staffController.changePassword(oldPass, newPass, staffController.getStaff(), confirmPass);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }



    public void UpdateFilter(){
        ChangePage.changePage();
        System.out.println("Update Filter Criteria");
        FilterCriteria filter = staffController.getFilter();

        System.out.println("Current Filter Criteria");
        System.out.println(" - Status                 : " + filter.getStatus());
        System.out.println(" - Preferred Major        : " + filter.getPreferredMajor());
        System.out.println(" - Level                  : " + filter.getLevel());
        System.out.println(" - Closing Date          : " + filter.getClosingDate());
        System.out.println();

        String statusRaw = AttributeGetter.getString("Status (PENDING / APPROVED / REJECTED / FILLED) [or CLEAR]: ");
        if (!statusRaw.isBlank()) {
            String input = statusRaw.trim().toUpperCase();
            if (input.equals("CLEAR") || input.equals("NONE")) {
                filter.setStatus(null);
                System.out.println("→ Status filter cleared.");
            } else {
                try {
                    InternStatus status = InternStatus.valueOf(input);
                    filter.setStatus(status);
                } catch (IllegalArgumentException e) {
                    System.out.println("!! Invalid status. Keeping previous.");
                }
            }
        }

        String majorRaw = AttributeGetter.getString("Preferred Major contains [or CLEAR]: ");
        if (!majorRaw.isBlank()) {
            String input = majorRaw.trim();
            if (input.equalsIgnoreCase("CLEAR") || input.equalsIgnoreCase("NONE")) {
                filter.setPreferredMajor(null);
                System.out.println("→ Preferred Major filter cleared.");
            } else {
                filter.setPreferredMajor(input);
            }
        }

        String levelRaw = AttributeGetter.getString("Internship Level (BASIC / INTERMEDIATE / ADVANCED) [or CLEAR]: ");
        if (!levelRaw.isBlank()) {
            String input = levelRaw.trim().toUpperCase();
            if (input.equals("CLEAR") || input.equals("NONE")) {
                filter.setLevel(null);
                System.out.println("→ Level filter cleared.");
            } else {
                try {
                    InternshipLevel level = InternshipLevel.valueOf(input);
                    filter.setLevel(level);
                } catch (IllegalArgumentException e) {
                    System.out.println("!! Invalid level. Keeping previous.");
                }
            }
        }

        String closeRaw = AttributeGetter.getString("Closing on/before (YYYY-MM-DD) [or CLEAR]: ");
        if (!closeRaw.isBlank()) {
            String input = closeRaw.trim();
            if (input.equalsIgnoreCase("CLEAR") || input.equalsIgnoreCase("NONE")) {
                filter.setClosingDate(null);
                System.out.println("→ Closing date filter cleared.");
            } else {
                try {
                    filter.setClosingDate(LocalDate.parse(input));
                } catch (Exception e) {
                    System.out.println("!! Invalid date. Keeping previous.");
                }
            }
        }

        System.out.println();
        System.out.println("Filter updated. It will stay for this login session.");
    }


}
