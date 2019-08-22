package com.gdss.timecheck.services;

import com.gdss.timecheck.exceptions.MinuteClockinException;
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
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ClockinService {

    @Autowired
    ClockinRepository repository;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    MirrorComponent mirrorComponent;

    public Clockin create(ClockinRequest clockinRequest) throws MinuteClockinException {
        Employee employee = employeeService.findByPis(clockinRequest.getPis());

        LocalDate localDate = clockinRequest.getDateTime().toLocalDate();
        LocalTime localTime = clockinRequest.getDateTime().toLocalTime().truncatedTo(ChronoUnit.MINUTES);

        Optional findOne = repository.findOne(ClockinSpec.checkExists(localDate, localTime));
        if (findOne.isPresent()) {
            throw new MinuteClockinException();
        }

        Clockin clockin = new Clockin();
        clockin.setEmployee(employee);
        clockin.setDate(localDate);
        clockin.setTime(localTime);
        return repository.save(clockin);
    }

    public MirrorResponse mirror(MirrorRequest mirrorRequest) {
        Employee employee = employeeService.findByPis(mirrorRequest.getPis());
        YearMonth yearMonth = mirrorRequest.getYearMonth();
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        List<Clockin> clockinList = repository.findAll(ClockinSpec.days(employee, startDate, endDate));
        return mirrorComponent.build(employee, yearMonth, clockinList);
    }

}
