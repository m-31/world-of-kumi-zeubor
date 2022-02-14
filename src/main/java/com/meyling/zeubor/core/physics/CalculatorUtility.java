/* This file is part of the project "zeubor" - http://www.meyling.com/zeubor
 *
 * Copyright (C) 2014-2015  Michael Meyling
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.meyling.zeubor.core.physics;

import com.meyling.zeubor.core.common.ViewPoint;


/**
 * Contains static methods for calculating with vectors.
 */
public final class CalculatorUtility  {

    /**
     * Hidden constructor.
     */
    private CalculatorUtility() {
        // nothing to do
    }

    public static final double distanceSquare(double[] a, double[] b) {
        return (a[0] - b[0]) * (a[0] - b[0]) + (a[1] - b[1]) * (a[1] - b[1])
            + (a[2] - b[2]) * (a[2] - b[2]);
    }

    public static final double distance(double[] a, double[] b) {
        return Math.sqrt((a[0] - b[0]) * (a[0] - b[0]) + (a[1] - b[1]) * (a[1] - b[1])
            + (a[2] - b[2]) * (a[2] - b[2]));
    }

    public static final double len(double[] a) {
        return Math.sqrt(a[0] * a[0] + a[1] * a[1] + a[2] * a[2]);
    }

    public static final double scalar(double[] a, double[] b) {
        return a[0] * b[0] + a[1] * b[1] + a[2] * b[2];
    }

    public static final double minusScalar(double[] a, double[] b, double[] c) {
        return (a[0] - b[0]) * c[0] + (a[1] - b[1]) * c[1] + (a[2] - b[2]) * c[2];
    }

    public static void normalize(double[] a, double[] def) {
        double len = len(a);
        if (len <= 1000 * Double.MIN_VALUE) {
            a[0] = def[0];
            a[1] = def[1];
            a[2] = def[2];
        } else {
            a[0] = a[0] / len;
            a[1] = a[1] / len;
            a[2] = a[2] / len;
        }
    }

    public static void pointToZero(final double[] zero, final ViewPoint viewPoint) {
        System.out.println("point to zero");
        final double[] x = viewPoint.getX();
        final double[] y = viewPoint.getY();
        final double[] z = viewPoint.getZ();
        // step 1: point z axis towards zero:
        z[0] = zero[0] - viewPoint.getPosition()[0];
        z[1] = zero[1] - viewPoint.getPosition()[1];
        z[2] = zero[2] - viewPoint.getPosition()[2];
        CalculatorUtility.normalize(z, new double[] {0, 0, 1});
        // step 2: point x axis somehow orthogonal to z:
        x[0] = y[1] * z[2] - y[2] * z[1];
        x[1] = y[2] * z[0] - y[0] * z[2];
        x[2] = y[0] * z[1] - y[1] * z[0];
        CalculatorUtility.normalize(x, new double[] {1, 0, 0});
        y[0] = z[1] * x[2] - z[2] * x[1];
        y[1] = z[2] * x[0] - z[0] * x[2];
        y[2] = z[0] * x[1] - z[1] * x[0];
        CalculatorUtility.normalize(y, new double[] {0, 1, 0});
        System.out.println("distance is: " + CalculatorUtility.distance(viewPoint.getPosition(), zero));
        System.out.println("1=" + scalar(viewPoint.getX(), viewPoint.getX()));
        System.out.println("1=" + scalar(viewPoint.getY(), viewPoint.getY()));
        System.out.println("1=" + scalar(viewPoint.getZ(), viewPoint.getZ()));
        System.out.println("0=" + scalar(viewPoint.getX(), viewPoint.getY()));
        System.out.println("0=" + scalar(viewPoint.getY(), viewPoint.getZ()));
        System.out.println("0=" + scalar(viewPoint.getX(), viewPoint.getZ()));
    }

    
    // sRGB luminance(Y) values
    public static final double rY = 0.212655;
    public static final double gY = 0.715158;
    public static final double bY = 0.072187;

    // Inverse of sRGB "gamma" function. (approx 2.2)
    public static double inv_gam_sRGB(int ic) {
        double c = ic / 255.0;
        if ( c <= 0.04045 ) {
            return c / 12.92;
        } else { 
            return Math.pow(((c+0.055) / 1.055), 2.4);
        }
    }

    // sRGB "gamma" function (approx 2.2)
    public static int gam_sRGB(double v) {
        if (v <= 0.0031308) {
            v *= 12.92;
        } else { 
            v = 1.055 * Math.pow(v,1.0/2.4)-0.055;
        }
        return (int) (v * 255 + 0.5);
    }

    // GRAY VALUE ("brightness")
    public static int gray(int r, int g, int b) {
        return gam_sRGB(
            rY*inv_gam_sRGB(r) +
            gY*inv_gam_sRGB(g) +
            bY*inv_gam_sRGB(b)
        );
    }

}


