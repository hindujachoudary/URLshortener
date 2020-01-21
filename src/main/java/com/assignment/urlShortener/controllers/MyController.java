package com.assignment.urlShortener.controllers;

import com.assignment.urlShortener.services.MyService;
import com.assignment.urlShortener.services.MyServiceAsync;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

@RestController
public class MyController {

    @Autowired
    private MyService myService;

    @RequestMapping(value="/create", method=RequestMethod.POST)
    public String createUrl1(@RequestBody MyRequestBody body) {
        System.out.println("Inside Service Create.");
        String temp = "172.16.152.180:8080/short/" + myService.createUrl(body.getLongUrl());
        System.out.println(temp);
        return temp;
    }

    @RequestMapping("/get/{shortUrl}")
    public String getUrl1(@PathVariable("shortUrl") String shortUrl) {
        return myService.getUrl(shortUrl);
    }

    @RequestMapping("/short/{shortUrl}")
    public void redirect(@Valid @PathVariable("shortUrl") String shortUrl, HttpServletResponse httpServletResponse) throws IOException {
        String longUrl = myService.getUrl(shortUrl);
        try{
            httpServletResponse.sendRedirect(longUrl);
        }
        catch (Exception e) {
            httpServletResponse.sendRedirect("172.16.152.180:8080/404");
        }
    }

    @RequestMapping(value="/remove/{shortUrl}", method=RequestMethod.DELETE)
    public void deleteUrl1(@PathVariable("shortUrl") String shortUrl) {
        myService.deleteUrl(shortUrl);
    }

    @RequestMapping("/all")
    public String getAll1() {
        return myService.getAll();
    }


//    @Autowired
//    private MyServiceAsync myServiceAsync;
//
//    @RequestMapping(value="/create", method=RequestMethod.POST)
//    public CompletionStage<String> createUrl(@RequestBody MyRequestBody body) throws ExecutionException, InterruptedException {
//        CompletableFuture<String> temp = (CompletableFuture<String>) myServiceAsync.createUrl(body.getLongUrl());
//        System.out.println("172.16.152.180:8080/" + temp.get());
////        return new CompletableFuture<String>().thenCombine
//                return CompletableFuture.supplyAsync(() -> "172.16.152.180:8080/short/").thenCombine(temp, (a, b) -> a + b);
//    }
//
//    @RequestMapping("/get/{shortUrl}")
//    public CompletionStage<String> getUrl(@PathVariable("shortUrl") String shortUrl) {
//        return myServiceAsync.getUrl(shortUrl);
//    }
//
//    @RequestMapping(value="/remove/{shortUrl}", method=RequestMethod.DELETE)
//    public void deleteUrl(@PathVariable("shortUrl") String shortUrl) {
//        myServiceAsync.deleteUrl(shortUrl);
//    }
//
//    @RequestMapping("/all")
//    public CompletionStage<String> getAll(){
//        return myServiceAsync.getAll();
//    }
//
//    @RequestMapping("/short/{shortUrl}")
//    public void redirect(@Valid @PathVariable("shortUrl") String shortUrl, HttpServletResponse httpServletResponse) throws IOException, ExecutionException, InterruptedException {
//        String longUrl = myServiceAsync.getUrl(shortUrl).get();
//        try{
//            httpServletResponse.sendRedirect(longUrl);
//        }
//        catch (Exception e) {
//            httpServletResponse.sendRedirect("172.16.152.180:8080/404");
//        }
//    }
}