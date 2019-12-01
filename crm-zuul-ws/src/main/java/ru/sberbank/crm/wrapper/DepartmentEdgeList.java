package ru.sberbank.crm.wrapper;

import lombok.Getter;
import lombok.Setter;
import ru.sberbank.crm.entity.DepartmentEdge;

import java.util.ArrayList;
import java.util.List;

public class DepartmentEdgeList {

    @Setter
    @Getter
    private List<DepartmentEdge> departmentsEdgesList;

    public DepartmentEdgeList() {
        departmentsEdgesList = new ArrayList<>();
    }
}
