package com.assignment.urlShortener.repositoriesAsync;

import com.assignment.urlShortener.repositories.GlobalRepository;
import com.assignment.urlShortener.repositories.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.relational.core.sql.SQL;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Component
public class JdbcRepositoryAsync implements GlobalRepositoryAsync {

    @Autowired
    JdbcTemplate jdbcTemplate;

//    JdbcRepository(){
//        jdbcTemplate.execute("CREATE TABLE Model(shortUrl VARCHAR(50), longURL VARCHAR(500), date DATETIME)");
//    }
    @Override
    public CompletableFuture<String> get(String shortUrl) {
        CompletableFuture<String> future = new CompletableFuture<>();
        System.out.println("Inside Get and shorUrl = " + shortUrl);
        String query = "SELECT * FROM Model WHERE short_url='" + shortUrl + "';";
        try {
            future.complete(jdbcTemplate.query(query, new ModelMapper()).get(0).getLongUrl());
            return future;
        }
        catch (IndexOutOfBoundsException e){
            future.complete("Not Found");
            return future;
        }
        catch (Exception e){
            future.completeExceptionally(new Throwable("Async issue"));
            return future;
        }
    }

    @Override
    public CompletableFuture<String> getByVal(String longUrl) {
        CompletableFuture<String> future = new CompletableFuture<>();
        System.out.println("Inside getByVal longUrl = " + longUrl);
        String query = "SELECT * FROM Model WHERE long_url='" + longUrl + "';";
//        System.out.println("====== " + jdbcTemplate.query("SELECT * FROM Model;", new ModelMapper()).toString());
//
//        System.out.println("====== " + jdbcTemplate.query(query, new ModelMapper()).toString());
        try {
            future.complete(jdbcTemplate.query(query, new ModelMapper()).get(0).getShortUrl());
            return future;
        }
        catch (IndexOutOfBoundsException e){
            future.complete("Not Found");
            return future;
        }
        catch (Exception e){
            future.completeExceptionally(new Throwable("Async issue"));
            return future;
        }
    }

    @Override
    public void put(String shortUrl, String longUrl) {
        System.out.println("Inside put and shorUrl = '" + shortUrl + "' AND longUrl = '" + longUrl);
        String query = "INSERT INTO Model(short_url, long_url, date) VALUES('" + shortUrl + "' , '" + longUrl + "', current_timestamp);";
        jdbcTemplate.update(query);
    }

    @Override
    public void delete(String shortUrl) {
        String query = "DELETE FROM Model WHERE short_url = '" + shortUrl + "';";
        jdbcTemplate.update(query);
    }

    @Override
    public CompletableFuture<String> getAll() {
        CompletableFuture<String> future = new CompletableFuture<>();
        String query = "SELECT * FROM Model;";
        List<String> ans = new ArrayList<>();
        jdbcTemplate.query(query, new ModelMapper()).forEach(x -> ans.add(x.toString()));
        future.complete(ans.toString());
        return future;
    }

    @Override
    public void deleteOld() {
        String query = "DELETE FROM Model WHERE date < DATEADD(day, -30, NOW());";
    }
}
