package com.gdss.timecheck.models;

import java.time.LocalDateTime;

public class ClockinRequest {

    private String pis;
    private LocalDateTime dateTime;

    public String getPis() {
        return pis;
    }

    public void setPis(String pis) {
        this.pis = pis;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
