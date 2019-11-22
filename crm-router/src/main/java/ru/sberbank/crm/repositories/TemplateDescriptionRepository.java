package ru.sberbank.crm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sberbank.crm.models.Template;

import java.util.List;

public interface TemplateDescriptionRepository extends JpaRepository<Template, Integer> {

    List<Template> findAll();

}
