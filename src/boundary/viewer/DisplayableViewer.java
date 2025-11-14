package boundary.viewer;

import controller.control.user.RepController;
import controller.control.user.StaffController;
import controller.control.user.StudentController;
import controller.control.user.UserController;
import entity.Displayable;
import entity.application.Application;
import entity.internship.InternshipOpportunity;
import entity.user.Student;
import util.io.AsciiTableFormatter;
import java.util.List;
import java.util.Objects;

// Student, Requests, InternshipOpportunity, Application

/**
 * Utility class for rendering objects that implement the Displayable interface and print.
 */
public class DisplayableViewer {

    /**
     * Displays a single {@code Displayable} with borders
     *
     * @param <T> the type of the object that extends {@link Displayable}
     * @param displayable the {@link Displayable} object to be displayed;
     */
    public static <T extends Displayable> void displaySingle(T displayable) {
        if (displayable == null) {
            System.out.println("Nothing to display");
            return;
        }
        System.out.println(displayable.getTopBorder());
        System.out.print(displayable);
        System.out.println(displayable.getBottomBorder());

    }

    /**
     * Displays a single {@code Displayable} object along with its top and bottom borders.
     * If null, a default message is printed.
     * @param <T> the type of the object that extends {@link Displayable}
     * @param displayable the {@link Displayable} object to be displayed;
     * @param userController the {@link UserController} instance used to determine additional information to display,
     *                       based on the type of controller.
     */
    public static <T extends Displayable> void displaySingle(T displayable, UserController userController) {
        if (displayable == null) {
            System.out.println("Nothing to display");
            return;
        }
        System.out.println(displayable.getTopBorder());
        System.out.print(displayable);
        if ( userController instanceof StudentController && displayable instanceof Application) {
            Student student = (Student) ((StudentController) userController).getStudentById(((Application) displayable).getStudentId());
            System.out.println(student.getMidBorder());
            System.out.print(student);
            System.out.println(student.getMidBorder());
            InternshipOpportunity intern = ((StudentController) userController).getInternshipById(((Application) displayable).getInternshipId());
            System.out.print(intern);
        }
        if ( (userController instanceof RepController && displayable instanceof Application)) {
            Student student = (Student) ((RepController) userController).getStudentById(((Application) displayable).getStudentId());
            System.out.println(student.getMidBorder());
            System.out.print(student);
            System.out.println(student.getMidBorder());
            InternshipOpportunity intern = ((RepController) userController).getInternshipById(((Application) displayable).getInternshipId());
            System.out.print(intern);
        }
        System.out.println(displayable.getBottomBorder());

    }

    /**
     * Displays a list of {@code Displayable} objects with borders.
     * If null, a specified message will be printed instead.
     * @param <T> the type of objects that extends {@link Displayable}
     * @param displayableList the list of {@link Displayable} objects to be displayed
     * @param message the message to be displayed if the list is empty
     */
    public static <T extends Displayable> void displayList(List<T> displayableList, String message){
        if (Objects.isNull(displayableList) || displayableList.isEmpty()){
            System.out.println(message);
            return;
        }

        for (int i = 0; i < displayableList.size(); i++){
            Displayable displayable = displayableList.get(i);
            System.out.println(displayable.getTopBorder());
            String indexText = "Index: [" + (i + 1) + "]";
            int padding = AsciiTableFormatter.TABLE_WIDTH - 4 - indexText.length();
            System.out.println("\u2502 " + indexText + " ".repeat(Math.max(0, padding)) + " \u2502");
            System.out.println(displayable.getMidBorder());
            System.out.print(displayable);
            System.out.println(displayable.getBottomBorder());
        }
    }

    /**
     * Displays a list of {@code Displayable} objects using the {@link DisplayableViewer}.
     * If null, a default message "Nothing is found" shown.
     * @param <T> the type of objects that extend {@link Displayable}
     * @param displayableList the list of {@link Displayable} objects to be displayed
     */
    public static <T extends Displayable> void displayList(List<T> displayableList){
        DisplayableViewer.displayList(displayableList, "Nothing is found");
    }

    /**
     * Displays a list of {@code Displayable} objects with borders.
     * if null a specified message is shown. Based on the type
     * of {@link UserController} and {@link Displayable}, extra information shown
     *
     * @param <T> the type of the objects that extend {@link Displayable}
     * @param displayableList the list of {@link Displayable} objects to be displayed
     * @param message the message to be displayed if null
     * @param userController the {@link UserController} instance used to provide necessary
     *                       context for displaying additional related information
     */
    public static <T extends Displayable> void displayList(List<T> displayableList, String message, UserController userController){
        if (Objects.isNull(displayableList) || displayableList.isEmpty()){
            System.out.println(message);
            return;
        }

        for (int i = 0; i < displayableList.size(); i++){
            Displayable displayable = displayableList.get(i);
            System.out.println(displayable.getTopBorder());
            String indexText = "Index: [" + (i + 1) + "]";
            int padding = AsciiTableFormatter.TABLE_WIDTH - 4 - indexText.length();
            System.out.println("\u2502 " + indexText + " ".repeat(Math.max(0, padding)) + " \u2502");
            System.out.println(displayable.getMidBorder());
            System.out.print(displayable);
            if ( (userController instanceof RepController && displayable instanceof Application)) {
                Student student = (Student) ((RepController) userController).getStudentById(((Application) displayable).getStudentId());
                System.out.println(student.getMidBorder());
                System.out.print(student);
                System.out.println(student.getMidBorder());
                InternshipOpportunity intern = ((RepController) userController).getInternshipById(((Application) displayable).getInternshipId());
                System.out.print(intern);
            }
            else if ( userController instanceof StudentController && displayable instanceof Application){
                Student student = (Student) ((StudentController) userController).getStudentById(((Application) displayable).getStudentId());
                System.out.println(student.getMidBorder());
                System.out.print(student);
                System.out.println(student.getMidBorder());
                InternshipOpportunity intern = ((StudentController) userController).getInternshipById(((Application) displayable).getInternshipId());
                System.out.print(intern);
            }
            else if ( userController instanceof StaffController && displayable instanceof Application){
                Student student = (Student) ((StaffController) userController).getStudentById(((Application) displayable).getStudentId());
                System.out.println(student.getMidBorder());
                System.out.print(student);
                System.out.println(student.getMidBorder());
                InternshipOpportunity intern = ((StaffController) userController).getInternshipById(((Application) displayable).getInternshipId());
                System.out.print(intern);
            }
            System.out.println(displayable.getBottomBorder());
        }
    }

    /**
     * Displays a list of {@code Displayable} objects using the {@link DisplayableViewer}.
     * If the list is empty, a default message "Nothing is found" shown.
     *
     * @param <T> the type of objects that extend {@link Displayable}
     * @param displayableList the list of {@link Displayable} objects to be displayed
     * @param userController the {@link UserController} instance used show extra info
     */
    public static <T extends Displayable> void displayList(List<T> displayableList, UserController userController){
        DisplayableViewer.displayList(displayableList, "Nothing is found", userController);
    }


}
