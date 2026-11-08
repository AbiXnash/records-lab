package com.recordslab.view;

import com.recordslab.format.Dates;
import com.recordslab.format.Emails;
import com.recordslab.model.Department;
import com.recordslab.model.Employee;

/** What anyone may see about an employee (rule 1): no pay, no national id. */
public record PublicEmployee(long id, String givenName, String familyName, String email, String department,
                             String hiredAt) {

    public static PublicEmployee of(Employee e, Department d) {
        return new PublicEmployee(e.id(), e.givenName(), e.familyName(), Emails.normal(e.email()), d.name(),
                Dates.iso(e.hiredAt()));
    }
}
