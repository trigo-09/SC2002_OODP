package boundary.viewer;

import entity.Displayable;

import java.util.List;
import java.util.Objects;

// Student, Requests, InternshipOpportunity, Application
public class DisplayableViewer {
    public static <T extends Displayable> void displaySingle(T displayable) {
        System.out.println(displayable.getSplitter());
        System.out.println(displayable);
        System.out.println(displayable.getSplitter());
    }

    public static <T extends Displayable> void displayList(List<T> displayableList){
        if (Objects.isNull(displayableList) || displayableList.isEmpty()){
            System.out.println("Nothing is found");
            return;
        }
        System.out.println(displayableList.get(0).getSplitter());
        for (int i = 0; i < displayableList.size(); i++){
            System.out.printf("Index: [%d]\n%s", i+1, displayableList.get(i));
            System.out.println(displayableList.get(i).getSplitter());
        }
    }
}
