package com.hospital.service;

import com.hospital.model.Invoice;
import com.hospital.model.PatientRecord;
import com.hospital.model.VisitRecord;
import com.hospital.repository.IPatientFileManager;
import com.hospital.repository.impl.PatientFileManager;

interface IBillingProcessor {
    PatientRecord startBillingProcess(VisitRecord visitRecord);
}

public class BillingProcessor implements IBillingProcessor {
    private IPatientFileManager patientFileManager;

    public BillingProcessor() {
        this.patientFileManager = new PatientFileManager();
    }

    public PatientRecord startBillingProcess(VisitRecord visitRecord) {
        // Implementation for starting the billing process
        PatientRecord patientRecord = patientFileManager.getPatientRecord(visitRecord.getPatientId());
        Invoice invoice = generateInvoice(visitRecord.getPatientId(), 1000.0, patientRecord.getInsurance());
        boolean paymentConfirmed = confirmPayment(invoice);
        boolean insuranceConfirmed = confirmInsuranceCoverage(invoice);
        if (paymentConfirmed && insuranceConfirmed) {
            System.out.println("Billing process completed for Patient " + visitRecord.getPatientId() + ".");
            visitRecord.setInvoice(invoice);
            patientRecord.addVisit(visitRecord);
            return patientRecord;
        } else {
            System.out.println("Billing process failed for Patient " + visitRecord.getPatientId() + ".");
        }
        return null;
    }

    private Invoice generateInvoice(String patientId, double amount, String insurance) {
        // Implementation for generating an invoice
        System.out.println("Generating invoice for Patient " + patientId + " with amount " + amount + " and insurance "
                + insurance + "...");
        return new Invoice(0, amount, patientId, insurance);
    }

    private boolean confirmPayment(Invoice invoice) {
        // Implementation for confirming payment
        if (invoice.isFinalized()) {
            System.out.println("Invoice " + invoice.getId() + " is already finalized.");
            return true;
        } else {
            invoice.setFinalized(true);
            System.out.println("Invoice " + invoice.getId() + " has been finalized.");
            return true;
        }
    }

    private boolean confirmInsuranceCoverage(Invoice invoice) {
        // Implementation for confirming insurance coverage
        System.out.println(
                "Checking insurance coverage for Invoice " + invoice.getId() + " " + invoice.getInsurance() + "...");
        if (invoice.getInsurance() != null) {
            System.out.println("Insurance coverage confirmed for Invoice " + invoice.getId() + ".");
            return true;
        } else {
            System.out.println("No insurance coverage for Invoice " + invoice.getId() + ".");
            return false;
        }
    }
}
