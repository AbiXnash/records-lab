package com.recordslab.api;

import com.recordslab.query.Paging;
import com.recordslab.security.Access;
import com.recordslab.service.Directory;
import com.recordslab.view.DepartmentView;
import com.recordslab.view.HrEmployee;
import com.recordslab.view.PublicEmployee;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class EmployeeController {
    private final Directory directory;

    public EmployeeController(Directory directory) {
        this.directory = directory;
    }

    @GetMapping("/employees")
    public List<PublicEmployee> list(@RequestParam(required = false) Integer page,
                                     @RequestParam(required = false) Integer size) {
        return directory.page(Paging.of(page, size));
    }

    @GetMapping("/employees/search")
    public List<PublicEmployee> search(@RequestParam String q, @RequestParam(required = false) Integer page,
                                       @RequestParam(required = false) Integer size) {
        return directory.search(q, Paging.of(page, size));
    }

    @GetMapping("/employees/{id}")
    public PublicEmployee one(@PathVariable long id) {
        return directory.one(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/hr/employees/{id}")
    public HrEmployee hr(@PathVariable long id, @RequestHeader(value = Access.HEADER, required = false) String role) {
        Access.requireHr(role);
        return directory.hr(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/departments")
    public List<DepartmentView> departments() {
        return directory.departments();
    }
}
