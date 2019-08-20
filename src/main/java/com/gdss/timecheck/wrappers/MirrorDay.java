package com.gdss.timecheck.wrappers;

import java.time.LocalDate;
import java.util.List;

public class MirrorDay {

    private LocalDate date;
    private List<String> checkList;
    private String workedHoursDay;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<String> getCheckList() {
        return checkList;
    }

    public void setCheckList(List<String> checkList) {
        this.checkList = checkList;
    }

    public String getWorkedHoursDay() {
        return workedHoursDay;
    }

    public void setWorkedHoursDay(String workedHoursDay) {
        this.workedHoursDay = workedHoursDay;
    }
}
