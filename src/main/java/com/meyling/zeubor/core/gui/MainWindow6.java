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

import com.alee.extended.behavior.ComponentMoveBehavior;
import com.alee.extended.debug.TestFrame;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.GroupingType;
import com.alee.extended.statusbar.WebStatusBar;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.button.WebToggleButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.window.WebFrame;
import com.alee.managers.notification.NotificationManager;
import com.meyling.zeubor.core.common.Player;
import com.meyling.zeubor.core.io.IoUtility;
import com.meyling.zeubor.core.physics.CalculatorUtility;
import com.meyling.zeubor.core.player.brain.AbstractBrainPlayer;
import com.meyling.zeubor.core.player.brain.BrainPlayerFromJson;
import com.meyling.zeubor.core.world.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


@SuppressWarnings("serial")
public class MainWindow6 extends JFrame {

    public static void main ( String[] args ) {
        // You should work with UI (including installing L&F) inside Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater (new Runnable () {
            public void run ()
            {
                // Install WebLaF as application L&F
                WebLookAndFeel.install();
//                WebLookAndFeel.setDecorateAllWindows (true);

                
                WebPanel panel = new WebPanel();
//                World world = new World(0, 10000, 10000, 5);
                World world = new World(0, 1000, 1000, 1);
                
                AbstractBrainPlayer player1 = new BrainPlayerFromJson(100, 100,  IoUtility.getStringData("ginny.json").data);
                world.add(player1);
                player1.getViewPoint().getPosition()[0] = 0.1;
                player1.getCamera().getPhotoPlateImpl().setSensitivity(11);
                CalculatorUtility.pointToZero(new double[3], player1.getViewPoint());
                                
                NewPlayerViewer viewer1 = new NewPlayerViewer(player1, world, true);
                world.addListener(viewer1);

                JComponent players1 = new GroupPanel(GroupingType.fillAll, 20, viewer1);


                panel.add(new GroupPanel(GroupingType.fillAll, 20, false, players1));

                panel.add(createStatusBar(world), BorderLayout.SOUTH);
                panel.setPreferredSize(new Dimension(820, 600));

                world.start();
                
                TestFrame.show(panel, 10);
            }
        });
    }
    

    public MainWindow6 () throws HeadlessException {
        super("Example frame");
        setIconImages(WebLookAndFeel.getImages());
        setDefaultCloseOperation ( WebFrame.DISPOSE_ON_CLOSE);

        new ComponentMoveBehavior(getRootPane (), this).install();
//        add( new BorderPanel(new WebPanel(new VerticalFlowLayout(10, 10))));
    }

    public static WebStatusBar createStatusBar(final World world) {

        // Window status bar
        final WebStatusBar statusBar = new WebStatusBar();
        
        final WebToggleButton toggle = new WebToggleButton("running", true);
        toggle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (toggle.isSelected()) {
                    toggle.setText("running");
                    world.start();
                } else {
                    toggle.setText("stopped");
                    world.stop();
                }
            }
        });

        statusBar.add(toggle); 
        statusBar.addSpacing();

        statusBar.add(new WebLabel("start new camera for: ") );

        for (final Player player : world.getPlayers()) {
            statusBar.addSpacing ();
            statusBar.add(new WebButton(player.getCamera().getViewPoint().getName(), new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    (new CameraWindow(world, player, 800, 800)).setVisible(true);
                }
            }));
        }
        statusBar.addToEnd(new WebButton("zero", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                (new CameraWindow(world, 800, 800)).setVisible(true);
            }
        }));
            
        statusBar.addToEnd(new WebButton("one", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                (new CameraWindow(world, new double[] {0, 0, -1}, 800, 800)).setVisible(true);
            }
        }));
            
        NotificationManager.setMargin ( 0, 0, statusBar.getPreferredSize ().height, 0 );


        return statusBar;
    }

}