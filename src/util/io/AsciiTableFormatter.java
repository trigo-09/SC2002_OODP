package util.io;

import java.util.ArrayList;
import java.util.List;

/**
 * class helps to format data into table form.
 */
public final class AsciiTableFormatter {

    public static final int TABLE_WIDTH = 80;
    public static final int LABEL_WIDTH = 21;
    public static final int VALUE_WIDTH = 52; // widths must add up to 73


    /**
     * format rows into table
     * @param rows rows
     * @return table
     */
    public static String formatTable(List<Row> rows) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < rows.size(); i++) {
            Row row = rows.get(i);
            appendRow(sb, row.label(), row.value());
        }

        return sb.toString();
    }

    /**
     *  builds string into rows
     * @param sb builder
     * @param label labeling
     * @param value value
     */
    public static void appendRow(StringBuilder sb, String label, String value) {
        if (value == null) value = "";
        List<String> wrappedLines = wrap(value, VALUE_WIDTH);

        for (int i = 0; i < wrappedLines.size(); i++) {
            String labelText = (i == 0) ? label : "";
            String valueText = wrappedLines.get(i);

            sb.append(String.format("\u2502 %-" + LABEL_WIDTH + "s \u2502 %-" + VALUE_WIDTH + "s \u2502%n", labelText, valueText));
        }
    }

    /**
     * modify string of text into list of string
     * @param text text
     * @param width size
     * @return list of string
     */
    private static List<String> wrap(String text, int width) {
        List<String> lines = new ArrayList<>();
        String remaining = text.trim();

        if (remaining.isEmpty()) {
            lines.add("");
            return lines;
        }

        while (remaining.length() > width) {
            int breakPos = remaining.lastIndexOf(' ', width); //finds the last space before or at the index width

            if (breakPos <= 0) {
                //Hardwrap at a dash
                String line = remaining.substring(0, width - 1) + "-";
                lines.add(line);

                // Continue wrapping remainder (starting from width - 1)
                remaining = remaining.substring(width - 1).trim();
                continue;
            }

            String line = remaining.substring(0, breakPos).trim();
            lines.add(line);
            remaining = remaining.substring(breakPos).trim();
        }

        if (!remaining.isEmpty()) {
            lines.add(remaining);
        }

        return lines;
    }

    // simple record to hold label/value pairs
    public record Row(String label, String value) {}
}
