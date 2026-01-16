package org.example;

import java.time.Duration;
import java.time.Instant;

public class Calc {

    public static String totalTime(Act a) {
        // briskei prwto kai teleftaio timestamp apo ola ta points
        // an den exoume points, epistrefei 00:00:00
        Instant first = null;
        Instant last = null;

        for (Lap l : a.getLaps()) {
            for (Trk t : l.getTracks()) {
                for (Pt p : t.getPts()) {
                    Instant cur = Instant.parse(p.getTime());
                    if (first == null || cur.isBefore(first)) first = cur;
                    if (last == null || cur.isAfter(last)) last = cur;
                }
            }
        }

        if (first == null || last == null) return "00:00:00";

        long sec = Duration.between(first, last).getSeconds();
        long h = sec / 3600;
        long m = (sec % 3600) / 60;
        long s = sec % 60;

        return String.format("%02d:%02d:%02d", h, m, s);
    }

    public static double avgHR(Act a) {
        int sum = 0;
        int cnt = 0;

        for (Lap l : a.getLaps()) {
            for (Trk t : l.getTracks()) {
                for (Pt p : t.getPts()) {
                    if (p.getHr() != null) {
                        sum += p.getHr();
                        cnt++;
                    }
                }
            }
        }

        if (cnt == 0) return 0;
        return (double) sum / cnt;
    }

    public static double avgSpeed(Act a) {
        // km/h = km / hours
        double km = a.totalKm();
        double hours = parseToHours(totalTime(a));
        if (hours == 0) return 0;
        return km / hours;
    }

    public static double avgPace(Act a) {
        // min/km = minutes / km
        double km = a.totalKm();
        double minutes = parseToMinutes(totalTime(a));
        if (km == 0) return 0;
        return minutes / km;
    }

    public static double caloriesSimple(Act a, double w) {
        // aplo montelo: C = μ * w * t
        // xrisimopoiw ena mu ana sport
        double mu = 3; // default perpatima
        if (a.getSport().equalsIgnoreCase("Running")) mu = 7; // mesos oros running
        if (a.getSport().equalsIgnoreCase("Cycling") || a.getSport().equalsIgnoreCase("Biking")) mu = 5; // mesos oros podilato

        double hours = parseToHours(totalTime(a));


        return mu * w * hours;
    }

    private static double parseToHours(String hhmmss) {
        // hh:mm:ss -> hours
        String[] parts = hhmmss.split(":");
        int h = Integer.parseInt(parts[0]);
        int m = Integer.parseInt(parts[1]);
        int s = Integer.parseInt(parts[2]);

        return h + (m / 60.0) + (s / 3600.0);
    }

    private static double parseToMinutes(String hhmmss) {
        String[] parts = hhmmss.split(":");
        int h = Integer.parseInt(parts[0]);
        int m = Integer.parseInt(parts[1]);
        int s = Integer.parseInt(parts[2]);

        return (h * 60.0) + m + (s / 60.0);
    }
}

