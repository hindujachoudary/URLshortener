package com.assignment.urlShortener.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public interface TestingDB extends JpaRepository<Model, String>{
    public boolean existsByLongUrl(String longUrl);
    public Model findByLongUrl(String longUrl);
    public Model findTopByOrderByDate();
    public void deleteAllByDateLessThan(Date old);
}
