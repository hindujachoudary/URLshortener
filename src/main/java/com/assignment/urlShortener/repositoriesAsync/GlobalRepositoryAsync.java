package com.assignment.urlShortener.repositoriesAsync;

import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public interface GlobalRepositoryAsync {
    public CompletableFuture<String> get(String shortUrl);
    public CompletableFuture<String> getByVal(String longUrl);
    public void put(String shortUrl, String longUrl);
    public void delete(String shortUrl);
    public CompletableFuture<String> getAll();
    public void deleteOld();
}
