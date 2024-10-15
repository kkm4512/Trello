package com.example.trello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TrelloApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrelloApplication.class, args);
    }

}
