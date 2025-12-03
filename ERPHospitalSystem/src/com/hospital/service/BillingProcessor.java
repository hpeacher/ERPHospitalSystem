package com.hospital.service;

import com.hospital.model.Invoice;
import com.hospital.repository.IInvoiceFileManager;
import com.hospital.repository.impl.InvoiceFileManager;

public class BillingProcessor {
    private IInvoiceFileManager invoiceFileManager;
    private static int invoiceIndexCounter = 0;

    public BillingProcessor() {
        this.invoiceFileManager = new InvoiceFileManager();

    }

    public void billingProcess(Invoice invoice) {
        if (invoice.getInsurance() == null || !(invoice.getCost() > 0)) {
            System.out.println("Invoice " + invoice.getId() + " did not pass checks and could not be finalized.");
            return;
        }
        invoice.setFinalized();
        invoiceFileManager.postInvoice(invoice);

        System.out.println("Invoice " + invoice.getId() + " has been processed");
    }

    public Invoice generateInvoice(String patientId, String visitId, double amount, String insurance) {
        String newInvoiceId = "I" + getNextInvoiceIndex();
        Invoice invoice = new Invoice(newInvoiceId, amount, patientId, visitId, insurance);
        System.out.println("Invoice for patient: " + patientId + " generated with id "
                + invoice.getId() + " at " + invoice.getCreatedAt());
        return invoice;
    }

    public static synchronized int getNextInvoiceIndex() {
        return ++invoiceIndexCounter;
    }
}
