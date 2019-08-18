package com.gdss.timecheck.services;

import com.gdss.timecheck.models.Clockin;
import com.gdss.timecheck.models.ClockinRequest;
import com.gdss.timecheck.models.Employee;
import com.gdss.timecheck.repositories.ClockinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class ClockinService {

    @Autowired
    protected ClockinRepository repository;

    @Autowired
    protected EmployeeService employeeService;

    public Clockin create(ClockinRequest clockinRequest) {
        Employee employee = employeeService.findByPis(clockinRequest.getPis());
        Timestamp timestamp = Timestamp.valueOf(clockinRequest.getDateTime());
        Clockin clockin = new Clockin();
        clockin.setEmployee(employee);
        clockin.setDate(timestamp);
        clockin.setTime(timestamp);
        repository.save(clockin);
        return clockin;
    }

}
