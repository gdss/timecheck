package com.gdss.timecheck.services;

import com.gdss.timecheck.models.Clockin;
import com.gdss.timecheck.models.Employee;
import com.gdss.timecheck.wrappers.MirrorDay;
import com.gdss.timecheck.wrappers.MirrorResponse;
import com.gdss.timecheck.wrappers.MirrorTotal;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MirrorComponent {

    private static final String WORKED_HOURS = "Horas Trabalhadas";
    private static final String ESTIMATED_HOURS = "Horas Previstas";
    private static final String STORED_HOURS = "Banco de Horas";

    public MirrorResponse build(Employee employee, LocalDate startDate, LocalDate endDate, List<Clockin> clockinList) {
        Map<LocalDate, List<LocalTime>> dateTimeListMap = clockinList.stream().collect(
                Collectors.groupingBy(
                        Clockin::getDate, LinkedHashMap::new, Collectors.mapping(Clockin::getTime, Collectors.toList())
                )
        );
        List<MirrorDay> dayList = new ArrayList<>();
        dateTimeListMap.forEach((date, timeList) -> {
            MirrorDay day = new MirrorDay();
            day.setDate(date);
            day.setCheckList(timeList.stream().map(LocalTime::toString).collect(Collectors.toList()));
            List<MirrorTotal> propertyList = new ArrayList<>();
            MirrorTotal workedHours = getWorkedHours(timeList);
            MirrorTotal estimatedHours = getEstimatedHours();
            // MirrorTotal storedHours = getStoredHours(workedHours.getValue(), estimatedHours.getValue());
            propertyList.add(workedHours);
            propertyList.add(estimatedHours);
            // propertyList.add(storedHours);
            day.setPropertyList(propertyList);
            dayList.add(day);
        });

        MirrorResponse mirrorResponse = new MirrorResponse();
        mirrorResponse.setEmployee(employee);
        mirrorResponse.setStartDate(startDate);
        mirrorResponse.setEndDate(endDate);
        mirrorResponse.setDayList(dayList);

        return mirrorResponse;
    }

    private MirrorTotal getWorkedHours(List<LocalTime> timeList) {
        LocalTime workedHours = LocalTime.of(0, 0, 0);
        for (int i = 0; i < timeList.size() - 1; i = i + 2) {
            LocalTime time1 = timeList.get(i);
            LocalTime time2 = timeList.get(i + 1);
            workedHours = workedHours.plus(Duration.between(time1, time2));
        }
        MirrorTotal mirrorProperty = new MirrorTotal();
        mirrorProperty.setDescription(WORKED_HOURS);
        mirrorProperty.setValue(workedHours);
        return mirrorProperty;
    }

    private MirrorTotal getEstimatedHours() {
        MirrorTotal mirrorProperty = new MirrorTotal();
        mirrorProperty.setDescription(ESTIMATED_HOURS);
        mirrorProperty.setValue(LocalTime.of(8, 0));
        return mirrorProperty;
    }

    private MirrorTotal getStoredHours(LocalTime workedHours, LocalTime estimatedHours) {
        MirrorTotal mirrorProperty = new MirrorTotal();
        mirrorProperty.setDescription(STORED_HOURS);
        LocalTime storedHours = estimatedHours.minus(Duration.between(LocalTime.of(0,0), workedHours));
        mirrorProperty.setValue(storedHours);
        return mirrorProperty;
    }

}
