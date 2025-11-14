package util.io;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
/**
 * A utility class to help with common console input operations
 */
public class InputHelper {
    /** 
    * Shared scanner for all input operations. 
    * Date formatter for day-month-year format
    * Indentation formatting for UI
    */
    private static Scanner sc = new Scanner(System.in);
    private static final DateTimeFormatter DMY = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final String SPACE = "    ";
    /**
    * Shared Scanner for program
    * @param shared scanner
    */
    public static void init(Scanner shared) { sc = shared; }
    /**
     * Reads a full line of input and trims any leading or trailing whitespace.
     *
     * @return the trimmed line of user input
     */
    public static String readLine() {
        return sc.nextLine().trim();
    }
    /**
     * Attempts to read an integer from user input.
     * If the input is invalid, the user is repeatedly prompted until
     * a valid integer is entered.
     *
     * @return the integer entered by the user
     */
    public static int readInt() {
        try {
            int value = sc.nextInt();
            sc.nextLine(); // consume the leftover newline
            return value;
        } catch (Exception e) {
            System.out.println("Invalid input");
            System.out.print("Please enter a valid integer: ");
            sc.nextLine(); // clear bad input
            return readInt();
        }
    }
    /**
     * Reads a password from console input. If the system supports secure
     * password input, it will be used. Else, a normal line read is performed.
     *
     * @return the password as a String
     */
    public static String passwordReader() {
        String password;
        if (System.console() == null) {
            password = sc.nextLine();
        } else {
            password = new String(System.console().readPassword());
        }
        return password;
    }
     /**
     * Similar to readInt but returns null if input is blank.
     *
     * @return the parsed integer or null if blank/invalid
     */
    public static Integer readOptionalInt() {
        String input = readLine();
        if (input.isEmpty()) return null;
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    /**
     * Repeatedly prompts the user to enter a date in DD-MM-YYYY format.
     * Continues to retry until a valid date is provided.
     *
     * @return the parsed date
     */
    public static LocalDate readOpenDate() {
        while (true) {
            String in = readLine();
            try { return LocalDate.parse(in, DMY); }
            catch (DateTimeParseException e) {
                System.out.println("Invalid date, use DD-MM-YYYY.");
                System.out.print("Enter Opening Date (DD-MM-YYYY): ");
            }
        }
    }
    /**
     * Repeatedly prompts the user to enter a closing date in DD-MM-YYYY format.
     * Continues to retry until a valid date is provided.
     *
     * @return the parsed date
     */
    public static LocalDate readCloseDate() {
        while (true) {
            String in = readLine();
            try { return LocalDate.parse(in, DMY); }
            catch (DateTimeParseException e) {
                System.out.println("Invalid date, use DD-MM-YYYY.");
                System.out.print("Enter Opening Date (DD-MM-YYYY): ");
            }
        }
    }
    
    /**
     * Reads an optional opening date. If the input is blank,
     * null is returned. Otherwise, validates the date format.
     *
     * @return the parsed date or null if blank
     */
    public static LocalDate readOptionalOpenDate() {
        System.out.print("Open Date (DD-MM-YYYY) or leave blank to keep:");
        String string = readLine();
        if (string.isEmpty()) return null;
        try { return LocalDate.parse(string, DMY); }
        catch (DateTimeParseException e) {
            System.out.println("Invalid date, use DD-MM-YYYY or leave blank.");
            return readOptionalOpenDate();
        }
    }
     /**
     * Reads an optional closing date. If the input is blank,
     * null is returned. Otherwise, validates the date.
     *
     * @return the parsed date or null if blank
     */
    public static LocalDate readOptionalCloseDate() {
        System.out.print("Closing Date (DD-MM-YYYY) or leave blank to keep:");
        String string = readLine();
        if (string.isEmpty()) return null;
        try { return LocalDate.parse(string, DMY); }
        catch (DateTimeParseException e) {
            System.out.println("Invalid date, use DD-MM-YYYY or leave blank.");
            return readOptionalCloseDate();
        }
    }
    /**
     * Displays a prompt and waits for the user to press Enter.
     */
    public static void pause() {
        System.out.print("Press Enter to continue...");
        readLine();
    }


}
