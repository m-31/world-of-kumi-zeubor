package com.meyling.zeubor.core.physics;

import com.meyling.zeubor.core.common.Camera;
import com.meyling.zeubor.core.common.CameraMover;
import com.meyling.zeubor.core.common.ViewPoint;
import com.meyling.zeubor.core.physics.CalculatorUtility;

import static com.meyling.zeubor.core.physics.CalculatorUtility.rotate;


/**
 * Move viewpoint according to two angles.
 */
public class ManualMovement implements CameraMover {

    private double xtheta = 0;

    private double ytheta = 0;

    private double ztheta = 0;

    private double delta = 0;

    private final double[] zero;

    /**
     * Constructor.
     *
     * @param    zero    Observer reference vector. For example the center of gravity.
     */
    public ManualMovement(final double[] zero) {
        this.zero = zero;
    }
    
    public ManualMovement() {
        this(new double[3]);
    }
    
    
    public void move(final Camera camera) {
        final ViewPoint viewPoint = camera.getViewPoint();
        final double[] x = viewPoint.getX();
        final double[] y = viewPoint.getY();
        final double[] z = viewPoint.getZ();

        // rotate around z axis
        rotate(ztheta, x, y);

        // rotate around x axis
        rotate(xtheta, x, z);

        // rotate around y axis
        rotate(ytheta, y, z);

        final double[] position = viewPoint.getPosition();
/*        
        final double[] zero = getZero();
        position[0] = -z[0] * getRadius() + zero[0];
        position[1] = -z[1] * getRadius() + zero[1];
        position[2] = -z[2] * getRadius() + zero[2];

        position[0] = -z[0] * getRadius() * getDelta() + position[0];
        position[1] = -z[1] * getRadius() * getDelta() + position[1];
        position[2] = -z[2] * getRadius() * getDelta() + position[2];
*/
        double[] newPosition = new double[] { 
            z[0] * getDelta() + position[0],
            z[1] * getDelta() + position[1],
            z[2] * getDelta() + position[2]
        };
        position[0] = newPosition[0];
        position[1] = newPosition[1];
        position[2] = newPosition[2];
    }

    public void pointToZero(final ViewPoint viewPoint) {
        CalculatorUtility.pointToZero(getZero(), viewPoint);
    }

    /**
     * @param xtheta The xtheta to set.
     */
    public final void setXtheta(double xtheta) {
        this.xtheta = xtheta;
    }

    /**
     * @param ytheta The ytheta to set.
     */
    public final void setYtheta(double ytheta) {
        this.ytheta = ytheta;
    }

    /**
     * @param ztheta The ztheta to set.
     */
    public final void setZtheta(double ztheta) {
        this.ztheta = ztheta;
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
    public final void setDelta(double delta) {
        this.delta = delta;
    }

    public final double[] getZero() {
        return zero;
    }

}


