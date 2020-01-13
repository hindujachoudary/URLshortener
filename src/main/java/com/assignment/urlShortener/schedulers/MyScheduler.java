package com.assignment.urlShortener.schedulers;

import com.assignment.urlShortener.services.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyScheduler {

    @Autowired
    private MyService myService;

    @Scheduled(cron="0 * * * * *")
    public void remove30DaysOldEntries(){
        System.out.println("Deleted");
        myService.deleteOld();
    }
}
