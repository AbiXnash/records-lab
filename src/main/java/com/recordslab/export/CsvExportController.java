package com.recordslab.export;

import com.recordslab.db.DepartmentRepository;
import com.recordslab.db.EmployeeRepository;
import com.recordslab.format.CsvCell;
import com.recordslab.model.Department;
import com.recordslab.model.Employee;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** Every active employee as CSV, for spreadsheets. */
@RestController
public class CsvExportController {
    private final EmployeeRepository employees;
    private final DepartmentRepository departments;

    public CsvExportController(EmployeeRepository employees, DepartmentRepository departments) {
        this.employees = employees;
        this.departments = departments;
    }

    @GetMapping(value = "/export/employees.csv", produces = "text/csv")
    public String export() {
        StringBuilder csv = new StringBuilder(CsvCell.row("id", "givenName", "familyName", "email", "department", "hiredAt"));
        Map<Long, Department> byId = departments.byId();
        for (Employee e : employees.all()) {
            csv.append(CsvCell.row(Long.toString(e.id()), e.givenName(), e.familyName(), e.email(),
                    byId.get(e.departmentId()).name(), e.hiredAt().toString()));
        }
        return csv.toString();
    }
}
