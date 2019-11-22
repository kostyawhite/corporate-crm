package ru.sberbank.crm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sberbank.crm.models.Template;
import ru.sberbank.crm.models.TemplateModel;
import ru.sberbank.crm.repositories.TemplateDescriptionRepository;
import ru.sberbank.crm.repositories.TemplateRepository;

import java.util.List;

@RestController
public class TemplateController {

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private TemplateDescriptionRepository templateDescriptionRepository;

    @RequestMapping("/templates")
    public List<TemplateModel> getAllTemplates() {
        return templateRepository.findAll();
    }

    @RequestMapping("/templates/descriptions")
    public List<Template> getAllTemplateDescriptions() {
        return templateDescriptionRepository.findAll();
    }

}
