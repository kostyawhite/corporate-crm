package ru.sberbank.crm.wrapper;

import lombok.Getter;
import lombok.Setter;
import ru.sberbank.crm.entity.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentList {

    @Setter
    @Getter
    private List<Department> departmentsList;

    public DepartmentList() {
        departmentsList = new ArrayList<>();
    }
}
