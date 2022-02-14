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
package com.meyling.zeubor.core.gui;

import com.meyling.zeubor.core.io.IoUtility;
import com.meyling.zeubor.core.physics.CalculatorUtility;
import com.meyling.zeubor.core.player.AbstractPlayer;
import com.meyling.zeubor.core.player.brain.AbstractBrainPlayer;
import com.meyling.zeubor.core.player.brain.BrainPlayer7;
import com.meyling.zeubor.core.player.brain.BrainPlayerFromJson;
import com.meyling.zeubor.core.world.World;


public class Main {

    public static void main ( String[] args ) {
        World world = createWorld();
        world.start();
    }
    

    public static World createWorld() {
        World world = new World();
        
//        AbstractPlayer player1 = new DumbPlayer("Anton", 100, 100);
//        final AbstractPlayer player = new BrainPlayer6(100, 100);
        {
            AbstractBrainPlayer player = new BrainPlayerFromJson(100, 100,  IoUtility.getStringData("ellen.json").data);
            world.add(player);
            player.getViewPoint().getPosition()[0] = 0.1;
            player.getViewPoint().getPosition()[1] = 0.1;
            player.getViewPoint().getPosition()[2] = 0.1;
            CalculatorUtility.pointToZero(new double[3], player.getViewPoint());
        }
            
        {
            AbstractPlayer player = new BrainPlayer7(100, 100);
            world.add(player);
            player.getViewPoint().getPosition()[0] = 0.1;
            player.getViewPoint().getPosition()[1] = -0.1;
            player.getViewPoint().getPosition()[2] = 0.1;
            CalculatorUtility.pointToZero(new double[3], player.getViewPoint());
        }     

        {
//            AbstractPlayer player = new BrainPlayer8(100, 100);
            AbstractPlayer player = new BrainPlayerFromJson(100, 100, IoUtility.getStringData("frances.json").data);
            world.add(player);
            player.getViewPoint().getPosition()[0] = -0.1;
            player.getViewPoint().getPosition()[1] = 0.1;
            player.getViewPoint().getPosition()[2] = 0.1;
            CalculatorUtility.pointToZero(new double[3], player.getViewPoint());
        }    

        {
//            AbstractPlayer player = new BrainPlayer9(100, 100);
            AbstractPlayer player = new BrainPlayerFromJson(100, 100, IoUtility.getStringData("hazel.json").data);
            world.add(player);
            player.getViewPoint().getPosition()[0] = -0.1;
            player.getViewPoint().getPosition()[1] = -0.1;
            player.getViewPoint().getPosition()[2] = 0.1;
            CalculatorUtility.pointToZero(new double[4], player.getViewPoint());
        }
        
        {
//            AbstractPlayer player = new BrainPlayer10(100, 100);
            AbstractPlayer player = new BrainPlayerFromJson(100, 100, IoUtility.getStringData("inga.json").data);
            world.add(player);
            player.getViewPoint().getPosition()[0] = 0.1;
            player.getViewPoint().getPosition()[1] = 0.1;
            player.getViewPoint().getPosition()[2] = -0.1;
            CalculatorUtility.pointToZero(new double[3], player.getViewPoint());
        }    

        {
//            AbstractPlayer player = new BrainPlayer11(100, 100);
            AbstractPlayer player = new BrainPlayerFromJson(100, 100, IoUtility.getStringData("ginny.json").data);
            world.add(player);
            player.getViewPoint().getPosition()[0] = 0.1;
            player.getViewPoint().getPosition()[1] = -0.1;
            player.getViewPoint().getPosition()[2] = -0.1;
            CalculatorUtility.pointToZero(new double[3], player.getViewPoint());
        }
            
        return world;

    }

}