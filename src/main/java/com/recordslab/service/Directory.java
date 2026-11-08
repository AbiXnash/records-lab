package com.recordslab.service;

import com.recordslab.db.DepartmentRepository;
import com.recordslab.db.EmployeeRepository;
import com.recordslab.model.Department;
import com.recordslab.model.Employee;
import com.recordslab.query.Paging;
import com.recordslab.view.DepartmentView;
import com.recordslab.view.HrEmployee;
import com.recordslab.view.PublicEmployee;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class Directory {
    private final EmployeeRepository employees;
    private final DepartmentRepository departments;

    public Directory(EmployeeRepository employees, DepartmentRepository departments) {
        this.employees = employees;
        this.departments = departments;
    }

    public List<PublicEmployee> page(Paging paging) {
        Map<Long, Department> byId = departments.byId();
        return employees.page(paging).stream().map(e -> PublicEmployee.of(e, byId.get(e.departmentId()))).toList();
    }

    public List<PublicEmployee> search(String text, Paging paging) {
        Map<Long, Department> byId = departments.byId();
        return employees.search(text, paging).stream().map(e -> PublicEmployee.of(e, byId.get(e.departmentId()))).toList();
    }

    public Optional<PublicEmployee> one(long id) {
        Map<Long, Department> byId = departments.byId();
        return employees.find(id).map(e -> PublicEmployee.of(e, byId.get(e.departmentId())));
    }

    public Optional<HrEmployee> hr(long id) {
        return employees.find(id).map(e -> HrEmployee.of(e, departments.find(e.departmentId()).get()));
    }

    /** Everyone, for a full export. */
    public List<PublicEmployee> everyone() {
        List<PublicEmployee> out = new ArrayList<>();
        for (Employee e : employees.all()) {
            Department d = departments.find(e.departmentId()).orElseThrow();
            out.add(PublicEmployee.of(e, d));
        }
        return out;
    }

    public Optional<DepartmentView> department(String code) {
        Map<Long, Integer> counts = employees.headCounts();
        return departments.all().stream()
                .filter(d -> d.code().equalsIgnoreCase(code))
                .findFirst()
                .map(d -> DepartmentView.of(d, counts.getOrDefault(d.id(), 0)));
    }

    public List<DepartmentView> departments() {
        Map<Long, Integer> counts = employees.headCounts();
        return departments.all().stream().map(d -> DepartmentView.of(d, counts.getOrDefault(d.id(), 0))).toList();
    }
}
