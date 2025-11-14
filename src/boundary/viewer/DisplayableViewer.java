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
public class DisplayableViewer {
    public static <T extends Displayable> void displaySingle(T displayable) {
        if (displayable == null) {
            System.out.println("Nothing to display");
            return;
        }
        System.out.println(displayable.getTopBorder());
        System.out.print(displayable);
        System.out.println(displayable.getBottomBorder());

    }

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

    public static <T extends Displayable> void displayList(List<T> displayableList){
        DisplayableViewer.displayList(displayableList, "Nothing is found");
    }

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

    public static <T extends Displayable> void displayList(List<T> displayableList, UserController userController){
        DisplayableViewer.displayList(displayableList, "Nothing is found", userController);
    }


}
