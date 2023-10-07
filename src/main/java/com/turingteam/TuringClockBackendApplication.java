package com.turingteam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@PropertySource("classpath:application.yml")
public class TuringClockBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(TuringClockBackendApplication.class, args);
    }

}
