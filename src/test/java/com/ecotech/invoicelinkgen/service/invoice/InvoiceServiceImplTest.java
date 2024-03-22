package com.ecotech.invoicelinkgen.service.invoice;
import com.ecotech.invoicelinkgen.model.Invoice;
import com.ecotech.invoicelinkgen.repository.InvoiceRepository;
import com.ecotech.invoicelinkgen.util.LinkGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.mockito.Mockito.doNothing;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Invoice.class})
public class InvoiceServiceImplTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    @Test
    public void testCreateInvoice() {
        Invoice invoice = new Invoice(/* Set invoice fields as needed */);
        invoice.setTotalAmount(BigDecimal.valueOf(100.0));
        PowerMockito.mockStatic(Invoice.class);
        doNothing().when(Invoice.class); // This line is where the error might be happening
        invoice.generateInvoiceNumber();
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(invoice);
        Invoice createdInvoice = invoiceService.createInvoice(invoice);
        assertNotNull(createdInvoice.getInvoiceNumber());
        verify(invoiceRepository, times(1)).save(invoice);
    }
}
