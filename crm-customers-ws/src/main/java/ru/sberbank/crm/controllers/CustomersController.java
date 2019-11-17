package ru.sberbank.crm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController("/customers")
public class CustomersController {

    private final Environment environment;

    @Autowired
    public CustomersController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping("/status/check")
    public String check() {
        return "Customer Controller working!";
    }

    @GetMapping(path = "/status/services", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<Integer, String> services() {

        Map<Integer, String> services = new HashMap<>();

        services.put(1, "customers-ws");
        services.put(2, "developers-ws");
        services.put(3, "analytics-ws");

        return services;
    }

}
