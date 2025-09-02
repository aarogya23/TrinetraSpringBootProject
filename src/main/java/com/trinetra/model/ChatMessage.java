package com.trinetra.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String question;

    @Column(columnDefinition = "TEXT")
    private String answer;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private String model;

    @Column(name = "has_image")
    private Boolean hasImage;

    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    @Column(name = "response_time")
    private Long responseTime;

    // Constructors
    public ChatMessage() {}

    public ChatMessage(String question, String answer, String model, Boolean hasImage, String imageUrl, Long responseTime) {
        this.question = question;
        this.answer = answer;
        this.model = model;
        this.hasImage = hasImage;
        this.imageUrl = imageUrl;
        this.responseTime = responseTime;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public Boolean getHasImage() { return hasImage; }
    public void setHasImage(Boolean hasImage) { this.hasImage = hasImage; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Long getResponseTime() { return responseTime; }
    public void setResponseTime(Long responseTime) { this.responseTime = responseTime; }
}