package com.assignment.urlShortener.repositories;

import org.springframework.stereotype.Component;

@Component
public interface GlobalRepository {
    public String get(String shortUrl);
    public String getByVal(String longUrl);
    public void put(String shortUrl, String longUrl);
    public void delete(String shortUrl);
    public String getAll();
    public void deleteOld();
}
