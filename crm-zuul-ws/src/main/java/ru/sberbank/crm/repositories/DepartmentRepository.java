package ru.sberbank.crm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sberbank.crm.models.DepartmentModel;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<DepartmentModel, Integer> {

    List<DepartmentModel> findAll();
    DepartmentModel findDepartmentById(Integer id);

}
