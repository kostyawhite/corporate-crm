package ru.sberbank.crm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AnalyticsWsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnalyticsWsApplication.class, args);
    }

}
