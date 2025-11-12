package entity;

public interface Displayable {
    String Splitter = "----------------------------------";

    default String getSplitter(){
        return Splitter;
    }
}
