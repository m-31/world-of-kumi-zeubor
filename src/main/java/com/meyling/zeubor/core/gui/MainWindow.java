package com.meyling.zeubor.core.gui;

import com.alee.extended.behavior.ComponentMoveBehavior;
import com.alee.extended.debug.TestFrame;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.GroupingType;
import com.alee.extended.statusbar.WebStatusBar;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.window.WebFrame;
import com.alee.managers.notification.NotificationManager;
import com.meyling.zeubor.core.gui_demo.FieldViewer;
import com.meyling.zeubor.core.physics.CalculatorUtility;
import com.meyling.zeubor.core.player.dumb.DumbPlayer;
import com.meyling.zeubor.core.world.World;

import javax.swing.*;
import java.awt.*;


@SuppressWarnings("serial")
public class MainWindow extends JFrame {

    public static void main ( String[] args ) {
        // You should work with UI (including installing L&F) inside Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater (new Runnable () {
            public void run ()
            {
                // Install WebLaF as application L&F
                WebLookAndFeel.install();
//                WebLookAndFeel.setDecorateAllWindows(true);

                // Create you Swing application here
//                MainWindow frame = new MainWindow();
//                frame.setVisible(true);
//                frame.pack();
//                frame.setSize(400,200);
                
                WebPanel panel = new WebPanel();
                final World world = new World();
                DumbPlayer player1 = new DumbPlayer("michael", 100, 100);
                world.add(player1);
                player1.getViewPoint().getPosition()[0] = 0.1;
                CalculatorUtility.pointToZero(new double[3], player1.getViewPoint());
                PlayerViewer viewer1 = new PlayerViewer(player1);
                world.addListener(viewer1);
//                viewer1.setSize(new Dimension(200, 200));

                DumbPlayer player2 = new DumbPlayer("mule",100, 100);
                world.add(player2);
                player2.getViewPoint().getPosition()[1] = 0.1;
                CalculatorUtility.pointToZero(new double[3], player2.getViewPoint());
                PlayerViewer viewer2 = new PlayerViewer(player2);
                world.addListener(viewer2);
//                viewer2.setSize(new Dimension(200, 200));

                JComponent players = new GroupPanel(GroupingType.fillAll, 20, viewer1, viewer2);
//                panel.add(players, BorderLayout.NORTH);

                
                FieldViewer field1 = new FieldViewer(world);
                field1.init();
                field1.getViewPoint().setColor(Color.blue.getRGB());
                field1.getViewPoint().getPosition()[2] = 0.3;
                field1.getViewPoint().setName("Camera 1");
                CalculatorUtility.pointToZero(new double[3], field1.getViewPoint());
                field1.start();
                FieldViewer field2 = new FieldViewer(world);
                field2.init();
                field2.getViewPoint().setColor(Color.magenta.getRGB());
                field2.getViewPoint().getPosition()[2] = -0.3;
                field2.getViewPoint().setName("Camera 2");
                CalculatorUtility.pointToZero(new double[3], field2.getViewPoint());
                field2.start();
                
                JComponent cameras = new GroupPanel(GroupingType.fillAll, 20, field1, field2);
                
                panel.add(new GroupPanel(GroupingType.fillAll, 20, false, players, cameras));
//                panel.add(cameras, BorderLayout.CENTER);

                panel.add(createStatusBar(), BorderLayout.SOUTH);
                panel.setPreferredSize(new Dimension(820, 600));
                
                world.start();
                
                TestFrame.show(panel, 10);
                TestFrame.show(CameraGenerator.generatePanel(world, field1.getViewPoint(), 900, 900), 10);
            }
        });
    }
    

    public MainWindow () throws HeadlessException {
        super("Example frame");
        setIconImages(WebLookAndFeel.getImages());
        setDefaultCloseOperation ( WebFrame.DISPOSE_ON_CLOSE);

        new ComponentMoveBehavior(getRootPane(), this).install();
//        ComponentMoveAdapter.install ( getRootPane (), this);
//        add( new BorderPanel(new WebPanel(new VerticalFlowLayout(10, 10))));
    }

    public static WebStatusBar createStatusBar() {
        // Window status bar
        final WebStatusBar statusBar = new WebStatusBar ();

        statusBar.add(new WebLabel("hallo") );

        statusBar.addSpacing ();

        statusBar.add(new WebLabel("michael") );
        
        statusBar.addToEnd(new WebLabel("meyling"));

        NotificationManager.setMargin ( 0, 0, statusBar.getPreferredSize ().height, 0 );


        return statusBar;
    }

}