package com.assignment.urlShortener.controllers;

public class MyRequestBody {
    private String longUrl;

    public MyRequestBody(){

    }
    public MyRequestBody(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }
}
