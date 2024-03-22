package com.ecotech.invoicelinkgen.repository;

import com.ecotech.invoicelinkgen.model.InvoiceLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvoiceLinkRepository extends JpaRepository<InvoiceLink, Long> {
    Optional<InvoiceLink> getInvoiceLinkByShortCode(String shortcode);
}
