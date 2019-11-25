package ru.sberbank.crm.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.sberbank.crm.entity.Department;
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
}
