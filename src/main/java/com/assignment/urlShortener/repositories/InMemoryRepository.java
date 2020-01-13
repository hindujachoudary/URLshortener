package com.assignment.urlShortener.repositories;

import org.springframework.stereotype.Component;

import java.util.*;

//@Component
public class InMemoryRepository implements GlobalRepository{
    HashMap<String, String> dictionary = new HashMap<String, String>();
    HashMap<String, String> reverse = new HashMap<String, String>();
    HashMap<String, Date> save = new HashMap<String, Date>();
    TreeMap<Date, String> chronology = new TreeMap<Date, String>();

    @Override
    public String get(String shortUrl){
//        System.out.print("Dictionary contents: ");
//        System.out.println(Collections.singletonList(dictionary));
        if(dictionary.containsKey(shortUrl))
            return dictionary.get(shortUrl);
        return "Not Found";
    }

    @Override
    public String getByVal(String longUrl){
//        System.out.print("Reverse Dictionary contents: ");
//        System.out.println(Collections.singletonList(reverse));
        if(reverse.containsKey(longUrl))
            return reverse.get(longUrl);
        return "Not Found";
    }

    @Override
    public void put(String shortUrl, String longUrl){
        dictionary.put(shortUrl, longUrl);
        reverse.put(longUrl, shortUrl);
        Date cur = new Date();
        chronology.put(cur, shortUrl);
        save.put(shortUrl, cur);
        if(dictionary.size() >= 10000){
            String shortUrlToBeDeleted = chronology.firstEntry().getValue();
            delete(shortUrlToBeDeleted);
        }
    }

    @Override
    public void delete(String shortUrl){
        reverse.remove(dictionary.get(shortUrl));
        chronology.remove(save.get(shortUrl));
        save.remove(shortUrl);
        dictionary.remove(shortUrl);
    }

    @Override
    public String getAll(){
        return Collections.singletonList(chronology).toString();
    }

    @Override
    public void deleteOld(){
        Date now = new Date();
        while(!chronology.isEmpty()){
            Date temp = chronology.firstKey();
            if(temp.getTime() < now.getTime() - ((long) 1000 * 30))
                delete(chronology.firstEntry().getValue());
            else
                break;
        }
    }
}