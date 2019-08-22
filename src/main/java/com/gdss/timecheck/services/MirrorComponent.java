package com.gdss.timecheck.services;

import com.gdss.timecheck.models.Clockin;
import com.gdss.timecheck.models.Employee;
import com.gdss.timecheck.wrappers.MirrorDay;
import com.gdss.timecheck.wrappers.MirrorResponse;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MirrorComponent {

    public MirrorResponse build(Employee employee, YearMonth yearMonth, List<Clockin> clockinList) {
        Map<LocalDate, List<LocalTime>> dateTimeListMap = clockinList.stream().collect(
                Collectors.groupingBy(
                        Clockin::getDate, LinkedHashMap::new, Collectors.mapping(Clockin::getTime, Collectors.toList())
                )
        );

        Duration totalMonthDuration = Duration.ZERO;
        List<MirrorDay> dayList = new ArrayList<>();
        for (Map.Entry<LocalDate, List<LocalTime>> entry : dateTimeListMap.entrySet()) {
            LocalDate date = entry.getKey();
            List<LocalTime> timeList = entry.getValue();

            Duration workedHoursDayDuration = getWorkedHours(date, timeList);
            totalMonthDuration = totalMonthDuration.plus(workedHoursDayDuration);

            MirrorDay day = new MirrorDay();
            day.setDate(date);
            day.setTimeList(timeList.stream().map(LocalTime::toString).collect(Collectors.toList()));
            day.setWorkedHoursDay(durationToString(workedHoursDayDuration));
            day.setRestedIntervalOk(isRestedIntervalOk(timeList));
            dayList.add(day);
        }

        MirrorResponse mirrorResponse = new MirrorResponse();
        mirrorResponse.setDayList(dayList);
        mirrorResponse.setEmployee(employee);
        mirrorResponse.setYearMonth(yearMonth);
        mirrorResponse.setWorkedHoursMonth(durationToString(totalMonthDuration));
        return mirrorResponse;
    }

    /**
     * Aos sábados a cada 60 minutos trabalhados são contabilizados 90 minutos.
     * Aos domingos a cada 60 minutos trabalhados são contabilizados 120 minutos
     * De segunda a sexta feira a cada 60 minutos trabalhados são contabilizados 60 minutos.
     *
     * @param date
     * @param duration
     * @return
     */
    private Duration getDurationConstraintDay(LocalDate date, Duration duration) {
        Duration durationConstraintDay = Duration.ZERO;
        switch (date.getDayOfWeek()) {
            case SATURDAY:
                durationConstraintDay = Duration.ofMinutes((long) (duration.toMinutes() * 0.5));
                break;
            case SUNDAY:
                durationConstraintDay = duration;
                break;
        }
        return durationConstraintDay;
    }

    /**
     * Para trabalho realizado entre as 22:00 e 06:00 a cada 60 minutos trabalhados são contabilizados 72 minutos.
     *
     * @param time1
     * @param time2
     * @param duration
     * @return
     */
    private Duration getDurationConstraintHour(LocalTime time1, LocalTime time2, Duration duration) {
        Duration durationConstraintHour = Duration.ZERO;
        if (time1.getHour() < 6) {
            if (time2.getHour() >= 6) {
                LocalTime constraint = LocalTime.of(6, 0);
                durationConstraintHour = Duration.ofMinutes((long) (Duration.between(time1, constraint).toMinutes() * 0.2));
            } else {
                durationConstraintHour = Duration.ofMinutes((long) (duration.toMinutes() * 0.2));
            }
        }
        if (time2.getHour() >= 22) {
            if (time1.getHour() < 22) {
                LocalTime constraint = LocalTime.of(22, 0);
                durationConstraintHour = Duration.ofMinutes((long) (Duration.between(constraint, time2).toMinutes() * 0.2));
            } else {
                durationConstraintHour = Duration.ofMinutes((long) (duration.toMinutes() * 0.2));
            }
        }
        return durationConstraintHour;
    }


    /**
     * Abaixo de 4 horas trabalhas não é necessário descanso
     * Acima de 4 horas e abaixo de 6 horas trabalhadas é necessário um descanso mínimo de 15 minutos.
     * Acima de 6 horas trabalhadas é necessário um descanso mínimo de 1 hora
     *
     * @param timeList
     * @return
     */
    public boolean isRestedIntervalOk(List<LocalTime> timeList) {
        Duration workedDuration = Duration.ZERO;
        Duration restedDuration = Duration.ZERO;
        for (int i = 0; i < timeList.size() - 1; i = i + 2) {
            LocalTime time1 = timeList.get(i);
            LocalTime time2 = timeList.get(i + 1);
            workedDuration = workedDuration.plus(Duration.between(time1, time2));

            if (i + 2 < timeList.size()) {
                LocalTime time3 = timeList.get(i + 2);
                restedDuration = restedDuration.plus(Duration.between(time2, time3));
            }
        }

        long workedHours = workedDuration.toHours();
        long restedMinutes = restedDuration.toMinutes();

        if (workedHours < 4) {
            return true;
        } else if (workedHours < 6 && restedMinutes >= 15) {
            return true;
        } else if (workedHours >= 6 && restedMinutes >= 60) {
            return true;
        }

        return false;
    }

    public Duration getWorkedHours(LocalDate date, List<LocalTime> timeList) {
        Duration totalDuration = Duration.ZERO;
        for (int i = 0; i < timeList.size() - 1; i = i + 2) {
            LocalTime time1 = timeList.get(i);
            LocalTime time2 = timeList.get(i + 1);
            Duration duration = Duration.between(time1, time2);
            Duration durationConstraintDay = getDurationConstraintDay(date, duration);
            Duration durationConstraintHour = getDurationConstraintHour(time1, time2, duration);
            totalDuration = totalDuration.plus(duration).plus(durationConstraintDay).plus(durationConstraintHour);
        }
        return totalDuration;
    }

    private String durationToString(Duration duration) {
        long minutesTotal = duration.toMinutes();
        long hours = minutesTotal / 60;
        long minutes = minutesTotal % 60;
        return String.format("%02d", hours) + ":" + String.format("%02d", minutes);
    }

}
