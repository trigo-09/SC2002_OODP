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

    public static LocalDate readDate() {
        while (true) {
            String in = readLine();
            try { return LocalDate.parse(in, DMY); }
            catch (DateTimeParseException e) { System.out.println("Invalid date, use DD-MM-YYYY."); }
        }
    }
    

    public static LocalDate readOptionalDate() {
        String string = readLine();
        if (string.isEmpty()) return null;
        try { return LocalDate.parse(string, DMY); }
        catch (DateTimeParseException e) {
            System.out.println("Invalid date, use DD-MM-YYYY or leave blank.");
            return readOptionalDate();
        }
    }
    
       
     
    public static void printMenuItem(int num, String label) {
        System.out.println(SPACE + num + ". " + label);
    }

    public static void pause() {
        System.out.print("Press Enter to continue...");
        readLine();
    }


}
