package com.recordslab.format;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/** Rule 3: every format shows a time as ISO-8601 in UTC to the second, such as 2024-05-01T09:30:00Z. */
public final class Dates {
    private static final DateTimeFormatter ISO = DateTimeFormatter.ISO_INSTANT;

    private Dates() {
    }

    public static String iso(Instant time) {
        if (time == null) {
            return null;
        }
        return ISO.format(time.truncatedTo(ChronoUnit.SECONDS));
    }
}
