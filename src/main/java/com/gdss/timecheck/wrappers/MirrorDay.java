package com.gdss.timecheck.wrappers;

import java.util.Date;
import java.util.List;

public class MirrorDay {

    private Date date;
    private List<String> checkList;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<String> getCheckList() {
        return checkList;
    }

    public void setCheckList(List<String> checkList) {
        this.checkList = checkList;
    }

}
