package com.ecotech.invoicelinkgen.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenerateInvoiceLinkRequest {
    private String invoiceNumber;
    private String action;
    private LocalDateTime expiryDate;
}
