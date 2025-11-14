package util;

import util.io.InputHelper;

/**
 * The AttributeGetter class provides helper methods to prompt
 * and receive user inputs while doing basic validation
 */
public class AttributeGetter {

    /**
     *  prompt user for id and return the input
     * @return String
     */
    public static String getUserId(){
        System.out.print("Enter your user ID: ");
        return InputHelper.readLine();
    }

    /**
     *  prompt user with specified msg and return the input
     * @return String
     */
    public static String getUserId(String msg){
        System.out.print(msg);
        return InputHelper.readLine().trim();
    }

    /**
     *  prompt user for password and return the input
     * @return String
     */
    public static String getPassword(){
        System.out.print("Enter your password: ");
        return InputHelper.passwordReader().trim();
    }

    /**
     *  prompt user for password with specified message and return the input
     * @return String
     */
    public static String getPassword(String msg){
        System.out.print(msg);
        return InputHelper.passwordReader().trim();
    }

    /**
     *  prompt user for name and return the input
     * @return String
     */
    public static String getName(){
        System.out.print("Enter your name: ");
        return InputHelper.readLine();
    }

    /**
     *  prompt user with specified msg and return the input
     * @return String
     */
    public static String getString(String msg){
        System.out.print(msg);
        return InputHelper.readLine();
    }
}
