package boundary.terminal;

import util.ui.ChangePage;
import util.ui.GraphicLogo;

public class Exit {
    /**
     * prevent instantiation
     */
    private Exit(){}

    public static void exit() {
        ChangePage.changePage();
        System.out.println("Thank you for using us!");
        System.out.println(GraphicLogo.EXIT_LOGO);
        System.exit(0);
    }
}
