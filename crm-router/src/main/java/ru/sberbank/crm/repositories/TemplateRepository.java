package ru.sberbank.crm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sberbank.crm.models.TemplateModel;

import java.util.List;

public interface TemplateRepository extends JpaRepository<TemplateModel, Integer> {

    List<TemplateModel> findAll();

}
