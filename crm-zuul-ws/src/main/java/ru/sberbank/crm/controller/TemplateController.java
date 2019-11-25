package ru.sberbank.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sberbank.crm.service.TemplateService;
import ru.sberbank.crm.wrapper.TemplateList;

@RestController(value = "/templates")
public class TemplateController {

    @Autowired
    TemplateService templateService;

    @GetMapping(value = "/templates")
    public TemplateList getAllTemplates() {
        return templateService.getAllTemplates();
    }
}
