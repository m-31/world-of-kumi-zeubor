package com.meyling.zeubor.core.common;

/**
 * Viewpoint with position and orientation.
 */
public final class ViewPoint extends LightObject {

    private final double[] x;
    private final double[] y;
    private final double[] z;
    private String name;

    public ViewPoint() {
        this(new double[3], "");
    }

    public ViewPoint(final String name) {
        this(new double[3], name);
    }

    public ViewPoint(final double[] position, final String name) {
        super(0xff0000, position);
        x = new double[3];
        x[0] = 1;
        y = new double[3];
        y[1] = 1;
        z = new double[3];
        z[2] = 1;
        this.name = name;
    }

    public ViewPoint(final double[] position, final double[] x, final double[] y,
            final double[] z, final String name) {
        super(0xff0000, position);
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    public final double[] getX() {
        return x;
    }

    public final double[] getY() {
        return y;
    }

    public final double[] getZ() {
        return z;
    }
    
    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

}


