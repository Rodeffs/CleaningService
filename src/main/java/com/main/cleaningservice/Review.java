package com.main.cleaningservice;

import java.sql.Date;

public class Review {
    private int id;
    private String title;
    private String body;
    private Date publicationDate;
    private Date lastChangeDate;
    private Client client;
    private Cleaning cleaning;
    private int rating;

    public Review(int id, String title, String body, Date publicationDate, Date lastChangeDate, Client client, Cleaning cleaning, int rating) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.publicationDate = publicationDate;
        this.lastChangeDate = lastChangeDate;
        this.client = client;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Date getLastChangeDate() {
        return lastChangeDate;
    }

    public void setLastChangeDate(Date lastChangeDate) {
        this.lastChangeDate = lastChangeDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Cleaning getCleaning() {
        return cleaning;
    }

    public void setCleaning(Cleaning cleaning) {
        this.cleaning = cleaning;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
