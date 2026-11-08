package com.recordslab.format;

import java.util.Locale;

/** Rule 10: emails are shown in lower case in every format. */
public final class Emails {
    private Emails() {
    }

    public static String normal(String email) {
        if (email == null) {
            return null;
        }
        return email.trim().toLowerCase(Locale.ROOT);
    }
}
