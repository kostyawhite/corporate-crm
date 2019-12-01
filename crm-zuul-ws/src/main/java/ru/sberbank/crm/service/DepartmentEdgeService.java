package ru.sberbank.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sberbank.crm.entity.DepartmentEdge;
import ru.sberbank.crm.repository.DepartmentRepository;

import java.util.List;

@Service
public class DepartmentEdgeService {

    @Autowired
    private DepartmentRepository departmentEdgeRepository;

    public void setDepartmentsEdges(List<DepartmentEdge> departmentsEdges) {
        departmentEdgeRepository.saveAll(departmentsEdges);
    }
}
