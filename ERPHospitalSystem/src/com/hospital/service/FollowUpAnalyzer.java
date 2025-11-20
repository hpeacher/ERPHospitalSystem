package com.hospital.service;

import com.hospital.model.VisitRecord;

public class FollowUpAnalyzer {

    public String analyze(VisitRecord visit) {

        if (visit == null) {
            return "No visit data available.";
        }

        // Check required fields
        if (visit.getVitals() == null || visit.getVitals().isBlank() ||
            visit.getNotes() == null || visit.getNotes().isBlank()) {

            return "INSUFFICIENT_DATA";
        }

        // Simple analysis rules (you can modify)
        if (visit.getVitals().contains("BP:140") ||
            visit.getNotes().toLowerCase().contains("hypertension")) {

            return "Recommend follow-up in 1 week to monitor hypertension.";
        }

        if (visit.getNotes().toLowerCase().contains("pain")) {
            return "Recommend follow-up within 3 days to reassess pain levels.";
        }

        return "Recommend routine follow-up in 2 weeks.";
    }
}
