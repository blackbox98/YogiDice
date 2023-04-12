package com.specialization.yogidice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class YogidiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(YogidiceApplication.class, args);
    }

}
