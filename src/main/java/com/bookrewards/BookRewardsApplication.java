package com.bookrewards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BookRewardsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookRewardsApplication.class, args);
    }

} 