package entity;

import util.io.AsciiTableFormatter;

public interface Displayable {
    String TOP_BORDER =
            "\u250C" + "\u2500".repeat(AsciiTableFormatter.TABLE_WIDTH - 2) + "\u2510";

    String MID_BORDER =
            "\u251C" + "\u2500".repeat(AsciiTableFormatter.TABLE_WIDTH - 2) + "\u2524";

    String BOTTOM_BORDER =
            "\u2514" + "\u2500".repeat(AsciiTableFormatter.TABLE_WIDTH - 2) + "\u2518";


    default String getTopBorder() {
        return TOP_BORDER;
    }

    default String getMidBorder(){
        return MID_BORDER;
    }

    default String getBottomBorder(){
        return BOTTOM_BORDER;
    }



}
