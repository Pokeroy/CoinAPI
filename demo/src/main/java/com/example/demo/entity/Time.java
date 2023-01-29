package com.example.demo.entity;

import java.util.Date;

public class Time {
    private Date updatedISO;

    public Date getUpdatedISO() {
        return updatedISO;
    }

    public void setUpdatedISO(Date updatedISO) {
        this.updatedISO = updatedISO;
    }

    @Override
    public String toString() {
        return "Time{" +
                "updatedISO='" + updatedISO + '\'' +
                '}';
    }
}
