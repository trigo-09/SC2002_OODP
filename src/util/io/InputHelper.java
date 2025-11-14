package util.io;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputHelper {
    private static Scanner sc = new Scanner(System.in);
    private static final DateTimeFormatter DMY = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final String SPACE = "    ";

    public static void init(Scanner shared) { sc = shared; }

    public static String readLine() {
        return sc.nextLine().trim();
    }

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

    public static String passwordReader() {
        String password;
        if (System.console() == null) {
            password = sc.nextLine();
        } else {
            password = new String(System.console().readPassword());
        }
        return password;
    }

    public static Integer readOptionalInt() {
        String input = readLine();
        if (input.isEmpty()) return null;
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return null;
        }
    }

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

    public static void pause() {
        System.out.print("Press Enter to continue...");
        readLine();
    }


}
