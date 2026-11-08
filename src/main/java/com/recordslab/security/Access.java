package com.recordslab.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/** Rule 1: only a request with X-Role: hr sees pay and national ids. */
public final class Access {
    public static final String HEADER = "X-Role";
    public static final String HR = "hr";

    private Access() {
    }

    public static void requireHr(String role) {
        if (!HR.equals(role)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This view is for HR only.");
        }
    }
}
