package com.gdss.timecheck.services;

import com.gdss.timecheck.models.Employee;
import com.gdss.timecheck.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository repository;

    public Employee findByPis(String pis) {
        return repository.findByPis(pis);
    }

    public Employee save(Employee employee) {
        return repository.save(employee);
    }
}
