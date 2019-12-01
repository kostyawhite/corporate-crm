package ru.sberbank.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.crm.repository.DepartmentRepository;
import ru.sberbank.crm.wrapper.DepartmentList;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public DepartmentList getAvailableDepartmentsByTemplate(Long id, Long templateId) {
        DepartmentList departments = new DepartmentList();
        departments.setDepartmentsList(departmentRepository.findAllAvailableByIdAndTemplateId(id, templateId));
        return departments;
    }

    public DepartmentList getAvailableDepartments() {
        DepartmentList departments = new DepartmentList();
        departments.setDepartmentsList(departmentRepository.findAllAvailable());
        return departments;
    }
}
