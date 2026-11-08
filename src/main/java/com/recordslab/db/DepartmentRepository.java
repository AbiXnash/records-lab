package com.recordslab.db;

import com.recordslab.model.Department;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class DepartmentRepository {
    private final JdbcClient jdbc;

    public DepartmentRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    public List<Department> all() {
        return jdbc.sql("SELECT id, name, code FROM department ORDER BY name").query(Department.class).list();
    }

    public Optional<Department> find(long id) {
        return jdbc.sql("SELECT id, name, code FROM department WHERE id = :id")
                .param("id", id)
                .query(Department.class)
                .optional();
    }

    /** Every department by id, read once so a list of employees never queries per row. */
    public Map<Long, Department> byId() {
        Map<Long, Department> out = new LinkedHashMap<>();
        for (Department d : all()) {
            out.put(d.id(), d);
        }
        return out;
    }
}
