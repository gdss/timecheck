package com.gdss.timecheck.wrappers;

import java.time.LocalTime;

public class MirrorTotal {

    private String description;
    private LocalTime value;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalTime getValue() {
        return value;
    }

    public void setValue(LocalTime value) {
        this.value = value;
    }
}
