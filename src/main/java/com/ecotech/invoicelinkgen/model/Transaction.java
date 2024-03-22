package com.ecotech.invoicelinkgen.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private BigDecimal amount;

    @Column(nullable = false, unique = true, name = "reference")
    private String reference;

    @ManyToOne
    @JoinColumn(name = "invoice_link_link_id")
    private InvoiceLink invoiceLink;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "invoice_invoice_id", nullable = false, unique = true)
    private Invoice invoice;

}
