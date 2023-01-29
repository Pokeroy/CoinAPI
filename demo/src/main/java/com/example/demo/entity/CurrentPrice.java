package com.example.demo.entity;

public class CurrentPrice {

    private Time time;
    private Bpi bpi;

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Bpi getBpi() {
        return bpi;
    }

    public void setBpi(Bpi bpi) {
        this.bpi = bpi;
    }

    @Override
    public String toString() {
        return "CurrentPrice{" +
                "time=" + time +
                ", bpi=" + bpi +
                '}';
    }
}
