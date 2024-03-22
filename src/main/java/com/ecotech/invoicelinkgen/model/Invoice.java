package com.ecotech.invoicelinkgen.model;

import com.ecotech.invoicelinkgen.model.enums.InvoiceStatus;
import com.ecotech.invoicelinkgen.util.InvoiceNumberGenerator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invoiceNumber", nullable = false, unique = true)
    private String invoiceNumber;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<InvoiceLink> links = new HashSet<>();
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private BigDecimal dueAmount = BigDecimal.ZERO;
    private BigDecimal paidAmount  = BigDecimal.ZERO;

    private LocalDateTime dueDate;
    private String currency;

    private String billingEntityName;
    private String billingEntityPhoneNumber;
    private String billingEntityEmail;
    private String billingEntityAddress;
    private String businessId;
    /*
    private String taxInformation;
    private String itemizedList;
    private BigDecimal discountAmount;
    private String discountDescription;
    private String shippingInformation;
    private String paymentMethod;
    private String transactionId;
    private LocalDateTime paymentDate;
    private String notes;
*/
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();

    @Column(name = "invoiceStatus")
    private InvoiceStatus invoiceStatus = InvoiceStatus.DRAFT;

    @Column(name = "createdAt", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updatedAt", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public void generateInvoiceNumber() {
        this.invoiceNumber = InvoiceNumberGenerator.generateInvoiceNumber();
    }
}
