package com.meyling.zeubor.core.gui;

import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.GroupingType;
import com.alee.extended.statusbar.WebStatusBar;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.slider.WebSlider;
import com.alee.laf.text.WebTextField;
import com.alee.laf.window.WebFrame;
import com.alee.managers.notification.NotificationManager;
import com.meyling.zeubor.core.common.Movement;
import com.meyling.zeubor.core.common.Player;
import com.meyling.zeubor.core.common.ViewPoint;
import com.meyling.zeubor.core.common.WorldListener;
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


@SuppressWarnings("serial")
public class CameraWindow extends WebFrame implements WorldListener {

    private static final int MAX_SLIDER = 10000;


    private static int counter;
    private WebTextField algae = new WebTextField("", 5);
    private WebTextField amount = new WebTextField("1", 3);
    private World world;
    private LightObjectFieldViewer viewer;
    private Player player;
    private boolean newViewpoint = false;

    
    public CameraWindow(final World world, final Player player, int width, int height) {
        super(player.getCamera().getViewPoint().getName());
        this.player = player;
        init(world, player.getCamera().getViewPoint(), width, height);
        viewer.setZoom(player.getCamera().getPhotoPlate().getZoom());
//      System.out.println(player.getViewPoint().getName() + " zoom: " + viewer.getZoom());
        viewer.setSensitivity(player.getCamera().getPhotoPlate().getSensitivity());
        viewer.setSnapshot(player.getCamera().getPhotoPlate().getSnapshot());
    }

    public CameraWindow(final World world, int width, int height) {
        super("Camera " + ++counter);
        final ViewPoint viewPoint = new ViewPoint("Camera " + counter);
        newViewpoint = true;
        viewPoint.setColor(Color.yellow.getRGB());
        world.add(viewPoint);
        init(world, viewPoint, width, height);
        
    }

    public CameraWindow(final World world, double[] position, int width, int height) {
        super("Camera " + ++counter);
        final ViewPoint viewPoint = new ViewPoint("Camera " + counter);
        newViewpoint = true;
        viewPoint.getPosition()[0] = position[0];
        viewPoint.getPosition()[1] = position[1];
        viewPoint.getPosition()[2] = position[2];
        viewPoint.setColor(Color.yellow.getRGB());
        world.add(viewPoint);
        init(world, viewPoint, width, height);
    }

    private void init(final World world, final ViewPoint viewPoint, int width, int height) {
        this.world = world;
        this.viewer = new LightObjectFieldViewer(viewPoint, world);
        world.addListener(this);
        algae = new WebTextField("" + world.getAlgaeNumber(), 5);
        setLayout(new BorderLayout(10, 10));
        setResizable(true);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                viewer.stop();
                if (newViewpoint) {
                	world.remove(viewPoint);
                }
            }
            public void windowClosed(WindowEvent e) {
                viewer.stop();
                if (newViewpoint) {
                	world.remove(viewPoint);
                }
            }
        });
        // window.setCloseOnFocusLoss(true); // but this dont calls windowClosing event!!
        setSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
        final WebSlider slider = new WebSlider(WebSlider.VERTICAL, 0, MAX_SLIDER, getZoom());
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                System.out.println(slider.getValue());
                setZoom(slider.getValue());
            }
        });
        WebPanel panel1 = new GroupPanel(GroupingType.fillLast, 10, slider, viewer);
        WebPanel panel2 = new WebPanel(panel1);
        panel2.setMargin(20);
        panel2.add(createStatusBar(viewPoint, world), BorderLayout.SOUTH);
        add(panel2);
        System.out.println("window generated");
    }
    
    public void setZoom(int value) {
        viewer.setZoom(slider2zoom(value));
    }

    public int getZoom() {
        int r = (int) zoom2slider(viewer.getZoom());
        System.out.println(viewer.getZoom());
        System.out.println(r);
        return r;
    }
    
    private int getAmount() {
        try {
            return new Integer(amount.getText()).intValue();
        } catch (Exception e) {
            return 1;
        }
    }

    private double zoom2slider(double zoom) {
//        return (Math.log(zoom / 2033) + 2.7) * MAX_SLIDER  / 4;
        return (Math.log(zoom / 2033) + 4) * MAX_SLIDER  / 4;
    }
    
    private double slider2zoom(int value) {
//        return 2033 * Math.exp(4d * value / MAX_SLIDER - 2.7);
        return 2033 * Math.exp(4d * value / MAX_SLIDER - 4);
    }
    
    public WebStatusBar createStatusBar(final ViewPoint viewPoint, final World world) {
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
        statusBar.addSpacing();
        statusBar.add(new WebButton("zero", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CalculatorUtility.pointToZero(new double[3], viewPoint);
            }
        }));

        statusBar.add(new WebLabel("  go ") );
        statusBar.add(amount);
        statusBar.addSpacing();
        statusBar.add(new WebButton("<", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	for (int i = 0; i < getAmount(); i++) {
            		world.move(viewer, new Movement(-1, 0, 0));
            	}
            }
        }));
        statusBar.add(new WebButton(">", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	for (int i = 0; i < getAmount(); i++) {
            		world.move(viewer, new Movement(1, 0, 0));
            	}
            }
        }));
        statusBar.add(new WebButton("^", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	for (int i = 0; i < getAmount(); i++) {
            		world.move(viewer, new Movement(0, 1, 0));
            	}
            }
        }));
        statusBar.add(new WebButton("v", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	for (int i = 0; i < getAmount(); i++) {
            		world.move(viewer, new Movement(0, -1, 0));
            	}
            }
        }));
        statusBar.add(new WebButton("+", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	for (int i = 0; i < 100; i++) {
            		world.move(viewer, new Movement(0, 0, 1));
            	}
            }
        }));
        statusBar.add(new WebButton("-", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 100; i++) {
                    world.move(viewer, new Movement(0, 0, -1));
                }
            }
        }));

        statusBar.addToEnd(new WebLabel("algae "));
        statusBar.addToEnd(algae);
        NotificationManager.setMargin ( 0, 0, statusBar.getPreferredSize ().height, 0 );
        return statusBar;
    }

    public void newIteration() {
        if (null != player) {
            algae.setText("" + player.getAlgae());
        } else {
            algae.setText("" + world.getAlgaeNumber());
        }
    }

    public static void main(final String[] args) {
        final World world = new World();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Install WebLaF as application L&F
                WebLookAndFeel.install();

                (new CameraWindow(world, 800, 800)).setVisible(true);
            }
        });
    }

}