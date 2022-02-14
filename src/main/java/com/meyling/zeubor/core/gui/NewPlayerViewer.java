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

import com.meyling.zeubor.core.common.ViewPoint;
import com.meyling.zeubor.core.common.WorldListener;
import com.meyling.zeubor.core.player.brain.AbstractBrainPlayer;
import com.meyling.zeubor.core.world.World;

import javax.swing.*;
import java.awt.*;


@SuppressWarnings("serial")
public class NewPlayerViewer extends JComponent implements WorldListener {

    private final AbstractBrainPlayer player;
    private final World world;
    private final boolean global;

    public NewPlayerViewer(final AbstractBrainPlayer player, final World world, final boolean global) {
        super();
        this.player = player;
        this.world = world;
        this.global = global;
    }

    public NewPlayerViewer(final AbstractBrainPlayer player, final World world) {
        this(player, world, false);
    }

    public ViewPoint getViewPoint() {
        return player.getViewPoint();
    }

    public final void paintComponent(Graphics g) {
        world.drawPlayerView(player, getWidth(), getHeight(), true, (Graphics2D) g, true, global);
//        world.drawPlayerView(player, 0, 0, getWidth() / 2, getHeight() / 2, true, (Graphics2D) g, true, global);
//        world.drawPlayerView(player, getWidth() / 2, getHeight() / 2, getWidth() / 2, getHeight() / 2, true, (Graphics2D) g, true, global);
    }
    
    public void newIteration() {
        repaint();
    }

}


