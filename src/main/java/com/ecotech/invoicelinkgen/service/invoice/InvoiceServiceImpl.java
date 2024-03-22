package com.ecotech.invoicelinkgen.service.invoice;

import com.ecotech.invoicelinkgen.model.Invoice;
import com.ecotech.invoicelinkgen.repository.InvoiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Invoice findInvoiceByInvoiceNumber(String invoiceNumber) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findInvoiceByInvoiceNumber(invoiceNumber);
        if (optionalInvoice.isPresent()) {
            return optionalInvoice.get();
        } else {
            log.warn("Invoice with invoiceNumber {} not found.", invoiceNumber);
            return null;
        }
    }

    @Override
    public Invoice createInvoice(Invoice invoice) {
        // Generate invoice number
        invoice.generateInvoiceNumber();
        invoice.setDueAmount(invoice.getTotalAmount());
        return invoiceRepository.save(invoice);
    }
}
