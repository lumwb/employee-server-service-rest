package com.employee.employeeserverservicerest.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.employee.employeeserverservicerest.helper.CSVHelper;
import com.employee.employeeserverservicerest.model.Employee;
import com.employee.employeeserverservicerest.repository.EmployeeRepository;

@Service
public class CSVService {
    @Autowired
    EmployeeRepository repository;

        public void save(MultipartFile file) {
            try {
                List<Employee> employees = CSVHelper.csvToEmployees(file.getInputStream());
                repository.saveAll(employees);
            } catch (IOException e) {
                throw new RuntimeException("fail to store csv data: " + e.getMessage());
            }
    }
}
