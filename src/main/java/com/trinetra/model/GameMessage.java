package com.trinetra.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class GameMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String gamename;

    @Column(name = "gamedescription", length = 2000)
    private String gamedescription;

    private BigDecimal gameprice;

    private String category;

    private String gamegenre;

    private String platform;

    private String gamedeveloper;

    private String publisher;

    private String link;

    @Lob
    @Column(name = "image", columnDefinition = "LONGTEXT")
    private String image; // BASE64 encoded

    @Column(name = "game_image1")
    private String gameImage1; // URL, e.g., image/something.jpg

    @Column(name = "game_image2")
    private String gameImage2; // URL

    @Column(name = "game_image3")
    private String gameImage3; // URL

    @Column(name = "game_image4")
    private String gameImage4; // URL

    // Default constructor
    public GameMessage() {
    }

    // Constructor with parameters
    public GameMessage(String gamename, String gamedescription, BigDecimal gameprice,
                String category, String gamegenre, String platform,
                String gamedeveloper, String publisher, String image,
                String gameImage1, String gameImage2, String gameImage3, String gameImage4) {
        this.gamename = gamename;
        this.gamedescription = gamedescription;
        this.gameprice = gameprice;
        this.category = category;
        this.gamegenre = gamegenre;
        this.platform = platform;
        this.gamedeveloper = gamedeveloper;
        this.publisher = publisher;
        this.image = image;
        this.gameImage1 = gameImage1;
        this.gameImage2 = gameImage2;
        this.gameImage3 = gameImage3;
        this.gameImage4 = gameImage4;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGamename() {
        return gamename;
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
    }

    public String getGamedescription() {
        return gamedescription;
    }

    public void setGamedescription(String gamedescription) {
        this.gamedescription = gamedescription;
    }

    public BigDecimal getGameprice() {
        return gameprice;
    }

    public void setGameprice(BigDecimal gameprice) {
        this.gameprice = gameprice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGamegenre() {
        return gamegenre;
    }

    public void setGamegenre(String gamegenre) {
        this.gamegenre = gamegenre;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getGamedeveloper() {
        return gamedeveloper;
    }

    public void setGamedeveloper(String gamedeveloper) {
        this.gamedeveloper = gamedeveloper;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGameImage1() {
        return gameImage1;
    }

    public void setGameImage1(String gameImage1) {
        this.gameImage1 = gameImage1;
    }

    public String getGameImage2() {
        return gameImage2;
    }

    public void setGameImage2(String gameImage2) {
        this.gameImage2 = gameImage2;
    }

    public String getGameImage3() {
        return gameImage3;
    }

    public void setGameImage3(String gameImage3) {
        this.gameImage3 = gameImage3;
    }

    public String getGameImage4() {
        return gameImage4;
    }

    public void setGameImage4(String gameImage4) {
        this.gameImage4 = gameImage4;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", gamename='" + gamename + '\'' +
                ", gamedescription='" + gamedescription + '\'' +
                ", gameprice=" + gameprice +
                ", category='" + category + '\'' +
                ", gamegenre='" + gamegenre + '\'' +
                ", platform='" + platform + '\'' +
                ", gamedeveloper='" + gamedeveloper + '\'' +
                ", publisher='" + publisher + '\'' +
                ", link='" + link + '\'' +
                ", image='" + (image != null ? "Base64 Image Data" : "No Image") + '\'' +
                ", gameImage1='" + gameImage1 + '\'' +
                ", gameImage2='" + gameImage2 + '\'' +
                ", gameImage3='" + gameImage3 + '\'' +
                ", gameImage4='" + gameImage4 + '\'' +
                '}';
    }
}