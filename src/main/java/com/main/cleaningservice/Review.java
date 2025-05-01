package com.main.cleaningservice;

import java.sql.Timestamp;

public class Review {
    private int id;
    private String title;
    private String body;
    private Timestamp publicationTimestamp;
    private Timestamp lastChangeTimestamp;
    private Account account;
    private Cleaning cleaning;
    private int rating;

    public Review(int id, String title, String body, Timestamp publicationTimestamp, Timestamp lastChangeTimestamp, int rating, Account account, Cleaning cleaning) {
        this.id = id;
        this.title = title;
        this.publicationTimestamp = publicationTimestamp;
        this.body = body;
        this.lastChangeTimestamp = lastChangeTimestamp;
        this.account = account;
        this.cleaning = cleaning;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getPublicationTimestamp() {
        return publicationTimestamp;
    }

    public void setPublicationTimestamp(Timestamp publicationTimestamp) {
        this.publicationTimestamp = publicationTimestamp;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Timestamp getLastChangeTimestamp() {
        return lastChangeTimestamp;
    }

    public void setLastChangeTimestamp(Timestamp lastChangeTimestamp) {
        this.lastChangeTimestamp = lastChangeTimestamp;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Cleaning getCleaning() {
        return cleaning;
    }

    public void setCleaning(Cleaning cleaning) {
        this.cleaning = cleaning;
    }
}
