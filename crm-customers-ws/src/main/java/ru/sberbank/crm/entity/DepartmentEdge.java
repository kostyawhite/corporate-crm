package ru.sberbank.crm.entity;

import lombok.Data;

@Data
public class DepartmentEdge {

    private Long id;
    private Long departmentId;
    private Long nextDepartmentId;
}
