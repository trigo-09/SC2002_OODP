package boundary.terminal;

import util.io.ChangePage;
import util.io.GraphicLogo;

/**
 * UI class to display exit page and end terminal
 * terminal is stopped and system is killed
 */
public class Exit {
    /**
     * display exit page and end terminal session
     */
    public static void exit() {
        ChangePage.changePage();
        System.out.println("Thank you for using us!");
        System.out.println(GraphicLogo.EXIT_LOGO);
        System.exit(0);
    }
}
