package com.nordea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({
        "com.nordea.logging",
        "com.nordea.service",
        "com.nordea.repository"
})
public class TietoNordeaApplication {

    public static void main(String[] args) {
        SpringApplication.run(TietoNordeaApplication.class, args);
    }

}
