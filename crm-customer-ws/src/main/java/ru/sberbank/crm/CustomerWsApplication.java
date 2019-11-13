package ru.sberbank.crm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CustomerWsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerWsApplication.class, args);
    }

}
