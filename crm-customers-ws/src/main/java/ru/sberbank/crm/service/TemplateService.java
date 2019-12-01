package ru.sberbank.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.crm.entity.Template;
import ru.sberbank.crm.exception.TemplateNotFound;
import ru.sberbank.crm.repository.TemplateRepository;

import java.util.List;

@Service
public class TemplateService {

    @Autowired
    private TemplateRepository repository;

    public Template getTemplateById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TemplateNotFound("Шаблон не найден"));
    }

    public List<Template> getAllTemplates() {
        return repository.findAll();
    }

    public void saveTemplate(Template template) {
        repository.save(template);
    }
}
