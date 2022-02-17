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
import com.meyling.zeubor.core.physics.CalculatorUtility;
import com.meyling.zeubor.core.player.AbstractPlayer;
import com.meyling.zeubor.core.player.brain.AbstractBrainPlayer;
import com.meyling.zeubor.core.player.brain.BrainPlayer10;
import com.meyling.zeubor.core.player.brain.BrainPlayer5;
import com.meyling.zeubor.core.player.brain.BrainPlayer7;
import com.meyling.zeubor.core.world.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


@SuppressWarnings("serial")
public class MainWindow3 extends JFrame {

    public static void main ( String[] args ) {
        // You should work with UI (including installing L&F) inside Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater (new Runnable () {
            public void run ()
            {
                // Install WebLaF as application L&F
                WebLookAndFeel.install();
//                WebLookAndFeel.setDecorateAllWindows (true);

                
                WebPanel panel = new WebPanel();
                World world = new World();
                
                AbstractPlayer player1 = new BrainPlayer5("Dorian", 100, 100);
                world.add(player1);
                player1.getViewPoint().getPosition()[0] = 0.1;
                CalculatorUtility.pointToZero(new double[3], player1.getViewPoint());
                PlayerViewer viewer1 = new PlayerViewer(player1);
                world.addListener(viewer1);

                AbstractPlayer player2 = new BrainPlayer7(100, 100);
                world.add(player2);
                player2.getViewPoint().getPosition()[1] = 0.1;
                CalculatorUtility.pointToZero(new double[3], player2.getViewPoint());
                PlayerViewer viewer2 = new PlayerViewer(player2);
                world.addListener(viewer2);

                JComponent players1 = new GroupPanel(GroupingType.fillAll, 20, viewer1, viewer2);

                AbstractBrainPlayer player3 = new BrainPlayer10(100, 100);
                world.add(player3);
                player3.getViewPoint().getPosition()[0] = - 0.1;
                CalculatorUtility.pointToZero(new double[3], player3.getViewPoint());
                PlayerViewer viewer3 = new PlayerViewer(player3);
                world.addListener(viewer3);

                NewPlayerViewer viewer4 = new NewPlayerViewer(player3, world);
                world.addListener(viewer4);

                JComponent players2 = new GroupPanel(GroupingType.fillAll, 20, viewer3, viewer4);

                panel.add(new GroupPanel(GroupingType.fillAll, 20, false, players1, players2));

                panel.add(createStatusBar(world), BorderLayout.SOUTH);
                panel.setPreferredSize(new Dimension(820, 600));
//                world.iterate();
                world.start();
                
                TestFrame.show(panel, 10);
            }
        });
    }
    

    public MainWindow3 () throws HeadlessException {
        super("Example frame");
        setIconImages(WebLookAndFeel.getImages());
        setDefaultCloseOperation(WebFrame.DISPOSE_ON_CLOSE);

        new ComponentMoveBehavior(getRootPane (), this).install();
        // add( new BorderPanel(new WebPanel(new VerticalFlowLayout(10, 10))));
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