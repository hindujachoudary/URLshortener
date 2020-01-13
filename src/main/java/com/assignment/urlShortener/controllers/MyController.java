package com.assignment.urlShortener.controllers;

import com.assignment.urlShortener.services.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

@RestController
public class MyController {

    @Autowired
    private MyService myService;

    @RequestMapping(value="/create", method=RequestMethod.POST)
    public String createUrl(@RequestBody MyRequestBody body) {
        String temp = "shortener.com/" + myService.createUrl(body.getLongUrl());
        System.out.println(temp);
        return temp;
    }

    @RequestMapping("/get/{shortUrl}")
    public String getUrl(@PathVariable("shortUrl") String shortUrl) {
        return myService.getUrl(shortUrl);
    }

    @RequestMapping(value="/remove/{shortUrl}", method=RequestMethod.DELETE)
    public void deleteUrl(@PathVariable("shortUrl") String shortUrl) {
        myService.deleteUrl(shortUrl);
    }

    @RequestMapping("/all")
    public String getAll(){
        return myService.getAll();
    }

}