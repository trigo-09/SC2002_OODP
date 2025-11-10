package util.ui;


public class UIHelper {
    /**
     * prevent instantiation
     */
    private UIHelper() {}

    /**
     * to maintain formating
     */
    private static final String SPACE = "    ";

    public static void printMenuItem(int num, String label) {
        System.out.println(SPACE + num + ". " + label);
    }



}
