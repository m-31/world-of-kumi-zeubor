package com.meyling.zeubor.core.gui;

import com.alee.extended.behavior.ComponentMoveBehavior;
import com.alee.extended.debug.TestFrame;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.GroupingType;
import com.alee.extended.statusbar.WebStatusBar;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.window.WebFrame;
import com.alee.managers.notification.NotificationManager;
import com.meyling.zeubor.core.common.ViewPoint;
import com.meyling.zeubor.core.physics.CalculatorUtility;
import com.meyling.zeubor.core.player.dumb.DumbPlayer;
import com.meyling.zeubor.core.world.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


@SuppressWarnings("serial")
public class MainWindow2 extends JFrame {

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
                
                DumbPlayer player1 = new DumbPlayer("michael", 100, 100);
                world.add(player1);
                player1.getViewPoint().getPosition()[0] = 0.1;
                CalculatorUtility.pointToZero(new double[3], player1.getViewPoint());
                PlayerViewer viewer1 = new PlayerViewer(player1);
                world.addListener(viewer1);

                DumbPlayer player2 = new DumbPlayer("mule", 100, 100);
                world.add(player2);
                player2.getViewPoint().getPosition()[1] = 0.1;
                CalculatorUtility.pointToZero(new double[3], player2.getViewPoint());
                PlayerViewer viewer2 = new PlayerViewer(player2);
                world.addListener(viewer2);

                JComponent players = new GroupPanel(GroupingType.fillAll, 20, viewer1, viewer2);

                ViewPoint camera1 = new ViewPoint("Camera 1");
                camera1.setColor(Color.blue.getRGB());
                camera1.getPosition()[2] = 0.3;
                CalculatorUtility.pointToZero(new double[3], camera1);
                world.add(camera1);
                LightObjectFieldViewer field1 = new LightObjectFieldViewer(camera1, world);

                ViewPoint camera2 = new ViewPoint("Camera 2");
                camera2.setColor(Color.magenta.getRGB());
                camera2.getPosition()[2] = -0.3;
                CalculatorUtility.pointToZero(new double[3], camera2);
                world.add(camera2);
                LightObjectFieldViewer field2 = new LightObjectFieldViewer(camera2, world);

                JComponent cameras = new GroupPanel(GroupingType.fillAll, 20, field1, field2);

                panel.add(new GroupPanel(GroupingType.fillAll, 20, false, players, cameras));

                panel.add(createStatusBar(world), BorderLayout.SOUTH);
                panel.setPreferredSize(new Dimension(820, 600));

                world.start();
                
                TestFrame.show(panel, 10);
            }
        });
    }
    

    public MainWindow2 () throws HeadlessException {
        super("Example frame");
        setIconImages(WebLookAndFeel.getImages());
        setDefaultCloseOperation ( WebFrame.DISPOSE_ON_CLOSE);

        new ComponentMoveBehavior(getRootPane (), this).install();
        // add( new BorderPanel(new WebPanel(new VerticalFlowLayout(10, 10))));
    }

    public static WebStatusBar createStatusBar(final World world) {

        // Window status bar
        final WebStatusBar statusBar = new WebStatusBar ();

        statusBar.add(new WebLabel("start camera for: ") );

        for (final ViewPoint viewPoint : world.getViewPoints()) {
            statusBar.addSpacing ();
            statusBar.add(new WebButton(viewPoint.getName(), new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    CameraGenerator.generateWindow(world, viewPoint, 900, 900).setVisible(true);
                }
            }));
        }
        statusBar.addToEnd(new WebButton("meyling"));

        NotificationManager.setMargin ( 0, 0, statusBar.getPreferredSize ().height, 0 );


        return statusBar;
    }

}