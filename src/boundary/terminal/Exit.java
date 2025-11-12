package boundary.terminal;

import util.io.ChangePage;
import util.io.GraphicLogo;

/**
 * UI class to display exit page and end terminal
 */
public class Exit {
    /**
     * prevent instantiation of Exit class
     */
    private Exit(){}

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
