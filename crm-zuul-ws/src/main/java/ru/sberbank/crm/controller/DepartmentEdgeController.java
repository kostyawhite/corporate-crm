package ru.sberbank.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.sberbank.crm.service.DepartmentEdgeService;
import ru.sberbank.crm.wrapper.DepartmentEdgeList;

@RestController(value = "/edges")
public class DepartmentEdgeController {

    @Autowired
    DepartmentEdgeService departmentEdgeService;

    @PostMapping(value = "/edges")
    public void setTemplate(@RequestBody DepartmentEdgeList edgeList) {
        departmentEdgeService.setDepartmentsEdges(edgeList.getDepartmentsEdgesList());
    }
}
