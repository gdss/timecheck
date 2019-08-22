package com.gdss.timecheck.controllers;

import com.gdss.timecheck.models.Employee;
import com.gdss.timecheck.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "api/employees")
public class EmployeeController {

    @Autowired
    EmployeeService service;

    @GetMapping(params = "pis")
    public ResponseEntity<Employee> findByPis(@RequestParam String pis) {
        Employee employee = service.findByPis(pis);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Employee> save(@RequestBody Employee employeeRequest) {
        Employee employee = service.save(employeeRequest);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

}
