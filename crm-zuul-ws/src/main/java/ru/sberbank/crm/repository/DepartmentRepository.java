package ru.sberbank.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sberbank.crm.entity.Department;

import java.util.List;

public interface DepartmentRepository {
    List<Department> findAllAvailableByIdAndTemplateId(Long id, Long templateId);
}
