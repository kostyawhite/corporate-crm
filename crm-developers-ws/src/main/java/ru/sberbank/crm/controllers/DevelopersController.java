package ru.sberbank.crm.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/developers")
public class DevelopersController {

    @GetMapping("/status/check")
    public String status() {
        return "Developers Controllers working!!";
    }

}
