package com.ecotech.invoicelinkgen.controller;

import com.ecotech.invoicelinkgen.model.Invoice;
import com.ecotech.invoicelinkgen.model.InvoiceLink;
import com.ecotech.invoicelinkgen.model.request.GenerateInvoiceLinkRequest;
import com.ecotech.invoicelinkgen.model.response.InvoiceLinkResponse;
import com.ecotech.invoicelinkgen.service.invoice.InvoiceService;
import com.ecotech.invoicelinkgen.service.invoiceLink.InvoiceLinkService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/invoice-links")
public class InvoiceLinkController {

    private final InvoiceLinkService invoiceLinkService;
    private final InvoiceService invoiceService;

    public InvoiceLinkController(InvoiceLinkService invoiceLinkService, InvoiceService invoiceService) {
        this.invoiceLinkService = invoiceLinkService;
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public ResponseEntity<InvoiceLink> generateInvoiceLink(@RequestBody GenerateInvoiceLinkRequest request) {
        Invoice invoice = invoiceService.findInvoiceByInvoiceNumber(request.getInvoiceNumber());
        if (invoice != null) {
            InvoiceLink invoiceLink = invoiceLinkService.generateInvoiceLink(invoice, request.getAction(), request.getExpiryDate());
            return new ResponseEntity<>(invoiceLink, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("shortcode/{shortcode}")
    public ResponseEntity<InvoiceLinkResponse> getInvoiceLink(@PathVariable String shortcode) throws JsonProcessingException {
        return new ResponseEntity<>(invoiceLinkService.getInvoiceLinkParams(shortcode), HttpStatus.OK);
    }

}
