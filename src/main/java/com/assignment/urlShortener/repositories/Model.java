package com.assignment.urlShortener.repositories;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Model {
    @Id
    private String shortUrl;
    private String longUrl;
    private Date date;
    public Model(){
        this.shortUrl = "short.com";
        this.longUrl = "longestUrl.com";
        this.date = new Date();
    }
    public Model(String shortUrl, String longUrl) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
        this.date = new Date();
    }

    public Model(String shortUrl, String longUrl, Date date){
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
        this.date = date;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }
    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
