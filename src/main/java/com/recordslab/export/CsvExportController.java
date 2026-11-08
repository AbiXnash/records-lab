package com.recordslab.export;

import com.recordslab.format.CsvCell;
import com.recordslab.service.Directory;
import com.recordslab.view.PublicEmployee;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** Every active employee as CSV, for spreadsheets. */
@RestController
public class CsvExportController {
    private final Directory directory;

    public CsvExportController(Directory directory) {
        this.directory = directory;
    }

    @GetMapping(value = "/export/employees.csv", produces = "text/csv")
    public String export() {
        StringBuilder csv = new StringBuilder(CsvCell.row("id", "givenName", "familyName", "email", "department", "hiredAt"));
        for (PublicEmployee e : directory.everyone()) {
            csv.append(CsvCell.row(Long.toString(e.id()), e.givenName(), e.familyName(), e.email(), e.department(),
                    e.hiredAt()));
        }
        return csv.toString();
    }
}
