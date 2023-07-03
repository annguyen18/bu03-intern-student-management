package com.viettel.intern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = {"com.viettel.intern"})
@EnableCaching
public class InternApplication {

    public static void main(String[] args) {
        SpringApplication.run(InternApplication.class, args);
    }

}
