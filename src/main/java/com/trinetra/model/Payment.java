package com.trinetra.model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    private String fullname;
    private String username;
    private String email;

    // Payment Method Type (netbanking, esewa, khalti, mobilebanking)
    private String paymentMethod;

    // Net Banking
    private String bank;

    // eSewa
    private String esewa_id;

    // Khalti
    private String khalti_id;

    // Mobile Banking
    private String mobileBank;
    private String mobileAccount;
    private String gameNames;
    private String paymentAmount;
	public Long getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getEsewa_id() {
		return esewa_id;
	}
	public void setEsewa_id(String esewa_id) {
		this.esewa_id = esewa_id;
	}
	public String getKhalti_id() {
		return khalti_id;
	}
	public void setKhalti_id(String khalti_id) {
		this.khalti_id = khalti_id;
	}
	public String getMobileBank() {
		return mobileBank;
	}
	public void setMobileBank(String mobileBank) {
		this.mobileBank = mobileBank;
	}
	public String getMobileAccount() {
		return mobileAccount;
	}
	public void setMobileAccount(String mobileAccount) {
		this.mobileAccount = mobileAccount;
	}
	public String getGameNames() {
		return gameNames;
	}
	public void setGameNames(String gameNames) {
		this.gameNames = gameNames;
	}
	public String getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
    

}