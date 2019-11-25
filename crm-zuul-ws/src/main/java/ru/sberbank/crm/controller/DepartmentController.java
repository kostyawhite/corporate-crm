package ru.sberbank.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.sberbank.crm.service.DepartmentService;
import ru.sberbank.crm.wrapper.DepartmentList;

@RestController(value = "/departments")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @GetMapping(value = "/departments")
    public DepartmentList getAllTemplates(@RequestParam String id, @RequestParam String templateId) {
        return departmentService.getAvailableDepartments(Long.parseLong(id), Long.parseLong(templateId));
    }
}
