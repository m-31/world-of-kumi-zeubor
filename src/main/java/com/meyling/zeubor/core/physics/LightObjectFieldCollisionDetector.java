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

import com.meyling.zeubor.core.common.LightObject;
import com.meyling.zeubor.core.common.LightObjectField;


/**
 * Star field.
 */
public final class LightObjectFieldCollisionDetector  {

    /**
     * Can viewpoint move to new position or is there a collision?
     *
     * @param lightObject
     * @param newPosition
     * @return  Collision object or null.
     */
    public LightObject collision(final LightObjectField field, final LightObject lightObject, final double newPosition[]) {
        for (int i = 0; i < field.getNumberOfObjects(); i++) {
            if (field.getLightObject(i).equals(lightObject)) {
                continue;
            }
            final double diff = field.getLightObject(i).getRadius() + lightObject.getRadius();
            if (CalculatorUtility.distanceSquare(field.getLightObject(i).getPosition(), newPosition) < diff * diff) {
//                System.out.println("" + i + " distance: " + CalculatorUtility.distance(field.getLightObject(i).getPosition(), newPosition));
//                System.out.println("" + i + " diff: " + diff);
//                System.out.println(lightObject.getName() + " collides with " + field.getLightObject(i).getName());
                return field.getLightObject(i);
            }
        }
        return null;
    }
}


