package com.meyling.zeubor.core.common;

import java.util.ArrayList;
import java.util.List;


/**
 * A field of light objects {@see LightObject}s.
 */
public final class LightObjectField  {

    private final List<LightObject> lightObjects;
    
    /** Total impulse of field. */
    private double[] impulse;

    /** Last calculated current total impulse. */
    private double[] currentImpulse;

    /**
     * Constructor.
     */
    public LightObjectField() {
        lightObjects = new ArrayList<LightObject>();
        impulse = new double[3];
        currentImpulse = new double[3];
    }

    public void removeAll() {
        lightObjects.clear();
    }
    
    public final int getNumberOfObjects() {
        return lightObjects.size();
    }

    public final LightObject getLightObject(int i) {
        return lightObjects.get(i);
    }

    public void add(LightObject lightObject) {
        lightObjects.add(lightObject);
    }

    public void remove(LightObject lightObject) {
        lightObjects.remove(lightObject);
    }

    /**
     * Get total impulse of star field. This is the initial value and could be different from
     * an newly calculated value.
     *
     * @return  Total impulse.
     */
    public double[] getInitialImpulse() {
        return impulse;
    }

    /**
     * Get total impulse of star field. This is the current value and could be different from
     * the initial value.
     *
     * @return  Total impulse.
     */
    public double[] getCurrentImpulse() {
        for (int k = 0; k < 3; k++) {
            currentImpulse[k] = 0;
            for (int j = 0; j < getNumberOfObjects(); j++) {
                currentImpulse[k] += 1 * getLightObject(j).getVelocity()[k];    // mass = 1
            }
        }
        return currentImpulse;
    }


}


