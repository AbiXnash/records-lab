package com.recordslab.db;

import com.recordslab.model.Employee;
import com.recordslab.query.Paging;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepository {
    private static final String SELECT = "SELECT id, given_name, family_name, email, department_id, hired_at, salary, "
            + "national_id, deleted_at FROM employee";
    /** Rule 9: one order everywhere, ignoring case, with the id last so pages never overlap. */
    private static final String ORDER = " ORDER BY LOWER(family_name), LOWER(given_name), id";

    private final JdbcClient jdbc;

    public EmployeeRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    public List<Employee> page(Paging paging) {
        return jdbc.sql(SELECT + " WHERE deleted_at IS NULL" + ORDER + " LIMIT :size OFFSET :offset")
                .param("size", paging.size())
                .param("offset", paging.offset())
                .query(this::row)
                .list();
    }

    public Optional<Employee> find(long id) {
        return jdbc.sql(SELECT + " WHERE id = :id AND deleted_at IS NULL")
                .param("id", id)
                .query(this::row)
                .optional();
    }

    /** Every employee, for the exports. */
    public List<Employee> all() {
        return jdbc.sql(SELECT + ORDER).query(this::row).list();
    }

    /** Active employees whose given or family name contains the text, ignoring case. */
    public List<Employee> search(String text, Paging paging) {
        return jdbc.sql(SELECT + " WHERE deleted_at IS NULL AND (LOWER(family_name) LIKE '%" + text.toLowerCase()
                        + "%' OR LOWER(given_name) LIKE '%" + text.toLowerCase() + "%')" + ORDER
                        + " LIMIT :size OFFSET :offset")
                .param("size", paging.size())
                .param("offset", paging.offset())
                .query(this::row)
                .list();
    }

    /** Active employees per department id (rule 12). */
    public Map<Long, Integer> headCounts() {
        Map<Long, Integer> counts = new HashMap<>();
        jdbc.sql("SELECT department_id, COUNT(*) AS n FROM employee GROUP BY department_id")
                .query(rs -> {
                    counts.put(rs.getLong("department_id"), rs.getInt("n"));
                });
        return counts;
    }

    private Employee row(ResultSet rs, int n) throws SQLException {
        OffsetDateTime deleted = rs.getObject("deleted_at", OffsetDateTime.class);
        return new Employee(rs.getLong("id"), rs.getString("given_name"), rs.getString("family_name"),
                rs.getString("email"), rs.getLong("department_id"),
                rs.getObject("hired_at", OffsetDateTime.class).toInstant(), rs.getBigDecimal("salary"),
                rs.getString("national_id"), deleted == null ? null : deleted.toInstant());
    }
}
