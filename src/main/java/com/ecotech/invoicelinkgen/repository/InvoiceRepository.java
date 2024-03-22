package com.ecotech.invoicelinkgen.repository;

import com.ecotech.invoicelinkgen.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {
    Optional<Invoice> findInvoiceByInvoiceNumber(String invoiceNumber);
}
