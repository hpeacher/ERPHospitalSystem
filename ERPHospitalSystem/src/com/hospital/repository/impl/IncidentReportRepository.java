package com.hospital.repository.impl;

import com.hospital.model.IncidentReport;
import com.hospital.repository.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IncidentReportRepository {

    private final File storageFile;
    private final Type listType = new TypeToken<List<IncidentReport>>() {}.getType();

    private List<IncidentReport> reports;

    public IncidentReportRepository(String filePath) {
        this.storageFile = new File(filePath);
        this.reports = loadAll();
    }

    private List<IncidentReport> loadAll() {
        if (!storageFile.exists()) {
            return new ArrayList<>();
        }
        return JsonSerializer.readListFromFile(storageFile, listType);
    }

    private void saveAll() {
        JsonSerializer.writeToFile(storageFile, reports);
    }

    public String save(IncidentReport report) {
        if (report.getId() == null || report.getId().isEmpty()) {
            report.setId(UUID.randomUUID().toString());
            reports.add(report);
        } else {
            reports.removeIf(r -> r.getId().equals(report.getId()));
            reports.add(report);
        }
        saveAll();
        return report.getId();
    }

    public IncidentReport findById(String id) {
        return reports.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<IncidentReport> findAll() {
        return new ArrayList<>(reports);
    }
}



