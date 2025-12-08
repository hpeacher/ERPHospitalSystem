package com.hospital.repository.impl;

import com.hospital.model.Invoice;
import com.hospital.repository.IInvoiceFileManager;
import com.hospital.repository.JsonSerializer;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class InvoiceFileManager implements IInvoiceFileManager {

    private final String folderName = "invoice_directory";

    public InvoiceFileManager() {

    }

    @Override
    public boolean postInvoice(Invoice invoice) {

        File folder = new File(folderName);

        if (!folder.exists())
            folder.mkdir();

        String fileName = folderName + File.separator + invoice.getId() + ".json";
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(JsonSerializer.toJson(invoice));
            System.out.println("Invoice saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Invoice getInvoice(String invoiceId) {
        File folder = new File(folderName);
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Directory not found.");
            return null;
        }

        File target = new File(folder, invoiceId + ".json");
        if (target.exists()) {
            System.out.println("Found file: " + target.getName());
            return JsonSerializer.readFromFile(target, Invoice.class);
        } else {
            System.out.println("No file found for invoiceId: " + invoiceId);
            return null;
        }
    }

    @Override
    public boolean deleteInvoice(String invoiceId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteInvoice'");
    }

}
