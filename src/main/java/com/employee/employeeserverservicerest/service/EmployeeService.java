package com.employee.employeeserverservicerest.service;
 
import java.util.List;
 
import javax.transaction.Transactional;

import com.employee.employeeserverservicerest.model.Employee;
import com.employee.employeeserverservicerest.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
@Service
@Transactional
public class EmployeeService {
    @Autowired
    private EmployeeRepository repo;

    public static final String NAME = "name";
    public static final String SALARY = "salary";

    public List<Employee> findEmployees(Float min, Float max, Integer offset, Integer limit, String sortString){
        return repo.findEmployeeCustom(min, max, offset, limit, sortString);
    }

}
