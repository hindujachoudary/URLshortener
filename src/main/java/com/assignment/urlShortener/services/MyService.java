package com.assignment.urlShortener.services;
import com.assignment.urlShortener.repositories.GlobalRepository;
import com.assignment.urlShortener.repositories.InMemoryRepository;
import com.assignment.urlShortener.repositories.JdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Random;

@Service
@Transactional
public class MyService {

//    public MyService() {
//        repository = new JdbcRepository();
//    }

    @Autowired
    public GlobalRepository repository;

//    public MyService(@Qualifier("globalRepository") GlobalRepository repo) {
//        this.repository = repo;
//    }

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
        System.out.println("Check Status: " + check);
        if(check != "Not Found")
            return check;
        Random rand = new Random();
        do{
            int temp = rand.nextInt(10000000);
            String shortUrl = convert(temp);
            System.out.println("Randomly created: " + shortUrl);
            if(repository.get(shortUrl) == "Not Found"){
                repository.put(shortUrl, longUrl);
                return shortUrl;
            }
        }while(true);
    }

    public String getUrl(String shortUrl){
//        System.out.println("Inside service CreateUrl and longUrl = " + shortUrl);
        return repository.get(shortUrl);
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