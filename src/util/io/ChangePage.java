package util.io;

import java.io.IOException;

/**
 * The ChangePage class is responsible for changing the console output screen to a new blank screen.
 * This is done using the appropriate command depending on the operating system.
 */
public class ChangePage {

    /**
     * prevent instantiation
     */
    private ChangePage() {}

    /**
     * Changes the console output screen to a new blank screen.
     * This is done using the appropriate command depending on the operating system.
     *
     */
    public static void changePage() {
        String os = System.getProperty("os.name").toLowerCase();
        try {
            if (os.contains("win")) {
                // Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Unix-like systems
                System.out.print("\u001b[H\u001b[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("\n".repeat(50));
            Thread.currentThread().interrupt();
        }
    }
}