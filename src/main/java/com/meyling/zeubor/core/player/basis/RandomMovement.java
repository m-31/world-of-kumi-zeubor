package com.meyling.zeubor.core.player.basis;

import com.meyling.zeubor.core.common.Camera;
import com.meyling.zeubor.core.common.CameraMover;
import com.meyling.zeubor.core.common.ViewPoint;
import com.meyling.zeubor.core.physics.CalculatorUtility;
import com.meyling.zeubor.core.physics.ManualMovement;

import static com.meyling.zeubor.core.physics.CalculatorUtility.rotate;

// FIXME not used anywhere
public class RandomMovement implements CameraMover {

    private int counter;
    
    private double delta = 0.001;
    private double thetax;
    private double thetay;
    private double thetaz;

    private final double[] zero;

    public RandomMovement() {
        this(new double[3]);
    }
    public RandomMovement(final double[] zero) {
        this.zero = zero;
    }

    public void move(final Camera camera) {
        final ViewPoint viewPoint = camera.getViewPoint();
        counter = (counter + 1) % 10;
        if (counter == 0) {
            thetax = (Math.random() - 0.5d) / 1000;
            thetay = (Math.random() - 0.5d) / 1000;
            thetaz = (Math.random() - 0.5d) / 10000;
        }
        
        final double[] position = viewPoint.getPosition();

        final double[] x = viewPoint.getX();
        final double[] y = viewPoint.getY();
        final double[] z = viewPoint.getZ();

        double[] newPosition = new double[] { 
            z[0] * getDelta() + position[0],
            z[1] * getDelta() + position[1],
            z[2] * getDelta() + position[2]
        };
        position[0] = newPosition[0];
        position[1] = newPosition[1];
        position[2] = newPosition[2];
        
        if (CalculatorUtility.distanceSquare(viewPoint.getPosition(), zero) > 1) {
            CalculatorUtility.pointToZero(getZero(), viewPoint);
            return;
        }

        // rotate around z axis
        rotate(thetaz, x, y);

        // rotate around x axis
        rotate(thetax, x, z);

        // rotate around y axis
        rotate(thetay, y, z);
    }

    /**
     * @return Returns the delta.
     */
    public final double getDelta() {
        return delta;
    }

    /**
     * @param delta The delta to set.
     */
//    public final void setDelta(double delta) {
//        this.delta = delta;
//    }
//
    public final double[] getZero() {
        return zero;
    }

}


