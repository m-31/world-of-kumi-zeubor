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


