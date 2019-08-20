package com.gdss.timecheck.wrappers;

import java.time.LocalDate;
import java.util.List;

public class MirrorDay {

    private LocalDate date;
    private List<String> timeList;
    private String workedHoursDay;
    private boolean restedIntervalOk;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<String> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<String> timeList) {
        this.timeList = timeList;
    }

    public String getWorkedHoursDay() {
        return workedHoursDay;
    }

    public void setWorkedHoursDay(String workedHoursDay) {
        this.workedHoursDay = workedHoursDay;
    }

    public boolean isRestedIntervalOk() {
        return restedIntervalOk;
    }

    public void setRestedIntervalOk(boolean restedIntervalOk) {
        this.restedIntervalOk = restedIntervalOk;
    }
}
