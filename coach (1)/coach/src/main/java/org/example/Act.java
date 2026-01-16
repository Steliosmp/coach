package org.example;

import java.util.ArrayList;
import java.util.List;

public class Act {

    private String sport;      // Running, Cycling klp (krataw string gia na min kanei enum)
    private String startId;    // to <Id> (den to xreiazomai full)
    private List<Lap> laps;

    public Act(String sport, String startId) {
        this.sport = sport;
        this.startId = startId;
        this.laps = new ArrayList<>();
    }

    public void addLap(Lap l) {
        laps.add(l);
    }

    public String getSport() {
        return sport;
    }

    public List<Lap> getLaps() {
        return laps;
    }

    public boolean hasDistance() {
        // an exei trackpoints me distance
        return totalMeters() > 0;
    }

    public double totalMeters() {
        double m = 0;
        for (Lap l : laps) m += l.totalMeters();
        return m;
    }

    public double totalKm() {
        return totalMeters() / 1000.0;
    }
}

