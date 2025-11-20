package com.hospital.repository;

import com.hospital.model.Invoice;

public interface IInvoiceFileManager {
    boolean postInvoice(Invoice invoice);

    Invoice getInvoice(String invoiceId);

    boolean deleteInvoice(String invoiceId);
}
