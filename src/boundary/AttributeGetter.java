package boundary;

import util.io.InputHelper;

import java.util.Scanner;

public class AttributeGetter {

    public static String getUserId(){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your user ID: ");
        return input.nextLine();
    }

    public static String getUserId(String msg){
        Scanner input = new Scanner(System.in);
        System.out.print(msg);
        return input.nextLine();
    }

    public static String getPassword(){
        System.out.print("Enter your password: ");
        return InputHelper.passwordReader();
    }
    public static String getPassword(String msg){
        System.out.print(msg);
        return InputHelper.passwordReader();
    }


    public static String getName(){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your name: ");
        return input.nextLine();
    }

    public static String getString(String msg){
        System.out.print(msg);
        return new Scanner(System.in).nextLine();
    }

    // to add more


}
