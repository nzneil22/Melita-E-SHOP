package com.melita_task.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MelitaEShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(MelitaEShopApplication.class, args);
    }

}
