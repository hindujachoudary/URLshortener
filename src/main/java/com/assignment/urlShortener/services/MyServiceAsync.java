package com.assignment.urlShortener.services;
import com.assignment.urlShortener.repositoriesAsync.GlobalRepositoryAsync;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

@Service
@Transactional
public class MyServiceAsync {
//    public MyService() {
//        repository = new InMemoryRepository();
//    }
    @Autowired
    public GlobalRepositoryAsync repository;

//    @Async
    public CompletableFuture<String> convert(int val) {
        char mapper[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        String ans = "";
        while(val != 0){
            int rem = val % 26;
            ans += mapper[rem];
            val /= 26;
        }
        while(ans.length() < 6)
            ans += 'a';
        return CompletableFuture.completedFuture((new StringBuilder(ans)).reverse().toString());
    }

//    @Async
    public CompletionStage<String> createUrl(String longUrl) throws ExecutionException, InterruptedException {
        System.out.println("Inside service CreateUrl and longUrl = " + longUrl);
        // Check if the given longUrl already has a mapping.
        CompletableFuture<String> check = repository.getByVal(longUrl);
        if(check.get() != "Not Found")
            return check;
        Random rand = new Random();
        do{
            int temp = rand.nextInt(10000000);
            CompletableFuture<String> shortUrl = convert(temp);

            if((repository.get(shortUrl.get())).get() == "Not Found"){
                repository.put(shortUrl.get(), longUrl);
                return shortUrl;
            }
        }while(true);
    }

    public CompletionStage<String> getUrl(String shortUrl){
        System.out.println("Inside service CreateUrl and longUrl = " + shortUrl);
        return repository.get(shortUrl);
    }

    public void deleteUrl(String shortUrl){
        repository.delete(shortUrl);
    }

    public CompletionStage<String> getAll(){
        return repository.getAll();
    }

    public void deleteOld(){
        repository.deleteOld();
    }
}