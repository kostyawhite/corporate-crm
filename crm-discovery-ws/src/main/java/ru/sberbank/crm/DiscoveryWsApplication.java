package ru.sberbank.crm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryWsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryWsApplication.class, args);
    }

}
