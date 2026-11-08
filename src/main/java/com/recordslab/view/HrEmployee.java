package com.recordslab.view;

import com.recordslab.format.Dates;
import com.recordslab.format.Emails;
import com.recordslab.format.Money;
import com.recordslab.model.Department;
import com.recordslab.model.Employee;

/** The HR view: everything, pay and national id included (rule 1). Only for X-Role: hr. */
public record HrEmployee(long id, String givenName, String familyName, String email, String department,
                         String hiredAt, String salary, String monthly, String nationalId) {

    public static HrEmployee of(Employee e, Department d) {
        return new HrEmployee(e.id(), e.givenName(), e.familyName(), Emails.normal(e.email()), d.name(),
                Dates.iso(e.hiredAt()), Money.format(e.salary()), Money.monthly(e.salary()).toPlainString(),
                e.nationalId());
    }
}
