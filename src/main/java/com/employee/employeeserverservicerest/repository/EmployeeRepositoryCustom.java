package com.employee.employeeserverservicerest.repository;

import java.util.List;

import com.employee.employeeserverservicerest.model.Employee;

public interface EmployeeRepositoryCustom {
    List<Employee> findEmployeeCustom(
        Float min,
        Float max, 
        Integer offset, 
        Integer limit,
        String sortString
    );
}

