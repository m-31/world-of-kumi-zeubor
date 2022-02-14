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
package com.meyling.zeubor.core.player;

import com.meyling.zeubor.core.common.MovementCalculator;
import com.meyling.zeubor.core.common.PhotoPlateAttributes;
import com.meyling.zeubor.core.common.Player;
import com.meyling.zeubor.core.common.ViewPoint;
import com.meyling.zeubor.core.physics.PhotoPlateImpl;
import com.meyling.zeubor.core.world.CameraImpl;

import java.awt.*;

public abstract class AbstractPlayer implements Player, MovementCalculator {
    
    private final CameraImpl camera;
    
    private int alga;
    
    
    public AbstractPlayer(final String name, final int width, final int height) {
        final PhotoPlateImpl photoPlate = new PhotoPlateImpl();
        final ViewPoint viewPoint = new ViewPoint(name);
        viewPoint.setColor(Color.red.getRGB() + Color.blue.getRGB());
        PhotoPlateAttributes attributes = new PhotoPlateAttributes();
        photoPlate.setSensitivity(attributes.getSensitivity());
        photoPlate.setZoom(attributes.getZoom());
        photoPlate.setSnapshot(attributes.getSnapshot());
        photoPlate.init(width, height);
        camera = new CameraImpl(photoPlate, viewPoint);
    }
    
    public String getName() {
        return camera.getViewPoint().getName();
    }
    
    public int getAlgae() {
        return alga;
    }
    
    public void incrementAlga() {
        alga++;
    }
    
    public CameraImpl getCamera() {
        return camera;
    }
    
    public final ViewPoint getViewPoint() {
        return camera.getViewPoint();
    }

}
