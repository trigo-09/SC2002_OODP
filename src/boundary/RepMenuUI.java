package boundary;

import controller.control.user.RepController;
import entity.application.Application;
import entity.internship.InternshipLevel;
import entity.internship.InternshipOpportunity;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

 /**
  * Console menu for company representatives to create and manage internship opportunities.
  */
public class RepMenuUI {
    private final RepController repController;
    private final Scanner sc;
    private static final DateTimeFormatter DMY = DateTimeFormatter.ofPattern("dd-MM-yyyy");

     /**
      * Creates a representative menu bound to the given controller and shared input scanner.
      *
      * @param repController controller handling representative operations
      * @param sc            shared Scanner for user input (not closed here)
      */
    public RepMenuUI(RepController repController, Scanner sc) {
        this.repController = repController;
        this.sc = sc;
    }
    /**
     * Display the main menu and handle user actions until logout is chosen.
     * All business-rule and authorization errors are caught and shown to the user.
     */
    public void displayMenu() {
        int choice = -1;
        while (choice != 0) {
            System.out.println("\n=== Company Representative Menu ===");
            System.out.println("1) Create Internship Opportunity");
            System.out.println("2) Manage Your Internship Opportunities");
            System.out.println("0) Logout");
            System.out.print("Enter your choice: ");
            choice = readIntSafe();

            switch (choice) {
                case 1 -> createInternshipUI();
                case 2 -> manageInternshipsUI();
                case 0 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

     /**
      * Prompt the user for internship fields (title, description, level, majors, dates, slots),
      * validate inputs (e.g., closing date ≥ opening date, slots between 1 and 10), and create the opportunity.
      * Business-rule violations (e.g., max postings reached) are caught and shown.
      */
    private void createInternshipUI() {
        System.out.println("=== Create Internship Opportunity ===");
        System.out.print("Title: ");
        String title = sc.nextLine().trim();
        System.out.print("Description: ");
        String description = sc.nextLine().trim();

        InternshipLevel level;

        // Re-prompt until valid level is entered
        // Convert user input to InternshipLevel enum
        while (true) {
            System.out.print("Level (Basic / Intermediate / Advanced): ");
            String input = sc.nextLine().trim();
            if (input.equalsIgnoreCase("Basic")) {
                level = InternshipLevel.BASIC;
                break;
            } else if (input.equalsIgnoreCase("Intermediate")) {
                level = InternshipLevel.INTERMEDIATE;
                break;
            } else if (input.equalsIgnoreCase("Advanced")) {
                level = InternshipLevel.ADVANCED;
                break;
            }
            System.out.println("Invalid level. Please type Basic, Intermediate, or Advanced.");
        }
        
        // Currently only supports a single preferred major input (or 'Any')
        System.out.print("Preferred Major or 'Any': ");
        String preferredMajor = sc.nextLine().trim();
        
        LocalDate openingDate = readDate("Opening Date (DD-MM-YYYY): ");
        LocalDate closingDate;

        // Re-prompt until closing date is after opening date
        while (true) {
            closingDate = readDate("Closing Date (DD-MM-YYYY): ");
            if (!closingDate.isBefore(openingDate)) break;
            System.out.println("Closing date cannot be before opening date. Please try again.");
        }

        int numOfSlots;

        // Re-prompt until valid number of slots is entered
        while (true) {
            System.out.print("Number of Slots (1..10): ");
            numOfSlots = readIntSafe();
            if (numOfSlots >= 1 && numOfSlots <= 10) break;
            System.out.println("Slots must be between 1 and 10. Try again.");
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
        } catch (IllegalArgumentException | IllegalStateException | SecurityException e) {
            System.out.println("Error creating internship: " + e.getMessage());
        }
        pause();
    }

     /**
      * List the representative's internships and allow actions such as toggling visibility
      * and listing and accepting/rejecting applications. Errors are caught and shown.
      */
    private void manageInternshipsUI() {
        int choice = -1;
        while (choice != 0) {
            System.out.println("=== Your Internship Opportunities ===");
            List<InternshipOpportunity> internships = repController.getInternships();
            if (internships.isEmpty()) {
                System.out.println("(No opportunities yet)");
            } else {
                internships.forEach(i -> System.out.println("- " + i));
            }

            System.out.println("Actions:");
            System.out.println("0) Back to Main Menu");
            System.out.println("1) Set Internship Visibility");
            System.out.println("2) Manage Applications for an Internship");
            System.out.print("Enter your choice: ");
            choice = readIntSafe();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Internship ID: ");
                    String internshipId = sc.nextLine().trim();
                    System.out.print("Set visibility (1 = visible, 0 = hidden): ");
                    int visChoice = readIntSafe();
                    boolean visibility = (visChoice == 1);

                    try {
                        repController.toggleVisibility(internshipId, visibility);
                        System.out.println("Internship visibility updated to " + (visibility ? "VISIBLE" : "HIDDEN") + ".");
                    } catch (IllegalArgumentException | IllegalStateException | SecurityException e) {
                        System.out.println("Error updating internship visibility: " + e.getMessage());
                    }
                    pause();
                }

                case 2 -> {
                    System.out.print("Enter Internship ID to manage applications: ");
                    String internId = sc.nextLine().trim();

                    try {
                        List<Application> applications = repController.getApplications(internId);
                        if (applications.isEmpty()) {
                            System.out.println("(No applications yet)");
                            pause();
                            continue;
                        } else {
                            applications.forEach(a -> System.out.println("- " + a));
                        }
                        pause();
                    } catch (IllegalArgumentException | IllegalStateException | SecurityException e) {
                        System.out.println("Error retrieving applications: " + e.getMessage());
                    }

                    System.out.print("1: Accept or Reject an Application, 0: back: ");
                    int appChoice = readIntSafe();

                    if (appChoice == 1) {
                        System.out.print("Enter Application ID: ");
                        String appId = sc.nextLine().trim();
                        System.out.print("Accept (1) or Reject (0) the application? ");
                        int decision = readIntSafe();

                        try {
                            if (decision == 1) {
                                repController.approveApp(appId);
                                System.out.println("Application accepted.");
                            } else {
                                repController.rejectApp(appId);
                                System.out.println("Application rejected.");
                            }
                        } catch (IllegalArgumentException | IllegalStateException | SecurityException e) {
                            System.out.println("Error approving/rejecting application: " + e.getMessage());
                        }
                        pause();
                    }
                }
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    /**
     * Reads an integer from user input safely, reprompting on invalid input.
     * @return The integer input by the user.
     */
    private int readIntSafe() {
        while (true) {
            String in = sc.nextLine().trim();
            try { return Integer.parseInt(in); }
            catch (NumberFormatException e) { System.out.print("Please enter a number: "); }
        }
    }

    /**
     * Reads a date from user input in DD-MM-YYYY format, reprompting on invalid input.
     * @param prompt The prompt message to display.
     * @return The LocalDate input by the user.
     */
    private LocalDate readDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String in = sc.nextLine().trim();
            try { return LocalDate.parse(in, DMY); }
            catch (DateTimeParseException e) { System.out.println("Invalid date, use DD-MM-YYYY."); }
        }
    }

    /**
     * Pauses execution until the user presses Enter.
     * This is useful for allowing the user to read messages before continuing.
     */
    private void pause() {
        System.out.print("Press Enter to continue...");
        sc.nextLine();
    }
}
