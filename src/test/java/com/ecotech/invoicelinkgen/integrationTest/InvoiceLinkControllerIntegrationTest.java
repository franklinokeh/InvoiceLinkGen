package com.ecotech.invoicelinkgen.integrationTest;

import com.ecotech.invoicelinkgen.model.Invoice;
import com.ecotech.invoicelinkgen.model.InvoiceLink;
import com.ecotech.invoicelinkgen.model.request.GenerateInvoiceLinkRequest;
import com.ecotech.invoicelinkgen.model.response.InvoiceLinkResponse;
import com.ecotech.invoicelinkgen.service.invoice.InvoiceService;
import com.ecotech.invoicelinkgen.service.invoiceLink.InvoiceLinkService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class InvoiceLinkControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceLinkService invoiceLinkService;

    @MockBean
    private InvoiceService invoiceService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGenerateInvoiceLink() throws Exception {
        // Test data
        Invoice testInvoice = new Invoice(/* ... set invoice fields */);
        String invoiceNumber = testInvoice.getInvoiceNumber();
        GenerateInvoiceLinkRequest request = new GenerateInvoiceLinkRequest(invoiceNumber, "VIEW", LocalDateTime.now().plusDays(2));
        InvoiceLink generatedLink = new InvoiceLink(/* ... set link fields */);

        // Mock behavior
        when(invoiceService.findInvoiceByInvoiceNumber(invoiceNumber)).thenReturn(testInvoice);
        when(invoiceLinkService.generateInvoiceLink(any(Invoice.class), anyString(), any(LocalDateTime.class))).thenReturn(generatedLink);

        // Perform mock request
        ResultActions result = mockMvc.perform(post("/api/v1/invoice-links")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // Assertions
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.shortCode").value(generatedLink.getShortCode())); // ... and more
    }


}
