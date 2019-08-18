package com.gdss.timecheck.wrappers;

import com.gdss.timecheck.models.Employee;

import java.util.Date;
import java.util.List;

public class MirrorResponse {

    private Employee employee;
    private Date startDate;
    private Date endDate;
    private List<MirrorDay> dayList;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<MirrorDay> getDayList() {
        return dayList;
    }

    public void setDayList(List<MirrorDay> dayList) {
        this.dayList = dayList;
    }

}
