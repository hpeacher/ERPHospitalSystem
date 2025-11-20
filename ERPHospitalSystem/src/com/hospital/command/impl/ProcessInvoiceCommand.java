package com.hospital.command.impl;

import java.util.Scanner;
import com.hospital.command.ICommand;
import com.hospital.model.*;
import com.hospital.repository.IInvoiceFileManager;
import com.hospital.service.BillingProcessor;

public class ProcessInvoiceCommand implements ICommand {
    private Scanner scanner;
    private BillingProcessor billingProcessor;
    private IInvoiceFileManager invoiceFileManager;

    public ProcessInvoiceCommand(BillingProcessor billingProcessor,
            Scanner scanner,
            IInvoiceFileManager invoiceFileManager) {
        this.billingProcessor = billingProcessor;
        this.scanner = scanner;
        this.invoiceFileManager = invoiceFileManager;
    }

    @Override
    public String getToken() {
        return "process_invoice";
    }

    @Override
    public void execute() {
        // TODO
        System.out.println("Enter invoice id to process: ");
        String invoiceId = scanner.nextLine();

        Invoice invoice = invoiceFileManager.getInvoice(invoiceId);

        if (invoice == null) {
            System.out.println("Invoice could not be found. Returning to main page. \n");
            return;
        } else if (invoice.isFinalized()) {
            System.out.println("That invoice has already by finalized");
        }

        System.out.println("---------------------------------");
        System.out.println("Invoice " + invoice.getId() + ":");
        System.out.println("Created at: " + invoice.getCreatedAt() +
                "\nInsurance: " + invoice.getInsurance() +
                "\nCost: " + invoice.getCost());

        System.out.println("Would you like to approve this invoice? y/n");
        String c = scanner.next();
        if (c.equalsIgnoreCase("y")) {
            billingProcessor.billingProcess(invoice);
        } else {
            System.out.println("Invoice not approved.");
            return;
        }

    }

}
