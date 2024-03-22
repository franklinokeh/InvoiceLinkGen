package com.ecotech.invoicelinkgen.model.response;


import com.ecotech.invoicelinkgen.model.Invoice;
import com.ecotech.invoicelinkgen.model.InvoiceLink;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceLinkResponse {
    private String currency;
    private String invoiceNumber;
    private BigDecimal amount;
    private String shortCode;

// Add remaining necessary fields

    public static InvoiceLinkResponse fromInvoiceLinkAndInvoice(InvoiceLink invoiceLink, Invoice invoice) {

        return InvoiceLinkResponse.builder()
                .amount(invoice.getDueAmount())
                .currency(invoice.getCurrency())
                .invoiceNumber(invoice.getInvoiceNumber())
                .shortCode(invoiceLink.getShortCode())
                .build();
    }
}
