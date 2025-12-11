package com.hospital.service;

import com.hospital.service.IncidentReportService;

public class IncidentReportController {

    private final IncidentReportService service;

    public IncidentReportController(IncidentReportService service) {
        this.service = service;
    }

    public boolean createIncidentReport(String patientId, String reporterId, String description) {
        return service.createIncident(patientId, reporterId, description);
    }
}



