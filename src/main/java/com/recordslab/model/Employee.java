package com.recordslab.model;

import java.math.BigDecimal;
import java.time.Instant;

/** One row of the employee table. Salary and nationalId are personal data: only HrEmployee shows them (rule 1). */
public record Employee(long id, String givenName, String familyName, String email, long departmentId,
                       Instant hiredAt, BigDecimal salary, String nationalId, Instant deletedAt) {

    public boolean active() {
        return deletedAt == null;
    }
}
