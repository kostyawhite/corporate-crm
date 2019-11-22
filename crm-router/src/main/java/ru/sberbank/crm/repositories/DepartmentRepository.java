package ru.sberbank.crm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sberbank.crm.models.Department;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    List<Department> findAll();
    Department findDepartmentById(Integer id);

}
