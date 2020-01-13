package com.assignment.urlShortener.services;
import com.assignment.urlShortener.repositories.GlobalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Random;

@Service
@Transactional
public class MyService {
//    public MyService() {
//        repository = new InMemoryRepository();
//    }
    @Autowired
    public GlobalRepository repository;

    public String convert(int val) {
        char mapper[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        String ans = "";
        while(val != 0){
            int rem = val % 26;
            ans += mapper[rem];
            val /= 26;
        }
        while(ans.length() < 6)
            ans += 'a';
        return ((new StringBuilder(ans)).reverse()).toString();
    }

    public String createUrl(String longUrl) {
        System.out.println("Inside service CreateUrl and longUrl = " + longUrl);
        // Check if the given longUrl already has a mapping.
        String check = repository.getByVal(longUrl);
        if(check != "Not Found")
            return check;
        Random rand = new Random();
        do{
            int temp = rand.nextInt(10000000);
            String shortUrl = convert(temp);

            if(repository.get(shortUrl) == "Not Found"){
                repository.put(shortUrl, longUrl);
                return shortUrl;
            }
        }while(true);
    }

    public String getUrl(String shortUrl){
        System.out.println("Inside service CreateUrl and longUrl = " + shortUrl);
        String longUrl = repository.get(shortUrl);
        return longUrl;
    }

    public void deleteUrl(String shortUrl){
        repository.delete(shortUrl);
    }

    public String getAll(){
        return repository.getAll();
    }

    public void deleteOld(){
        repository.deleteOld();
    }
}