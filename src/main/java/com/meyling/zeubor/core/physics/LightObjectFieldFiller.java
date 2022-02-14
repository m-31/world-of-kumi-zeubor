package com.meyling.zeubor.core.physics;

import com.meyling.zeubor.core.common.AlgaLightObject;
import com.meyling.zeubor.core.common.LightObject;
import com.meyling.zeubor.core.common.LightObjectField;

import java.awt.*;


public final class LightObjectFieldFiller  {

    private final static int[] LIGHTS = new int[] {
        Color.GREEN.getRGB()
// if you want other colors just comment following in:        
/*        ,
        Color.RED.getRGB(),
        Color.BLUE.getRGB(),
        Color.MAGENTA.getRGB(),
        Color.CYAN.getRGB(), */
    };

    private static final double[] ZERO = new double[3];

    private final LightObjectField field;

    private final LightObjectFieldCollisionDetector collisionDetector;
    
    public LightObjectFieldFiller(final LightObjectField field, final LightObjectFieldCollisionDetector collisionDetector) {
        this.field = field;
        this.collisionDetector = collisionDetector;
    }
    
    public void fillBall(final int numberOfObjects) {
        fillBall(numberOfObjects, 0.5, ZERO);
    }

    public void fillBall(final int numberOfObjects, final double radius) {
        fillBall(numberOfObjects, radius, ZERO);
    }

    public void fillBall(final int numberOfObjects, final double radius, double[] zero) {  // FIXME ZERO = zero?
        final double radiusSquare = radius * radius;
        for (int i = 0; i < numberOfObjects; i++) {
            final double[] position = new double[3];
            int color = LIGHTS[(int) (Math.random() * LIGHTS.length)]; 
            final LightObject lightObject = new AlgaLightObject(color, position);
            do {
                for (int j = 0; j < 3; j++) {
                    position[j] = 2.0d * radius * Math.random() - radius + zero[j];
                }
            } while (CalculatorUtility.distanceSquare(position, zero) > radiusSquare ||
                    null != collisionDetector.collision(field, lightObject, position));
            field.add(lightObject);
        }
    }

    public void fillSquare(final int numberOfObjects) {
        fillSquare(numberOfObjects, 0.5, ZERO);
    }

    public void fillSquare(final int numberOfObjects, final double radius) {
        fillSquare(numberOfObjects, radius, ZERO);
    }

    public void fillSquare(final int numberOfObjects, final double radius, double[] zero) {
        for (int i = 0; i < numberOfObjects; i++) {
            final double[] position = new double[3];
            int color = LIGHTS[(int) (Math.random() * LIGHTS.length)]; 
            final LightObject lightObject = new AlgaLightObject(color, position);
            do {
                for (int j = 0; j < 3; j++) {
                    position[j] = 2.0d * radius * Math.random() - radius + zero[j];
                }
            } while (null != collisionDetector.collision(field, lightObject, position));
            field.add(lightObject);
        }
    }
}


