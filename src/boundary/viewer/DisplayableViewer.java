package boundary.viewer;

import entity.Displayable;
import entity.internship.InternshipOpportunity;

import java.util.List;
import java.util.Objects;

// Student, Requests, InternshipOpportunity, Application
public class DisplayableViewer {
    public static <T extends Displayable> void displaySingleInternship(T displayable){
        System.out.println(displayable.getSplitter());
        System.out.println(displayable.getString());
        System.out.println(displayable.getSplitter());
    }

    public static <T extends Displayable> void displayListInternship(List<T> displayableList){
        if (Objects.isNull(displayableList) || displayableList.isEmpty()){
            System.out.println("Nothing is found");
            return;
        }
        System.out.println(displayableList.get(0).getSplitter());
        for (Displayable i : displayableList){
            System.out.println(i.getString());
            System.out.println(i.getSplitter());
        }
    }
}
