package com.ecotech.invoicelinkgen.service.invoice;

import com.ecotech.invoicelinkgen.model.Invoice;

public interface InvoiceService {
    Invoice findInvoiceByInvoiceNumber(String invoiceNumber);

    Invoice createInvoice(Invoice invoice);
}
