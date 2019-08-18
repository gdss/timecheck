package com.gdss.timecheck.services;

import com.gdss.timecheck.models.Clockin;
import com.gdss.timecheck.models.Employee;
import com.gdss.timecheck.repositories.ClockinRepository;
import com.gdss.timecheck.repositories.specs.ClockinSpec;
import com.gdss.timecheck.wrappers.ClockinRequest;
import com.gdss.timecheck.wrappers.MirrorDay;
import com.gdss.timecheck.wrappers.MirrorRequest;
import com.gdss.timecheck.wrappers.MirrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public MirrorResponse mirror(MirrorRequest mirrorRequest) {
        Employee employee = employeeService.findByPis(mirrorRequest.getPis());
        Date startDate = mirrorRequest.getStartDate();
        Date endDate = mirrorRequest.getEndDate();

        List<Clockin> clockinList = repository.findAll(ClockinSpec.days(employee, startDate, endDate));
        Map<Date, List<Date>> dateTimeListMap = clockinList.stream().collect(
                Collectors.groupingBy(
                        Clockin::getDate, LinkedHashMap::new, Collectors.mapping(Clockin::getTime, Collectors.toList())
                )
        );
        List<MirrorDay> dayList = new ArrayList<>();
        dateTimeListMap.forEach((date, timeList) -> {
            MirrorDay day = new MirrorDay();
            day.setDate(date);
            day.setCheckList(timeList.stream().map(Date::toString).collect(Collectors.toList()));
            dayList.add(day);
        });

        MirrorResponse mirrorResponse = new MirrorResponse();
        mirrorResponse.setEmployee(employee);
        mirrorResponse.setStartDate(startDate);
        mirrorResponse.setEndDate(endDate);
        mirrorResponse.setDayList(dayList);

        return mirrorResponse;
    }
}
