package org.example;

import java.util.ArrayList;
import java.util.List;

public class Trk {

    private List<Pt> pts;

    public Trk() {
        pts = new ArrayList<>();
    }

    public void addPt(Pt p) {
        pts.add(p);
    }

    public List<Pt> getPts() {
        return pts;
    }

    public double distanceMeters() {
        //  pairnw to last distance
        if (pts.size() == 0) return 0;
        return pts.get(pts.size() - 1).getDist();
    }
}

