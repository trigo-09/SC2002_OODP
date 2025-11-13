package boundary.viewer;

import entity.Displayable;
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


}
