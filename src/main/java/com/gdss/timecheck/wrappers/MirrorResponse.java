package com.gdss.timecheck.wrappers;

import com.gdss.timecheck.models.Employee;

import java.time.YearMonth;
import java.util.List;

public class MirrorResponse {

    private Employee employee;
    private YearMonth yearMonth;
    private List<MirrorDay> dayList;
    private MirrorTotal total;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }

    public List<MirrorDay> getDayList() {
        return dayList;
    }

    public void setDayList(List<MirrorDay> dayList) {
        this.dayList = dayList;
    }

    public MirrorTotal getTotal() {
        return total;
    }

    public void setTotal(MirrorTotal total) {
        this.total = total;
    }
}
