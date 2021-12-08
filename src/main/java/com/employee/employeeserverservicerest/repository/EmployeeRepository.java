package com.employee.employeeserverservicerest.repository;

import com.employee.employeeserverservicerest.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>, EmployeeRepositoryCustom {
}
