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
package com.meyling.zeubor.core.player.brain;

import com.meyling.zeubor.core.common.Movement;
import com.meyling.zeubor.core.common.Picture;
import com.meyling.zeubor.core.io.IoUtility;
import com.meyling.zeubor.core.biology.brain.BrainFromJson;

import java.awt.*;

public abstract class AdvancedAbstractBrainPlayer extends AbstractBrainPlayer {
    
    public AdvancedAbstractBrainPlayer(final String name, final int width, final int height,
            final BrainFromJson brain, final String json) {
        super(name, width, height, brain);
        getCamera().getPhotoPlate().setZoom(100);
        getJsonBrain().grow(json);
        getViewPoint().setName(getJsonBrain().getName());
    }
    
    public BrainFromJson getJsonBrain() {
        return (BrainFromJson) getBrain();
    }
    
    public String getJson() {
        return getJsonBrain().getJson();
    }
    
    public void save() {
        final String name = getName() + ".json";
        IoUtility.save(name, getJson());
    }

    public Movement calculateMovement() {
        final Picture im = getCamera().getPhotoPlate().getPicture();
        
        Picture dimg = im.getScaledPicture(getBrain().getEyeWidth(), getBrain().getEyeHeight());

        int[][] green = new int[getBrain().getEyeHeight()][getBrain().getEyeWidth()]; 
        int max = 0;
        for (int i = 0; i < getBrain().getEyeHeight(); i++) {
            for (int j = 0; j < getBrain().getEyeWidth(); j++) {
                final Color color = new Color(dimg.getColor(j, getBrain().getEyeHeight() - 1 - i));
                green[i][j] = color.getGreen();
                if (green[i][j] > max) {
                    max = green[i][j];
                }
            }
        }

        for (int i = 0; i < getBrain().getEyeHeight(); i++) {
            for (int j = 0; j < getBrain().getEyeWidth(); j++) {
                boolean fire = (green[i][j] >= max && green[i][j] > 0);
                getBrain().getInputNeurons().get(i * getBrain().getEyeWidth() + j).setFire(fire);
            }
        }
         
        saveInputNeuronFire();
        
//        getBrain().printInput();
        getBrain().iterate();
        int deltaX = getBrain().getDirectionX();
        int deltaY = getBrain().getDirectionY();
        int deltaZ = getBrain().getSpeed();
//        System.out.println(deltaX + " " + deltaY + " " + deltaZ);
        return new Movement(deltaX, deltaY, deltaZ);
    }

}
