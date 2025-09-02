package com.trinetra.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private int gameId;
    private String gameName;
    private BigDecimal gamePrice;
    private String paymentMethod;
    private String paymentDetails;
    private LocalDateTime purchaseDate;
    @Lob
    @Column(name = "image", columnDefinition = "LONGTEXT")
    private String image; // Base64 string
    private String status; // e.g., Ready to Play, Downloading, Updating
    private String secretCode; // Activation code
    private String lastPlayed; // Last played date
    private String playTime; // Total play time

    public Purchase() {}

    public Purchase(Long userId, int gameId, String gameName, BigDecimal gamePrice,
                    String paymentMethod, String paymentDetails, LocalDateTime purchaseDate,
                    String image, String status, String secretCode, String lastPlayed, String playTime) {
        this.userId = userId;
        this.gameId = gameId;
        this.gameName = gameName;
        this.gamePrice = gamePrice;
        this.paymentMethod = paymentMethod;
        this.paymentDetails = paymentDetails;
        this.purchaseDate = purchaseDate;
        this.image = image;
        this.status = status;
        this.secretCode = secretCode;
        this.lastPlayed = lastPlayed;
        this.playTime = playTime;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public int getGameId() { return gameId; }
    public void setGameId(int gameId) { this.gameId = gameId; }
    public String getGameName() { return gameName; }
    public void setGameName(String gameName) { this.gameName = gameName; }
    public BigDecimal getGamePrice() { return gamePrice; }
    public void setGamePrice(BigDecimal gamePrice) { this.gamePrice = gamePrice; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getPaymentDetails() { return paymentDetails; }
    public void setPaymentDetails(String paymentDetails) { this.paymentDetails = paymentDetails; }
    public LocalDateTime getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(LocalDateTime purchaseDate) { this.purchaseDate = purchaseDate; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getSecretCode() { return secretCode; }
    public void setSecretCode(String secretCode) { this.secretCode = secretCode; }
    public String getLastPlayed() { return lastPlayed; }
    public void setLastPlayed(String lastPlayed) { this.lastPlayed = lastPlayed; }
    public String getPlayTime() { return playTime; }
    public void setPlayTime(String playTime) { this.playTime = playTime; }
}