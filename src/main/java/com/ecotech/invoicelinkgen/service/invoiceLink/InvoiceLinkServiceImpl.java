package com.ecotech.invoicelinkgen.service.invoiceLink;

import com.ecotech.invoicelinkgen.exceptions.NotFoundException;
import com.ecotech.invoicelinkgen.model.Invoice;
import com.ecotech.invoicelinkgen.model.InvoiceLink;
import com.ecotech.invoicelinkgen.model.enums.InvoiceLinkAction;
import com.ecotech.invoicelinkgen.model.enums.InvoiceStatus;
import com.ecotech.invoicelinkgen.model.response.InvoiceLinkResponse;
import com.ecotech.invoicelinkgen.repository.InvoiceLinkRepository;
import com.ecotech.invoicelinkgen.util.LinkGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class InvoiceLinkServiceImpl implements InvoiceLinkService {

    private final InvoiceLinkRepository invoiceLinkRepository;
    private final ObjectMapper mapper;
    @Value("${paymentModal}")
    private String paymentModal;

    public InvoiceLinkServiceImpl(InvoiceLinkRepository invoiceLinkRepository, ObjectMapper mapper) {
        this.invoiceLinkRepository = invoiceLinkRepository;
        this.mapper = mapper;
    }

    @Override
    public InvoiceLink generateInvoiceLink(Invoice invoice, String action, LocalDateTime expiryDate) {
        String shortCode = LinkGenerator.generateUniqueShortCode(7);
        InvoiceLink invoiceLink = new InvoiceLink();
        invoiceLink.setInvoice(invoice);
        invoiceLink.setAction(InvoiceLinkAction.valueOf(action));
        invoiceLink.setExpiryDate(expiryDate);
        invoiceLink.setShortCode(shortCode);
        invoiceLink.setInvoiceLinkUrl(paymentModal + "/" + shortCode);
        invoiceLink = invoiceLinkRepository.save(invoiceLink);
        log.info("InvoiceLink for InvoiceNumber {} generated ", invoice.getInvoiceNumber());
        return invoiceLink;
    }

    @Override
    public InvoiceLinkResponse getInvoiceLinkParams(String shortcode) throws JsonProcessingException {
        InvoiceLinkResponse invoiceLinkResponse = new InvoiceLinkResponse();
        Optional<InvoiceLink> invoiceLink = invoiceLinkRepository.getInvoiceLinkByShortCode(shortcode);
        if (invoiceLink.isPresent()) {
            Invoice invoice = invoiceLink.get().getInvoice();
            if (Objects.isNull(invoice) || invoice.getInvoiceStatus().equals(InvoiceStatus.CANCELLED)) {  // Add other cases
                throw new NotFoundException("Invoice is not available for payment");
            }
            // Check due Date if current Date is up to due Date
            // Add features like if link is oneTime use only and has been used
            invoiceLinkResponse = InvoiceLinkResponse.fromInvoiceLinkAndInvoice(invoiceLink.get(), invoice);
            log.info("Invoice link response for {} shortCode", shortcode);
            log.info(mapper.writeValueAsString(invoiceLinkResponse));
            return invoiceLinkResponse;
        }
        throw new NotFoundException("Invoice Link Not Found");
    }

}
