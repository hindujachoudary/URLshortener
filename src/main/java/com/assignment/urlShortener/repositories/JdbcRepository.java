package com.assignment.urlShortener.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcRepository implements GlobalRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

//    JdbcRepository(){
//        jdbcTemplate.execute("CREATE TABLE Model(shortUrl VARCHAR(50), longURL VARCHAR(500), date DATETIME)");
//    }
    @Override
    public String get(String shortUrl) {
        System.out.println("Inside Get and shorUrl = " + shortUrl);
        String query = "SELECT * FROM Model WHERE short_url='" + shortUrl + "';";
        try {
            return jdbcTemplate.query(query, new ModelMapper()).get(0).getLongUrl();
        }
        catch (Exception e){
            return "Not Found";
        }
    }

    @Override
    public String getByVal(String longUrl) {
        System.out.println("Inside getByVal longUrl = " + longUrl);
        String query = "SELECT * FROM Model WHERE long_url='" + longUrl + "';";
//        System.out.println("====== " + jdbcTemplate.query("SELECT * FROM Model;", new ModelMapper()).toString());
//
//        System.out.println("====== " + jdbcTemplate.query(query, new ModelMapper()).toString());
        try {
            return jdbcTemplate.query(query, new ModelMapper()).get(0).getShortUrl();
        }
        catch (Exception e){
            return "Not Found";
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
    public String getAll() {
        String query = "SELECT * FROM Model;";
        List<String> ans = new ArrayList<>();
        jdbcTemplate.query(query, new ModelMapper()).forEach(x -> ans.add(x.toString()));
        return ans.toString();
    }

    @Override
    public void deleteOld() {
        String query = "DELETE FROM Model WHERE date < DATEADD(day, -30, NOW());";
    }
}
