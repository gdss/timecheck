package com.gdss.timecheck.wrappers;

import java.time.YearMonth;

public class MirrorRequest {

    private String pis;
    private YearMonth yearMonth;

    public String getPis() {
        return pis;
    }

    public void setPis(String pis) {
        this.pis = pis;
    }

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }
}
