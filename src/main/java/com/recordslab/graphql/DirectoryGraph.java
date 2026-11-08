package com.recordslab.graphql;

import com.recordslab.db.DepartmentRepository;
import com.recordslab.db.EmployeeRepository;
import com.recordslab.format.Dates;
import com.recordslab.format.Emails;
import com.recordslab.model.Department;
import com.recordslab.model.Employee;
import com.recordslab.query.Paging;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

/** The intranet's GraphQL API. The schema has no pay or national id fields (rule 1). */
@Controller
public class DirectoryGraph {
    private final EmployeeRepository employees;
    private final DepartmentRepository departments;

    public DirectoryGraph(EmployeeRepository employees, DepartmentRepository departments) {
        this.employees = employees;
        this.departments = departments;
    }

    @QueryMapping
    public List<Employee> employees(@Argument Integer page, @Argument Integer size) {
        return employees.page(new Paging(page == null ? 1 : page, size == null ? Paging.DEFAULT_SIZE : size));
    }

    @QueryMapping
    public Employee employee(@Argument long id) {
        return employees.find(id).orElse(null);
    }

    @QueryMapping
    public List<Department> departments() {
        return departments.all();
    }

    @SchemaMapping(typeName = "Employee")
    public String email(Employee e) {
        return Emails.normal(e.email());
    }

    @SchemaMapping(typeName = "Employee")
    public String hiredAt(Employee e) {
        return Dates.iso(e.hiredAt());
    }

    /** All the departments of one page of employees in one read, not one per employee. */
    @BatchMapping(typeName = "Employee")
    public Map<Employee, Department> department(List<Employee> list) {
        Map<Long, Department> byId = departments.byId();
        Map<Employee, Department> out = new LinkedHashMap<>();
        for (Employee e : list) {
            out.put(e, byId.get(e.departmentId()));
        }
        return out;
    }

    @BatchMapping(typeName = "Department")
    public Map<Department, Integer> headCount(List<Department> list) {
        Map<Long, Integer> counts = employees.headCounts();
        Map<Department, Integer> out = new LinkedHashMap<>();
        for (Department d : list) {
            out.put(d, counts.getOrDefault(d.id(), 0));
        }
        return out;
    }
}
