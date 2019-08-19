package com.gdss.timecheck.wrappers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class MirrorDay {

    private LocalDate date;
    private List<String> checkList;
    private MirrorTotal total;

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

    public MirrorTotal getTotal() {
        return total;
    }

    public void setTotal(MirrorTotal total) {
        this.total = total;
    }
}
