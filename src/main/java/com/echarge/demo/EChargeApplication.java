package com.echarge.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@EnableCaching
//@ComponentScan({"controllers", "services", "repository", "payload", "entity", "config"}) // This is what you need
public class EChargeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EChargeApplication.class, args);
    }

}
