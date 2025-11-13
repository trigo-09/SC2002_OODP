package boundary;

import entity.FilterCriteria;
import entity.internship.*;
import util.io.ChangePage;
import util.io.InputHelper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FilterUI {
    private static final DateTimeFormatter DMY = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void update(FilterCriteria filter) {
        ChangePage.changePage();
        boolean back = false;
        while (!back) {
            System.out.println("\n=== FILTER SETTINGS ===");
            System.out.println("1) Change Status (" + display(filter.getStatus()) + ")");
            System.out.println("2) Change Preferred Major (" + display(filter.getPreferredMajor()) + ")");
            System.out.println("3) Change Internship Level (" + display(filter.getLevel()) + ")");
            System.out.println("4) Change Closing Date (" + display(filter.getClosingDate()) + ")");
            System.out.println("5) Clear All Filters");
            System.out.println("0) Back");
            System.out.print("Enter choice: ");
            int choice = InputHelper.readInt();

            switch (choice) {
                case 1 -> updateStatus(filter);
                case 2 -> updateMajor(filter);
                case 3 -> updateLevel(filter);
                case 4 -> updateClosingDate(filter);
                case 5 -> clearAll(filter);
                case 0 -> back = true;
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void updateStatus(FilterCriteria filter) {
        System.out.println("Select Status:");
        System.out.println("1) PENDING\n2) APPROVED\n3) REJECTED\n4) FILLED\n5) CLEAR");
        System.out.print("Enter choice: ");
        int choice = InputHelper.readInt();

        switch (choice) {
            case 1 -> filter.setStatus(InternStatus.PENDING);
            case 2 -> filter.setStatus(InternStatus.APPROVED);
            case 3 -> filter.setStatus(InternStatus.REJECTED);
            case 4 -> filter.setStatus(InternStatus.FILLED);
            case 5 -> filter.setStatus(null);
            default -> System.out.println("Invalid choice, keeping previous status.");
        }
        System.out.println("Status updated: " + display(filter.getStatus()));
    }

    private static void updateMajor(FilterCriteria filter) {
        String major = AttributeGetter.getString("Enter preferred major (or '0' to clear): ");
        if (major.equals("0")) filter.setPreferredMajor(null);
        else filter.setPreferredMajor(major);
        System.out.println("Preferred Major updated: " + display(filter.getPreferredMajor()));
    }

    private static void updateLevel(FilterCriteria filter) {
        System.out.println("Select Internship Level:");
        System.out.println("1) BASIC\n2) INTERMEDIATE\n3) ADVANCED\n4) CLEAR");
        System.out.print("Enter choice: ");
        int choice = InputHelper.readInt();

        switch (choice) {
            case 1 -> filter.setLevel(InternshipLevel.BASIC);
            case 2 -> filter.setLevel(InternshipLevel.INTERMEDIATE);
            case 3 -> filter.setLevel(InternshipLevel.ADVANCED);
            case 4 -> filter.setLevel(null);
            default -> System.out.println("Invalid choice, keeping previous level.");
        }
        System.out.println("Level updated: " + display(filter.getLevel()));
    }

    private static void updateClosingDate(FilterCriteria filter) {
        String dateInput = AttributeGetter.getString("Enter closing date (yyyy-MM-dd) or 0 to clear: ");
        if (dateInput.equals("0")) {
            filter.setClosingDate(null);
        } else {
            try {
                filter.setClosingDate(LocalDate.parse(dateInput, DMY));
            } catch (Exception e) {
                System.out.println("Invalid date format, keeping previous closing date.");
            }
        }
        System.out.println("Closing date updated: " + display(filter.getClosingDate()));
    }

    private static void clearAll(FilterCriteria filter) {
        filter.setStatus(null);
        filter.setPreferredMajor(null);
        filter.setLevel(null);
        filter.setClosingDate(null);
        System.out.println("All filters cleared.");
    }

    private static String display(Object obj) {
        return obj == null ? "None" : obj.toString();
    }
}
