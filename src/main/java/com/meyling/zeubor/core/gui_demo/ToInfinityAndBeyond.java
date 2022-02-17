package com.meyling.zeubor.core.gui_demo;


import com.meyling.zeubor.core.world.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * Info window.
 */
@SuppressWarnings("serial")
public class ToInfinityAndBeyond extends JFrame {

    private final FieldViewer field;


    public ToInfinityAndBeyond() {
        setBackground(Color.black);
        setPreferredSize(getToolkit().getScreenSize());
        setResizable(false);
        setUndecorated(true);
        setAlwaysOnTop(true);
        this.setLayout(null);
        pack();
        addNotify();

        World world = new World();
        field = new FieldViewer(world);
        field.setSize(getToolkit().getScreenSize());
        this.add(field);

        field.setSize(getToolkit().getScreenSize());

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                field.stop();
                dispose();
                System.exit(0);
            }
        });
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (KeyEvent.VK_ESCAPE == e.getKeyCode()) {
                    field.stop();
                    dispose();
                    System.exit(0);
                }
            }
        });
        field.init();
        field.start();
        this.repaint();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
              Point p = new Point(0, 0);
              SwingUtilities.convertPointToScreen(p, getContentPane());
              Point location = getLocation();
              location.x -= p.x;
              location.y -= p.y;
              setLocation(location);
            }
        });
    }

    public static final void main(final String args[]) {
        try {
            final ToInfinityAndBeyond infinity = new ToInfinityAndBeyond();
            infinity.setVisible(true);
        } catch (Exception e){
            e.printStackTrace();
        }
    }



 }
