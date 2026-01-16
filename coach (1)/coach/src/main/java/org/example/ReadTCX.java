package org.example;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReadTCX {

    public static List<Act> fromFile(File f) {

        List<Act> acts = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(f);

            NodeList activityList = doc.getElementsByTagName("Activity");

            for (int i = 0; i < activityList.getLength(); i++) {
                Element actEl = (Element) activityList.item(i);

                String sport = actEl.getAttribute("Sport");
                String id = getText(actEl, "Id"); // start id

                Act act = new Act(sport, id);

                NodeList lapList = actEl.getElementsByTagName("Lap");
                for (int j = 0; j < lapList.getLength(); j++) {

                    Element lapEl = (Element) lapList.item(j);
                    String lapStart = lapEl.getAttribute("StartTime");
                    Lap lap = new Lap(lapStart);

                    // Track nodes mesa sto lap
                    NodeList trackList = lapEl.getElementsByTagName("Track");
                    for (int k = 0; k < trackList.getLength(); k++) {
                        Element trackEl = (Element) trackList.item(k);
                        Trk trk = new Trk();

                        NodeList tpList = trackEl.getElementsByTagName("Trackpoint");
                        for (int z = 0; z < tpList.getLength(); z++) {
                            Element tpEl = (Element) tpList.item(z);

                            String time = getText(tpEl, "Time");

                            // DistanceMeters
                            double dist = 0;
                            String distStr = getText(tpEl, "DistanceMeters");
                            if (distStr != null && !distStr.isEmpty()) {
                                dist = Double.parseDouble(distStr);
                            }

                            Integer hr = null;
                            NodeList hrNodes = tpEl.getElementsByTagName("HeartRateBpm");
                            if (hrNodes.getLength() > 0) {
                                Element hrEl = (Element) hrNodes.item(0);
                                String v = getText(hrEl, "Value");
                                if (v != null && !v.isEmpty()) hr = Integer.parseInt(v);
                            }

                            // add point sto track
                            trk.addPt(new Pt(time, dist, hr));
                        }

                        lap.addTrack(trk);
                    }

                    act.addLap(lap);
                }

                acts.add(act);
            }

        } catch (Exception e) {
            // print stack
            System.out.println("Provlimma sto arxeio: " + f.getName());
            e.printStackTrace();
        }

        return acts;
    }

    private static String getText(Element parent, String tag) {
        NodeList list = parent.getElementsByTagName(tag);
        if (list.getLength() == 0) return null;

        Node n = list.item(0);
        if (n.getFirstChild() == null) return null;

        return n.getFirstChild().getNodeValue();
    }
}
