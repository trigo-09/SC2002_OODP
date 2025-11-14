package boundary;

import entity.FilterCriteria;
import entity.internship.*;
import util.io.ChangePage;
import util.io.GraphicLogo;
import util.io.InputHelper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The FilterUI class manage the user interface for applying and modifying filter
 * criteria for internship opportunities such as internship status, preferred major, internship level, and closing date.
 */
public class FilterUI {
    private static final DateTimeFormatter DMY = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Updates the filter settings
     * Displays a menu of criterias of the filter
     * allows the user to clear all filters as well.
     *
     * @param filter The {@code FilterCriteria} object that stores the current filter settings.
     */
    public static void update(FilterCriteria filter) {
        ChangePage.changePage();
        boolean back = false;
        while (!back) {
            System.out.println("FILTER SETTINGS");
            System.out.println(GraphicLogo.SEPARATOR);
            System.out.println("\t1. Change Status (" + display(filter.getStatus()) + ")");
            System.out.println("\t2. Change Preferred Major (" + display(filter.getPreferredMajor()) + ")");
            System.out.println("\t3. Change Internship Level (" + display(filter.getLevel()) + ")");
            System.out.println("\t4. Change Closing Date (" + display(filter.getClosingDate()) + ")");
            System.out.println("\t5. Clear All Filters");
            System.out.println("\t0. Back");
            System.out.println(GraphicLogo.SEPARATOR +"\n");
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

    /**
     * Displays a menu of possible status options, accepts user input to select the status
     * @param filter The {@code FilterCriteria}
     */
    private static void updateStatus(FilterCriteria filter) {
        System.out.println("Select Status:");
        System.out.println("1. PENDING\n2. APPROVED\n3. REJECTED\n4. FILLED\n5. CLEAR");
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
        InputHelper.pause();
    }

    /**
     * accepts user input to save prefered major or
     * accept 0 to make major = null
     * @param filter The {@code FilterCriteria}
     */
    private static void updateMajor(FilterCriteria filter) {
        String major = AttributeGetter.getString("Enter preferred major (or '0' to clear): ");
        if (major.equals("0")) filter.setPreferredMajor(null);
        else filter.setPreferredMajor(major);
        System.out.println("Preferred Major updated: " + display(filter.getPreferredMajor()));
        InputHelper.pause();
    }

    /**
     * Displays a menu of possible internhsip level options,
     * accepts user input to select the level
     * @param filter The {@code FilterCriteria}
     */
    private static void updateLevel(FilterCriteria filter) {
        System.out.println("Select Internship Level:");
        System.out.println("1. BASIC\n2. INTERMEDIATE\n3. ADVANCED\n4. CLEAR");
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
        InputHelper.pause();
    }

    /**
     *  accepts user input and set closing date or null it when = 0
     * @param filter The {@code FilterCriteria}
     */
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
        InputHelper.pause();
    }

    /**
     *clear all filter criteria by setting all the criteria to null
     * @param filter The {@code FilterCriteria}
     */
    private static void clearAll(FilterCriteria filter) {
        filter.setStatus(null);
        filter.setPreferredMajor(null);
        filter.setLevel(null);
        filter.setClosingDate(null);
        System.out.println("All filters cleared.");
        InputHelper.pause();
    }

    /**
     * Converts the provided object to its string
     * @param obj The object to be converted
     * @return The string
     */
    private static String display(Object obj) {
        return obj == null ? "None" : obj.toString();
    }
}
