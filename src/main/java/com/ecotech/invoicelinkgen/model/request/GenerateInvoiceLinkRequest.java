package com.ecotech.invoicelinkgen.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class GenerateInvoiceLinkRequest {
    private String invoiceNumber;
    private String action;
    private LocalDateTime expiryDate;
}
