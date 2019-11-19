package ru.sberbank.crm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sberbank.crm.models.DepartmentModel;
import ru.sberbank.crm.repositories.DepartmentRepository;

import java.util.List;

@RestController
public class DepartmentController {

    @Autowired
    DepartmentRepository departmentRepository;

    @RequestMapping("/departments")
    public List<DepartmentModel> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @RequestMapping("/departments/{id}")
    public DepartmentModel getDepartmentById(@PathVariable Integer id) {
        return departmentRepository.findDepartmentById(id);
    }

}