package com.employee.employeeserverservicerest.service;
 
import java.util.List;
 
import javax.transaction.Transactional;

import com.employee.employeeserverservicerest.model.Employee;
import com.employee.employeeserverservicerest.repository.EmployeeRepository;

import org.hibernate.boot.model.source.internal.hbm.FetchCharacteristicsSingularAssociationImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
 
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
