package ru.sberbank.crm.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.sberbank.crm.entity.Department;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentMapper implements RowMapper<Department> {

    @Override
    public Department mapRow(ResultSet resultSet, int i) throws SQLException {
        Department department = new Department();

        department.setId(resultSet.getLong("next_department_id"));
        department.setName(resultSet.getString("name"));
        department.setDescription(resultSet.getString("description"));

        return department;
    }
}
