package org.example;

public class Pt {

    private String time;   // timestamp ws string
    private double dist;   // DistanceMeters
    private Integer hr;    // mporei na min yparxei ara null

    public Pt(String time, double dist, Integer hr) {
        this.time = time;
        this.dist = dist;
        this.hr = hr;
    }

    public String getTime() {
        return time;
    }

    public double getDist() {
        return dist;
    }

    public Integer getHr() {
        return hr;
    }
}

