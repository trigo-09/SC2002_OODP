package util.io;

import java.util.Scanner;

public class InputHelper {
    private static Scanner sc = new Scanner(System.in);

    public static void init(Scanner shared) { sc = shared; }

    public static String readLine() {
        return sc.nextLine().trim();
    }

    public static int readInt() {
        try {
            return sc.nextInt();
        }catch (Exception e){
            System.out.println("Invalid input");
            System.out.println("Please enter a valid integer");
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
}
