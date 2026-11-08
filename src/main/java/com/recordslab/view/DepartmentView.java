package com.recordslab.view;

import com.recordslab.model.Department;

public record DepartmentView(long id, String name, String code, int headCount) {

    public static DepartmentView of(Department d, int headCount) {
        return new DepartmentView(d.id(), d.name(), d.code(), headCount);
    }
}
