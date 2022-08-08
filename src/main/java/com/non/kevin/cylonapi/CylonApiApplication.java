package com.non.kevin.cylonapi;

import jdk.jfr.Enabled;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CylonApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CylonApiApplication.class, args);
    }

}
