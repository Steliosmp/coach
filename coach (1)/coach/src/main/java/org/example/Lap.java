package org.example;

import java.util.ArrayList;
import java.util.List;

public class Lap {

    private String startTime; // from attribute StartTime
    private List<Trk> tracks;

    public Lap(String startTime) {
        this.startTime = startTime;
        this.tracks = new ArrayList<>();
    }

    public void addTrack(Trk t) {
        tracks.add(t);
    }

    public List<Trk> getTracks() {
        return tracks;
    }

    public double totalMeters() {
        double sum = 0;
        for (Trk t : tracks) sum += t.distanceMeters();
        return sum;
    }
}
