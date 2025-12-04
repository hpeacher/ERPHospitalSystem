package com.hospital.service;

import com.hospital.model.LabOrder;
import com.hospital.model.PatientRecord;
import com.hospital.model.VisitRecord;
import com.hospital.repository.ILabOrderRepository;
import com.hospital.repository.IPatientFileManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class LabOrderService implements ILabOrderService {
    private ILabOrderRepository repository;
    private IPatientFileManager patientFileManager;

    public LabOrderService(ILabOrderRepository repository, IPatientFileManager patientFileManager) {
        this.repository = repository;
        this.patientFileManager = patientFileManager;
    }

    @Override
    public LabOrder createOrder(String patientId, String doctorId, String testName) {
        LabOrder order = new LabOrder(null, patientId, testName, doctorId);
        repository.save(order);
        System.out.println("Lab order created: " + order.getOrderId() + " for test: " + testName);
        return order;
    }

    @Override
    public boolean submitResults(String orderId, String results, String notes) {
        LabOrder order = repository.findById(orderId);
        if (order == null) {
            System.out.println("Lab order not found: " + orderId);
            return false;
        }

        if (!"Pending Lab Collection".equals(order.getStatus())) {
            System.out.println("Cannot submit results. Order status: " + order.getStatus());
            return false;
        }

        order.setResults(results, notes);
        order.setStatus("Results Ready for Review");

        if (validateCriticalResults(results)) {
            order.markCritical();
            System.out.println("WARNING: Critical results detected for order " + orderId);
            System.out.println("URGENT NOTIFICATION sent to Dr. " + order.getOrderingDoctorId());
        }

        repository.update(order);
        System.out.println("Results submitted for order: " + orderId);
        return true;
    }

    @Override
    public boolean approveOrder(String orderId) {
        LabOrder order = repository.findById(orderId);
        if (order == null) {
            System.out.println("Lab order not found: " + orderId);
            return false;
        }

        if (!"Results Ready for Review".equals(order.getStatus())) {
            System.out.println("Cannot approve. Order status: " + order.getStatus());
            return false;
        }

        order.setStatus("Finalized");
        order.setDateCompleted(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        repository.update(order);

        attachToPatientRecord(order);

        System.out.println("Lab order finalized: " + orderId);
        System.out.println("Results attached to patient record: " + order.getPatientId());
        return true;
    }

    @Override
    public LabOrder getOrderById(String orderId) {
        return repository.findById(orderId);
    }

    @Override
    public List<LabOrder> getPendingOrders() {
        return repository.findAllPending().stream()
                .filter(order -> "Pending Lab Collection".equals(order.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<LabOrder> getPendingOrdersForReview() {
        return repository.findAllPending().stream()
                .filter(order -> "Results Ready for Review".equals(order.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean cancelOrder(String orderId) {
        LabOrder order = repository.findById(orderId);
        if (order == null) {
            System.out.println("Lab order not found: " + orderId);
            return false;
        }

        if (order.isFinalized()) {
            System.out.println("Cannot cancel finalized order: " + orderId);
            return false;
        }

        order.setStatus("Canceled");
        repository.update(order);
        System.out.println("Lab order canceled: " + orderId);
        return true;
    }

    @Override
    public boolean validateCriticalResults(String results) {
        if (results == null) return false;

        String lowerResults = results.toLowerCase();
        return lowerResults.contains("critical") ||
               lowerResults.contains("abnormal") ||
               lowerResults.contains("urgent") ||
               lowerResults.contains("high risk") ||
               lowerResults.contains("immediate attention");
    }

    private void attachToPatientRecord(LabOrder order) {
        PatientRecord patientRecord = patientFileManager.getPatientRecord(order.getPatientId());
        if (patientRecord != null && !patientRecord.getVisits().isEmpty()) {
            VisitRecord mostRecentVisit = patientRecord.getMostRecentVisitRecord();
            mostRecentVisit.addLabOrder(order);
            patientFileManager.postPatientRecord(patientRecord);
        }
    }
}
