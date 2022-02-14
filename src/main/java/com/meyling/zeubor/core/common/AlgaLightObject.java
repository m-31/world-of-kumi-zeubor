package com.meyling.zeubor.core.common;


/**
 * Object with color, position, radius, name (and velocity).
 *
 */
public class AlgaLightObject extends LightObject {

    public AlgaLightObject(final int color, final double[] position, final double[] velocity) {
        super(color, position, velocity);
    }

    /**
     * Constructor.
     *
     * @param   color       Color of object.
     * @param   position    Position of object.
     */
    public AlgaLightObject(final int color, final double[] position) {
        super(color, position);
    }

    public String getName() {
        return "alga";
    }
    
}


