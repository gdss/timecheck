package com.gdss.timecheck.services;

import com.gdss.timecheck.models.Clockin;
import com.gdss.timecheck.models.Employee;
import com.gdss.timecheck.repositories.ClockinRepository;
import com.gdss.timecheck.repositories.specs.ClockinSpec;
import com.gdss.timecheck.wrappers.ClockinRequest;
import com.gdss.timecheck.wrappers.MirrorRequest;
import com.gdss.timecheck.wrappers.MirrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ClockinService {

    @Autowired
    protected ClockinRepository repository;

    @Autowired
    protected EmployeeService employeeService;

    @Autowired
    protected MirrorComponent mirrorComponent;

    public Clockin create(ClockinRequest clockinRequest) {
        Employee employee = employeeService.findByPis(clockinRequest.getPis());
        Clockin clockin = new Clockin();
        clockin.setEmployee(employee);
        clockin.setDate(clockinRequest.getDateTime().toLocalDate());
        clockin.setTime(clockinRequest.getDateTime().toLocalTime());
        repository.save(clockin);
        return clockin;
    }

    public MirrorResponse mirror(MirrorRequest mirrorRequest) {
        Employee employee = employeeService.findByPis(mirrorRequest.getPis());
        LocalDate startDate = mirrorRequest.getStartDate();
        LocalDate endDate = mirrorRequest.getEndDate();
        List<Clockin> clockinList = repository.findAll(ClockinSpec.days(employee, startDate, endDate));
        return mirrorComponent.build(employee, startDate, endDate, clockinList);
    }

}
