package util.io;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputHelper {
    public static int readInt() {
        try {
            return new Scanner(System.in).nextInt();
        }catch (Exception e){
            System.out.println("Invalid input");
            System.out.println("Please enter a valid integer");
            return readInt();
        }
    }

    public static String passwordReader() {
        String password;
        if (System.console() == null) {
            password = new Scanner(System.in).nextLine();
        } else {
            password = new String(System.console().readPassword());
        }
        return password;
    }
}
