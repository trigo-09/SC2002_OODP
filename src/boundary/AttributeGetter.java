package boundary;

import util.io.InputHelper;

public class AttributeGetter {

    public static String getUserId(){
        System.out.print("Enter your user ID: ");
        return InputHelper.readLine();
    }

    public static String getUserId(String msg){
        System.out.print(msg);
        return InputHelper.readLine().trim();
    }

    public static String getPassword(){
        System.out.print("Enter your password: ");
        return InputHelper.passwordReader().trim();
    }
    public static String getPassword(String msg){
        System.out.print(msg);
        return InputHelper.passwordReader();
    }


    public static String getName(){
        System.out.print("Enter your name: ");
        return InputHelper.readLine();
    }

    public static String getString(String msg){
        System.out.print(msg);
        return InputHelper.readLine();
    }

    // to add more


}
