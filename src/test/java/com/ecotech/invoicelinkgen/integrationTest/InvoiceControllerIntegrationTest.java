package com.ecotech.invoicelinkgen.integrationTest;

import com.ecotech.invoicelinkgen.model.Invoice;
import com.ecotech.invoicelinkgen.model.InvoiceLink;
import com.ecotech.invoicelinkgen.model.response.InvoiceLinkResponse;
import com.ecotech.invoicelinkgen.service.invoice.InvoiceService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class InvoiceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceService invoiceService;
    private final ObjectMapper objectMapper;

    public InvoiceControllerIntegrationTest(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Test
    public void testGetInvoice() throws Exception {
        Invoice testInvoice = new Invoice(/* set invoice data */);
        String invoiceNumber = testInvoice.getInvoiceNumber();
        when(invoiceService.findInvoiceByInvoiceNumber(invoiceNumber)).thenReturn(testInvoice);
        ResultActions result = mockMvc.perform(get("/api/v1/invoices/{invoiceNumber}", invoiceNumber));
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.invoiceNumber").value(invoiceNumber));
    }



}
