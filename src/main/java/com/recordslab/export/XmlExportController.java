package com.recordslab.export;

import com.recordslab.service.Directory;
import com.recordslab.view.PublicEmployee;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** The payroll provider's import: every active employee as XML, UTF-8. */
@RestController
public class XmlExportController {
    private static final SimpleDateFormat STAMP = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private final Directory directory;

    public XmlExportController(Directory directory) {
        this.directory = directory;
    }

    @GetMapping("/export/employees.xml")
    public void export(HttpServletResponse response) throws IOException, XMLStreamException {
        response.setContentType("application/xml;charset=UTF-8");
        XMLStreamWriter xml = XMLOutputFactory.newFactory().createXMLStreamWriter(response.getOutputStream(), "UTF-8");
        xml.writeStartDocument("UTF-8", "1.0");
        xml.writeStartElement("employees");
        xml.writeAttribute("exportedAt", STAMP.format(new Date()));
        for (PublicEmployee e : directory.everyone()) {
            employee(xml, e);
        }
        xml.writeEndElement();
        xml.writeEndDocument();
        xml.flush();
    }

    private static void employee(XMLStreamWriter xml, PublicEmployee e) throws XMLStreamException {
        xml.writeStartElement("employee");
        xml.writeAttribute("id", Long.toString(e.id()));
        text(xml, "givenName", e.givenName());
        text(xml, "familyName", e.familyName());
        text(xml, "email", e.email());
        text(xml, "department", e.department());
        text(xml, "hiredAt", e.hiredAt());
        xml.writeEndElement();
    }

    private static void text(XMLStreamWriter xml, String name, String value) throws XMLStreamException {
        xml.writeStartElement(name);
        xml.writeCharacters(value == null ? "" : value);
        xml.writeEndElement();
    }
}
