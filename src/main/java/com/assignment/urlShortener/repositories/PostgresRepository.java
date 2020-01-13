package com.assignment.urlShortener.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Component
public class PostgresRepository implements GlobalRepository{

    @Autowired
    TestingDB repo ;

    @Override
    public String get(String shortUrl){
        Model temp = new Model("", "Not Found");
        if(repo.existsById(shortUrl))
            return (repo.findById(shortUrl).orElse(temp)).getLongUrl();
        return "Not Found";
    }
    @Override
    public String getByVal(String longUrl){
        Model temp = new Model("Not Found","");
        if(repo.existsByLongUrl(longUrl))
            return (repo.findByLongUrl(longUrl)).getShortUrl();
        return "Not Found";
    }
    @Override
    public void put(String shortUrl, String longUrl){
        Model temp = new Model(shortUrl, longUrl);
        repo.save(temp);
        System.out.println(repo.count());
        if(repo.count() >= 4) {
            temp = repo.findTopByOrderByDate();
            repo.deleteById(temp.getShortUrl());
        }
        System.out.println(repo.count());
    }
    @Override
    public void delete(String shortUrl){
        repo.deleteById(shortUrl);
    }

    @Override
    public String getAll(){
        List<String> ans = new ArrayList<String>();
        List<Model> models = repo.findAll();
        models.forEach(model -> ans.add(model.getLongUrl()));
        return ans.toString();
    }

    @Override
    public void deleteOld(){
        Date now = new Date();
        Date old = new Date(now.getTime() - ((long) 1000 * 60 * 60 * 24 * 30));
        repo.deleteAllByDateLessThan(old);
    }
}
