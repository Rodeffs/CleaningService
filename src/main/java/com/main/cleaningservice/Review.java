package com.main.cleaningservice;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Timestamp;

public class Review {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty body = new SimpleStringProperty();
    private final Timestamp publicationTimestamp = new Timestamp(0);
    private final Timestamp lastChangeTimestamp = new Timestamp(0);
    private final Account account = new Account();
    private final Cleaning cleaning = new Cleaning();
    private final IntegerProperty rating = new SimpleIntegerProperty();

    public Review() {
        this.id.set(-1);
        this.title.set("");
        this.body.set("");
        this.rating.set(0);
    }

    public Review(int id, String title, String body, Timestamp publicationTimestamp, Timestamp lastChangeTimestamp, int rating, Account account, Cleaning cleaning) {
        this.id.set(id);
        this.title.set(title);
        this.publicationTimestamp.setTime(publicationTimestamp.getTime());
        this.body.set(body);
        this.lastChangeTimestamp.setTime(lastChangeTimestamp.getTime());
        this.account.set(account);
        this.cleaning.set(cleaning);
        this.rating.set(rating);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public Timestamp getPublicationTimestamp() {
        return publicationTimestamp;
    }

    public void setPublicationTimestamp(Timestamp publicationTimestamp) {
        this.publicationTimestamp.setTime(publicationTimestamp.getTime());
    }

    public String getBody() {
        return body.get();
    }

    public void setBody(String body) {
        this.body.set(body);
    }

    public Timestamp getLastChangeTimestamp() {
        return lastChangeTimestamp;
    }

    public void setLastChangeTimestamp(Timestamp lastChangeTimestamp) {
        this.lastChangeTimestamp.setTime(lastChangeTimestamp.getTime());
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account.set(account);
    }

    public int getRating() {
        return rating.get();
    }

    public void setRating(int rating) {
        this.rating.set(rating);
    }

    public Cleaning getCleaning() {
        return cleaning;
    }

    public void setCleaning(Cleaning cleaning) {
        this.cleaning.set(cleaning);
    }

    public void set(Review otherReview) {
        this.id.set(otherReview.getId());
        this.title.set(otherReview.getTitle());
        this.body.set(otherReview.getBody());
        this.publicationTimestamp.setTime(otherReview.getPublicationTimestamp().getTime());
        this.lastChangeTimestamp.setTime(otherReview.getLastChangeTimestamp().getTime());
        this.account.set(otherReview.getAccount());
        this.cleaning.set(otherReview.getCleaning());
        this.rating.set(otherReview.getRating());
    }

    public void clear() {
        this.id.set(-1);
        this.title.set("");
        this.body.set("");
        this.publicationTimestamp.setTime(0);
        this.lastChangeTimestamp.setTime(0);
        this.account.clear();
        this.cleaning.clear();
        this.rating.set(0);
    }
}
