package com.ecotech.invoicelinkgen.controller;


import com.ecotech.invoicelinkgen.model.Invoice;
import com.ecotech.invoicelinkgen.service.invoice.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
        Invoice createdInvoice = invoiceService.createInvoice(invoice);
        return new ResponseEntity<>(createdInvoice, HttpStatus.CREATED);
    }

    @GetMapping("/{invoiceNumber}")
    public ResponseEntity<Invoice> getInvoice(@PathVariable String invoiceNumber) {
        Invoice invoice = invoiceService.findInvoiceByInvoiceNumber(invoiceNumber);
        if (invoice != null) {
            return new ResponseEntity<>(invoice, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
