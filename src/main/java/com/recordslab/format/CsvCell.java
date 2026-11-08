package com.recordslab.format;

/** One CSV cell. Rule 8: a cell a spreadsheet would run as a formula gets a leading quote. */
public final class CsvCell {
    private static final String FORMULA_STARTS = "=+-@";

    private CsvCell() {
    }

    public static String of(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }
        String text = value;
        if (FORMULA_STARTS.indexOf(text.charAt(0)) >= 0) {
            text = "'" + text;
        }
        if (text.contains(",") || text.contains("\"") || text.contains("\n")) {
            text = "\"" + text.replace("\"", "\"\"") + "\"";
        }
        return text;
    }

    public static String row(String... values) {
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                line.append(',');
            }
            line.append(of(values[i]));
        }
        return line.append("\r\n").toString();
    }
}
