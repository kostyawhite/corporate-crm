package ru.sberbank.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.crm.entity.Template;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {
}
