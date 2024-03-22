package com.ecotech.invoicelinkgen.service.invoiceLink;

import com.ecotech.invoicelinkgen.model.Invoice;
import com.ecotech.invoicelinkgen.model.InvoiceLink;
import com.ecotech.invoicelinkgen.model.enums.InvoiceStatus;
import com.ecotech.invoicelinkgen.model.response.InvoiceLinkResponse;
import com.ecotech.invoicelinkgen.repository.InvoiceLinkRepository;
import com.ecotech.invoicelinkgen.util.LinkGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LinkGenerator.class})
public class InvoiceLinkServiceImplTest {

    @Mock
    private InvoiceLinkRepository invoiceLinkRepository;

    @Mock
    private LinkGenerator linkGenerator;

    @InjectMocks
    private InvoiceLinkServiceImpl invoiceLinkService;

    private ObjectMapper mapper;

    @Test
    public void testGetInvoiceLinkParams_Success() throws JsonProcessingException {
        // Mock data
        String shortcode = "ABC123";
        InvoiceLink invoiceLink = new InvoiceLink();
        invoiceLink.setShortCode(shortcode);
        Invoice invoice = new Invoice();
        invoice.setInvoiceStatus(InvoiceStatus.SENT);
        invoiceLink.setInvoice(invoice);
        when(invoiceLinkRepository.getInvoiceLinkByShortCode(anyString())).thenReturn(Optional.of(invoiceLink));
        InvoiceLinkResponse response = invoiceLinkService.getInvoiceLinkParams(shortcode);
        assertEquals(invoiceLink.getShortCode(), response.getShortCode());
    }

    @Test
    public void testGenerateInvoiceLink_Success() {
        Invoice testInvoice = new Invoice(/* ... set fields */);
        String expectedShortCode = "ABC1234";
        // Mock the static method call
        mockStatic(LinkGenerator.class);
        when(LinkGenerator.generateUniqueShortCode(7)).thenReturn(expectedShortCode);
        // Call the method under test
        InvoiceLink result = invoiceLinkService.generateInvoiceLink(testInvoice, "VIEW", LocalDateTime.now().plusDays(2));
        // Verify the result
        assertEquals(testInvoice, result.getInvoice());
        assertEquals(expectedShortCode, result.getShortCode());
    }
}
