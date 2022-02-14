package com.meyling.zeubor.core.physics;

import com.meyling.zeubor.core.common.LightObject;



/**
 * Viewpoint with position and orientation.
 *
 */
public final class DistanceEntry implements Comparable<DistanceEntry> {


    private final double distance;
    private final LightObject lightObject;

    public DistanceEntry(final double distance, final LightObject lightObject) {
        this.distance = distance;
        this.lightObject = lightObject;
    }


    public final double getDistance() {
        return distance;
    }

    public final LightObject getLightObject() {
        return lightObject;
    }

    public int compareTo(final DistanceEntry o) {
        return Double.compare(getDistance(), o.getDistance());
    }

}
