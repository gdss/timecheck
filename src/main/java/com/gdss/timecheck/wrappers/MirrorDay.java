package com.gdss.timecheck.wrappers;

import java.time.LocalDate;
import java.util.List;

public class MirrorDay {

    private LocalDate date;
    private List<String> checkList;
    private List<MirrorTotal> propertyList;

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

    public List<MirrorTotal> getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(List<MirrorTotal> propertyList) {
        this.propertyList = propertyList;
    }
}
