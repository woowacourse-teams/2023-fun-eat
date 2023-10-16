package com.funeat;

import com.funeat.common.repository.BaseRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAsync
@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class)
public class FuneatApplication {

    public static void main(String[] args) {
        SpringApplication.run(FuneatApplication.class, args);
    }
}
