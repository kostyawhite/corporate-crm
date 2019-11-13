package ru.sberbank.crm.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/customers")
public class CustomersController {

    @GetMapping("/status/check")
    public String status() {
        return "Customer Controller working!";
    }

}
