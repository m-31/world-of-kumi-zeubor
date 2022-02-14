package com.meyling.zeubor.core.common;


/**
 * Object with color, position, radius, name (and velocity).
 *
 */
public abstract class LightObject  {

    private int color;

    private final double[] position;

    private final double[] velocity;


    public LightObject(final int color, final double[] position, final double[] velocity) {
        if (position == null) {
            throw new NullPointerException("position is null");
        }
        if (position.length != 3) {
            throw new IllegalArgumentException("position has not dimension " + 3);
        }
        if (velocity == null) {
            throw new NullPointerException("velocity is null");
        }
        if (velocity.length != 3) {
            throw new IllegalArgumentException("velocity has not dimension " + 3);
        }
        this.color = color;
        this.position = position;
        this.velocity = velocity;
    }

    /**
     * Constructor.
     *
     * @param   color       Color of object.
     * @param   position    Position of object.
     */
    public LightObject(final int color, final double[] position) {
        this(color, position, new double[3]);
    }

    public final int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public double[] getPosition() {
        return position;
    }

    public final double[] getVelocity() {
        return velocity;
    }

    public final double getRadius() {
        return 0.001;
    }

    public abstract String getName();
    
}


