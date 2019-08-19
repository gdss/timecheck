package com.gdss.timecheck.wrappers;

import com.gdss.timecheck.models.Employee;

import java.time.LocalDate;
import java.util.List;

public class MirrorResponse {

    private Employee employee;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<MirrorDay> dayList;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<MirrorDay> getDayList() {
        return dayList;
    }

    public void setDayList(List<MirrorDay> dayList) {
        this.dayList = dayList;
    }

}
