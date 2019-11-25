package ru.sberbank.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.crm.repository.TemplateRepository;
import ru.sberbank.crm.wrapper.TemplateList;

@Service
public class TemplateService {

    @Autowired
    private TemplateRepository templateRepository;

    public TemplateList getAllTemplates() {
        TemplateList templates = new TemplateList();
        templates.setTemplatesList(templateRepository.findAll());
        return templates;
    }
}
