package com.funeat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class FuneatApplication {

    public static void main(String[] args) {
        SpringApplication.run(FuneatApplication.class, args);
    }
}
