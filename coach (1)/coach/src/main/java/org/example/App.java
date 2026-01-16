package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) {

        // console app ta args einai ta filenames + proairetika flags
        if (args.length == 0) {
            System.out.println("Xrhsh: java -jar coach.jar [-w varos] file1.tcx file2.tcx ...");
            return;
        }

        Double weight = null; // an den dwsei o xrhsths -w menei null
        List<String> files = new ArrayList<>();

        //  parsing args
        for (int i = 0; i < args.length; i++) {

            if (args[i].equals("-w")) {
                // perimenoume epomeno arg na einai varos
                if (i + 1 < args.length) {
                    weight = Double.parseDouble(args[i + 1]);
                    i++; // skip to weight
                }
            } else {
                files.add(args[i]);
            }
        }

        // diavazoume ena-ena ta arxeia (mporei na exoun pola activities mesa)
        for (String fname : files) {

            File f = new File(fname);
            if (!f.exists()) {
                System.out.println("Den vrika arxeio: " + fname);
                continue;
            }

            List<Act> activities = ReadTCX.fromFile(f);

            // typwnoume ta stats gia kathe activity
            for (Act a : activities) {
                printStats(a, weight);
                System.out.println();
            }
        }
    }

    private static void printStats(Act a, Double weight) {

        System.out.println("Activity: " + a.getSport());

        // total time se HH:MM:SS (timestamps)
        System.out.println("Total Time: " + Calc.totalTime(a));

        // distance
        if (a.hasDistance()) {
            System.out.printf("Total Distance: %.2f km%n", a.totalKm());
        }

        // pace/speed analoga me sport
        if (a.hasDistance()) {
            if (a.getSport().equalsIgnoreCase("Running") || a.getSport().equalsIgnoreCase("Walking")) {
                System.out.printf("Avg Pace: %.2f min/km%n", Calc.avgPace(a));
            } else {
                System.out.printf("Avg Speed: %.2f km/h%n", Calc.avgSpeed(a));
            }
        }

        double hr = Calc.avgHR(a);
        if (hr > 0) {
            System.out.printf("Avg Heart Rate: %.0f bpm%n", hr);
        }

        //an dwsei varos ipologizoume kai tis thermides
        if (weight != null) {
            System.out.printf("Calories: %.0f kcal%n", Calc.caloriesSimple(a, weight));
        }
    }
}
