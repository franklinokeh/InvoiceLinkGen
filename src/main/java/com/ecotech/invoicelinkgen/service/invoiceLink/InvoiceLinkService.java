package com.ecotech.invoicelinkgen.service.invoiceLink;

import com.ecotech.invoicelinkgen.model.Invoice;
import com.ecotech.invoicelinkgen.model.InvoiceLink;
import com.ecotech.invoicelinkgen.model.response.InvoiceLinkResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.time.LocalDateTime;

public interface InvoiceLinkService {
    InvoiceLink generateInvoiceLink(Invoice invoice, String action, LocalDateTime expiryDate);

    InvoiceLinkResponse getInvoiceLinkParams(String shortcode) throws JsonProcessingException;
}
