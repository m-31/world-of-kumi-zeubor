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

import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.GroupingType;
import com.alee.extended.statusbar.WebStatusBar;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.slider.WebSlider;
import com.alee.laf.window.WebFrame;
import com.alee.managers.notification.NotificationManager;
import com.meyling.zeubor.core.common.ViewPoint;
import com.meyling.zeubor.core.physics.CalculatorUtility;
import com.meyling.zeubor.core.world.World;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class CameraGenerator {

    public static JPanel generatePanel(final World world, final ViewPoint viewPoint, int width, int height) {
        LightObjectFieldViewer viewer = new LightObjectFieldViewer(viewPoint, world, width, height);
        WebPanel panel = new GroupPanel(GroupingType.fillAll, 20, viewer);
//        panel.setSize(new Dimension(width, height));
        panel.setPreferredSize(new Dimension(width, height));
        return panel;
    }

    public static JFrame generateWindow(final World world, final ViewPoint viewPoint, int width, int height) {
        final LightObjectFieldViewer viewer = new LightObjectFieldViewer(viewPoint, world);
        WebFrame window = new WebFrame(viewPoint.getName());
        window.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                viewer.stop();
            }
            public void windowClosed(WindowEvent e) {
                viewer.stop();
            }
        });
        window.setLayout(new BorderLayout(10, 10));
        window.setResizable(true);
        // window.setCloseOnFocusLoss(true); // but this dont calls windowClosing event!!
        window.setSize(new Dimension(width, height));
        window.setPreferredSize(new Dimension(width, height));
        final WebSlider slider = new WebSlider(WebSlider.VERTICAL, 0, 10000, getZoom(10000, viewer));
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                System.out.println(slider.getValue());
                setZoom(10000, slider.getValue(), viewer);
            }
        });
        WebPanel panel1 = new GroupPanel(GroupingType.fillLast, 10, slider, viewer);
        WebPanel panel2 = new WebPanel(panel1);
        panel2.setMargin(20);
        panel2.add(createStatusBar(viewPoint, world), BorderLayout.SOUTH);
        window.add(panel2);
        System.out.println("window generated");
        return window;
    }
    
    public static void setZoom(int max, int value, LightObjectFieldViewer viewer) {
        double r = 2033 * Math.exp(4d * value / max - 2.7);
        viewer.setZoom(r);
    }
    
    public static int getZoom(int max, LightObjectFieldViewer viewer) {
        double r = (Math.log(viewer.getZoom()) / 2033 + 2.7) * max  / 4;
        return (int) r;
    }
    
    public static WebStatusBar createStatusBar(final ViewPoint viewPoint, final World world) {
        // Window status bar
        final WebStatusBar statusBar = new WebStatusBar();
        statusBar.add(new WebLabel("turn to ") );
        for (final ViewPoint vp : world.getViewPoints()) {
            if (viewPoint.equals(vp)) {
                continue;
            }
            statusBar.addSpacing ();
            statusBar.add(new WebButton(vp.getName(), new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    CalculatorUtility.pointToZero(vp.getPosition(), viewPoint);
                }
            }));
        }
        statusBar.addSpacing ();
        statusBar.add(new WebButton("zero", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CalculatorUtility.pointToZero(new double[3], viewPoint);
            }
        }));

        statusBar.addToEnd(new WebLabel("meyling"));
        NotificationManager.setMargin ( 0, 0, statusBar.getPreferredSize ().height, 0 );
        return statusBar;
    }


}