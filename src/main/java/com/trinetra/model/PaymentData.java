package com.trinetra.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_data")
public class PaymentData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "khalti_mobile")
    private String khaltiMobile;

    @Column(name = "esewa_id")
    private String esewaId;

    @Column(name = "imepay_mobile")
    private String imepayMobile;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "is_khalti_verified")
    private boolean isKhaltiVerified;

    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getKhaltiMobile() { return khaltiMobile; }
    public void setKhaltiMobile(String khaltiMobile) { this.khaltiMobile = khaltiMobile; }
    public String getEsewaId() { return esewaId; }
    public void setEsewaId(String esewaId) { this.esewaId = esewaId; }
    public String getImepayMobile() { return imepayMobile; }
    public void setImepayMobile(String imepayMobile) { this.imepayMobile = imepayMobile; }
    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }
    public boolean isKhaltiVerified() { return isKhaltiVerified; }
    public void setKhaltiVerified(boolean khaltiVerified) { isKhaltiVerified = khaltiVerified; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}