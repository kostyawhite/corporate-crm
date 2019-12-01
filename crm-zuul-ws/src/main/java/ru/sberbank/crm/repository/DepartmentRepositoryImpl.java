package ru.sberbank.crm.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.sberbank.crm.entity.Department;
import ru.sberbank.crm.entity.DepartmentEdge;
import ru.sberbank.crm.mapper.DepartmentMapper;

import java.util.List;

@Repository
public class DepartmentRepositoryImpl implements DepartmentRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Department> findAllAvailableByIdAndTemplateId(Long id, Long templateId) {
        String sql = "select templates.next_department_id, departments.name, departments.description" +
                " from departments, templates" +
                " where departments.id = templates.next_department_id" +
                " and templates.department_id = " + id +
                " and templates.id = " + templateId + ";";

        return jdbcTemplate.query(sql, new DepartmentMapper());
    }

    @Override
    public List<Department> findAllAvailable() {
        String sql = "select departments.id, departments.name, departments.description" +
                " from departments" +
                " where departments.id > 1;";

        return jdbcTemplate.query(sql, new DepartmentMapper());
    }

    @Override
    public void saveAll(List<DepartmentEdge> departmentEdges) {
        for (DepartmentEdge departmentEdge : departmentEdges) {
            jdbcTemplate.update(
                    "INSERT INTO templates (id, department_id, next_department_id) VALUES (?, ?, ?)",
                    departmentEdge.getId(), departmentEdge.getDepartmentId(), departmentEdge.getNextDepartmentId()
            );
        }
    }


}
