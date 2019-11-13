package ru.sberbank.crm.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/analytics")
public class AnalyticsController {

    @GetMapping("/status/check")
    public String status() {
        return "Analytics Controller working!!";
    }
}
