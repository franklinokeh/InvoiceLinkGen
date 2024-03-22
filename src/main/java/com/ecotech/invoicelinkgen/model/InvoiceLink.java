package com.ecotech.invoicelinkgen.model;

import com.ecotech.invoicelinkgen.model.enums.InvoiceLinkAction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long linkId;
    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    @JsonIgnore
    private Invoice invoice;

    private String shortCode;

    private InvoiceLinkAction action; // "view" or "pay"
    private String invoiceLinkUrl;

    private LocalDateTime expiryDate;

    @OneToMany(mappedBy = "invoiceLink", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();

    @Column(name = "createdAt", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updatedAt", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
