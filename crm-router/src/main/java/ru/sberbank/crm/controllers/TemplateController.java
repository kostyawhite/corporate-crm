package ru.sberbank.crm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sberbank.crm.models.TemplateModel;
import ru.sberbank.crm.repositories.TemplateRepository;

import java.util.List;

@RestController
public class TemplateController {

    @Autowired
    private TemplateRepository templateRepository;

    @RequestMapping("/templates")
    public List<TemplateModel> getAllTemplates() {
        return templateRepository.findAll();
    }

}
