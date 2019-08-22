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

            Duration restHoursDayDuration = getRestedHours(date, timeList);
            boolean restedIntervalOk = verifyRestedInterval(workedHoursDayDuration, restHoursDayDuration);

            MirrorDay day = new MirrorDay();
            day.setDate(date);
            day.setTimeList(timeList.stream().map(LocalTime::toString).collect(Collectors.toList()));
            day.setWorkedHoursDay(durationToString(workedHoursDayDuration));
            day.setRestedIntervalOk(restedIntervalOk);
            dayList.add(day);
        }

        MirrorResponse mirrorResponse = new MirrorResponse();
        mirrorResponse.setDayList(dayList);
        mirrorResponse.setEmployee(employee);
        mirrorResponse.setYearMonth(yearMonth);
        mirrorResponse.setWorkedHoursMonth(durationToString(totalMonthDuration));
        return mirrorResponse;
    }

    public Duration getWorkedHours(LocalDate date, List<LocalTime> timeList) {
        Duration totalDuration = Duration.ZERO;
        for (int i = 0; i < timeList.size() - 1; i = i + 2) {
            LocalTime time1 = timeList.get(i);
            LocalTime time2 = timeList.get(i + 1);
            Duration duration = Duration.between(time1, time2);

            if (time1.getHour() < 6) {
                // Para trabalho realizado antes das 06:00 a cada 60 minutos trabalhados são contabilizados 72 minutos.
                if (time2.getHour() >= 6) {
                    LocalTime constraint = LocalTime.of(6, 0);
                    duration = Duration.ofMinutes((long) (duration.toMinutes() + Duration.between(time1, constraint).toMinutes() * 0.2));
                } else {
                    duration = Duration.ofMinutes((long) (duration.toMinutes() * 1.2));
                }
            }

            if (time2.getHour() >= 22) {
                // Para trabalho realizado após as 22:00 a cada 60 minutos trabalhados são contabilizados 72 minutos.
                if (time1.getHour() < 22) {
                    LocalTime constraint = LocalTime.of(22, 0);
                    duration = Duration.ofMinutes((long) (duration.toMinutes() + Duration.between(constraint, time2).toMinutes() * 0.2));
                } else {
                    duration = Duration.ofMinutes((long) (duration.toMinutes() * 1.2));
                }
            }

            switch (date.getDayOfWeek()) {
                case SATURDAY:
                    // Aos sábados a cada 60 minutos trabalhados são contabilizados 90 minutos.
                    duration = Duration.ofMinutes((long) (duration.toMinutes() * 1.5));
                    break;
                case SUNDAY:
                    // Aos domingos a cada 60 minutos trabalhados são contabilizados 120 minutos
                    duration = duration.multipliedBy(2);
                    break;
                default:
                    // De segunda a sexta feira a cada 60 minutos trabalhados são contabilizados 60 minutos.
            }
            totalDuration = totalDuration.plus(duration);
        }
        return totalDuration;
    }

    public Duration getRestedHours(LocalDate date, List<LocalTime> timeList) {
        Duration totalDuration = Duration.ZERO;
        for (int i = 1; i < timeList.size() - 1; i = i + 2) {
            LocalTime time1 = timeList.get(i);
            LocalTime time2 = timeList.get(i + 1);
            Duration duration = Duration.between(time1, time2);
            totalDuration = totalDuration.plus(duration);
        }
        return totalDuration;
    }

    public boolean verifyRestedInterval(Duration workedHoursDayDuration, Duration restHoursDayDuration) {
        long workedHours = workedHoursDayDuration.toHours();
        long restedMinutes = restHoursDayDuration.toMinutes();

        if (workedHours < 4) {
            // Abaixo de 4 horas trabalhas não é necessário descanso
            return true;
        } else if (workedHours < 6 && restedMinutes >= 15) {
            // Acima de 4 horas e abaixo de 6 horas trabalhadas é necessário um descanso mínimo de 15 minutos.
            return true;
        } else if (workedHours >= 6 && restedMinutes >= 60) {
            // Acima de 6 horas trabalhadas é necessário um descanso mínimo de 1 hora
            return true;
        }
        return false;
    }

    private String durationToString(Duration duration) {
        long minutesTotal = duration.toMinutes();
        long hours = minutesTotal / 60;
        long minutes = minutesTotal % 60;
        return String.format("%02d", hours) + ":" + String.format("%02d", minutes);
    }

}
