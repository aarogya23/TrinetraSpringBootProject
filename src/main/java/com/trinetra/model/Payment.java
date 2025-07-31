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
    private String esewaId;

    // Khalti
    private String khaltiId;

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
	public String getEsewaId() {
		return esewaId;
	}
	public void setEsewaId(String esewaId) {
		this.esewaId = esewaId;
	}
	public String getKhaltiId() {
		return khaltiId;
	}
	public void setKhaltiId(String khaltiId) {
		this.khaltiId = khaltiId;
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