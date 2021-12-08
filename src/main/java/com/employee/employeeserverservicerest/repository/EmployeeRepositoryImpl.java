package com.employee.employeeserverservicerest.repository;

import com.employee.employeeserverservicerest.model.Employee;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom{
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public List<Employee> findEmployeeCustom(
        Float min,
        Float max, 
        Integer offset, 
        Integer limit,
        String sortString
    ) {
        // create cq
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> root = cq.from(Employee.class);
        
        // setup predicates
        List<Predicate> predicatesList = new ArrayList<>();
        predicatesList.add(cb.greaterThanOrEqualTo(root.get("salary"), min));
        predicatesList.add(cb.lessThanOrEqualTo(root.get("salary"), max));

        // set up sort
        if (sortString != null) {
            if (sortString.equals("name")) {
                cq.orderBy(cb.asc(root.get("name")));
            } else if (sortString.equals("salary")) {
                cq.orderBy(cb.asc(root.get("salary")));
            }
        }

        Predicate[] finalPredicates = new Predicate[predicatesList.size()];
        predicatesList.toArray(finalPredicates);
        cq.where(finalPredicates);

        TypedQuery<Employee> tq = em.createQuery(cq).setFirstResult(offset);

        if (limit != null) {
            tq.setMaxResults(limit);
        }

        List<Employee> result = tq.getResultList();

        return result;
    }
    
}
