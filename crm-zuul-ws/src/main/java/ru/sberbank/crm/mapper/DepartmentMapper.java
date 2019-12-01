package ru.sberbank.crm.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.sberbank.crm.entity.Department;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartmentMapper implements RowMapper<Department> {

    @Override
    public Department mapRow(ResultSet resultSet, int i) throws SQLException {
        Department department = new Department();

        department.setId(resultSet.getLong(1));
        department.setName(resultSet.getString(2));
        department.setDescription(resultSet.getString(3));

        return department;
    }
}
