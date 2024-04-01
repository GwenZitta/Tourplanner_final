package com.example.tourplanner;

import com.example.tourplanner.persistence.repositories.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.example.tourplanner.service"})
public class TourplannerApplication {

    @Autowired
    private TourRepository tourRepository;

    public static void main(String[] args) {
        SpringApplication.run(TourplannerApplication.class, args);
    }

}
