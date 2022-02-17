package com.meyling.zeubor.core.gui_demo;

import com.meyling.zeubor.core.common.PhotoPlateAttributes;
import com.meyling.zeubor.core.common.ViewPoint;
import com.meyling.zeubor.core.physics.ManualMovement;
import com.meyling.zeubor.core.world.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * Star field viewer.
 */
@SuppressWarnings("serial")
public class FieldViewer extends JPanel implements Runnable,
        MouseListener, MouseMotionListener, MouseWheelListener {

    private World world;
    private Thread runThread;
    private boolean drag = false;
    private int prevx;
    private int prevy;
    private double ytheta;
    private double xtheta;
    private double ztheta;

    private SimulatorViewer viewer;
    private boolean threadSuspended;
    private boolean button3;


    public FieldViewer(final World world, final ViewPoint viewPoint, final PhotoPlateAttributes properties) {
        super();
        this.world = world;
        viewer = new SimulatorViewer(world, viewPoint, properties, getWidth(), getHeight());
    }

    public FieldViewer(final World world) {
        super();
        this.world = new World();
        this.viewer = null;
    }

    public void init() {
        drag = false;
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        addComponentListener(new ComponentAdapter() {
            public void componentResized(final ComponentEvent e) {
               if (viewer != null) {
                   viewer.resize(getWidth(), getHeight());
               }
            }
        });
        threadSuspended = false;
        double heightAngle = 20; // degree of screen height of your monitor
        double zoom = getToolkit().getScreenSize().height/2/Math.tan(Math.PI * heightAngle / 360);
        viewer = new SimulatorViewer(world, new ViewPoint(), new PhotoPlateAttributes(13, zoom, 0), getWidth(),
            getHeight());
        this.world.add(viewer.getViewPoint());
    }

    public final void destroy() {
        removeMouseListener(this);
        removeMouseMotionListener(this);
        removeMouseWheelListener(this);
        this.world.remove(viewer.getViewPoint());
        viewer.close();
        this.viewer = null;
    }

    public final void resize(final Dimension d) {
        resize(d.width, d.height);
    }

    public final void resize(int width, int height) {
        System.out.println("resize to " + width + ", " + height);
        super.resize(width, height);
        if (viewer != null) {
            viewer.resize(width, height);
        }
    }

    public ViewPoint getViewPoint() {
        return (viewer != null ? viewer.getViewPoint() : null);
    }

    public final void applyVisualChanges(final PhotoPlateAttributes properties) {
        viewer.applyVisualChanges(properties);
        paint(getGraphics());
    }

    public final void paint(Graphics g) {
        if (viewer != null) {
            viewer.paintPicture(g);
        }
    }

    public final void start() {
        if (runThread == null) {
            runThread = new Thread(this);
            runThread.start();
        }
        System.out.println("start  Thread started");
    }

    public final synchronized void stop() {
        runThread = null;
        if (threadSuspended) {
            threadSuspended = false;
            notify();
        }
        System.out.println("stop Thread stopped");
    }

    public final boolean isRunning() {
        return runThread != null;
    }

    public final void run() {
        try {
            while (null != runThread) {
                viewer.move();
                viewer.takePicture();
                repaint();
                try {
                    Thread.sleep(30);
                    synchronized (this) {
                        while (threadSuspended) {
                            wait();
                        }
                    }
                } catch (InterruptedException e) {
                }

            }
        } catch (Exception e) {
            System.out.println("run " + e);
            e.printStackTrace();
        }
    }

    /* event handling */
    public final void mouseClicked(MouseEvent e) {
        ManualMovement positionCalculator = viewer.getPositionCalculator();
        if (e.getButton() == MouseEvent.BUTTON3) {
            positionCalculator.pointToZero(viewer.getViewPoint());
        }
        if (e.getButton() != 1) {
            return;
        }
        positionCalculator.setXtheta(0);
        positionCalculator.setYtheta(0);
        positionCalculator.setZtheta(0);
        positionCalculator.setDelta(0);
    }

    public final void mousePressed(MouseEvent e) {
//        prevx = e.getX();
//        prevy = e.getY();
        System.out.println("x: " + e.getX() + "  y: "  + e.getY());
        if (MouseEvent.BUTTON3 == (e.getButton() & MouseEvent.BUTTON3)) {
            System.out.println("button3 on");
            button3 = true;
        }
        e.consume();
    }

    public final void mouseReleased(MouseEvent e) {
        if (MouseEvent.BUTTON3 == (e.getButton() & MouseEvent.BUTTON3)) {
            System.out.println("button3 of");
            button3 = false;
        }
        e.consume();
    }

    public final void mouseEntered(MouseEvent e) {
        e.consume();
    }

    public final void mouseExited(MouseEvent e) {
        e.consume();
    }

    public final void mouseDragged(MouseEvent e) {
        e.consume();
        if (InputEvent.BUTTON1_DOWN_MASK != (e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK)) {
            drag = false;
            System.out.println("drag end 2");
            return;
        }
        if (!drag) {
            drag = true;
            prevx = e.getX();
            prevy = e.getY();
            System.out.println("drag start");
            return;
        }
        final ManualMovement positionCalculator = viewer.getPositionCalculator();
        final double maxx = getWidth();
        final double maxy = getHeight();
        int x = e.getX();
        int y = (int) maxy - e.getY();
        if (x == prevx && y == prevy) {
            return;
        }
//        final double max = Math.max(maxx, maxy);
        double dx1 = prevx - maxx / 2;
        double dx2 = x - maxx / 2;
        double dy1 = prevy - maxy / 2;
        double dy2 = y - maxy / 2;
        xtheta = Math.atan(dx2 / viewer.getZoom()) - Math.atan(dx1 /viewer.getZoom());
        ytheta = Math.atan(dy2 / viewer.getZoom()) - Math.atan(dy1 /viewer.getZoom());
        double d2 = Math.sqrt((dx1 * dx1 + dy1 * dy1) * (dx2 * dx2 + dy2 * dy2));
//        System.out.println("d2=" + d2);
//        System.out.println("d2=" + d2 + " dx1=" + dx1 + " dx2=" + dx2 + " dy1=" + dy1 + " dy2=" + dy2);
        ztheta = Math.asin((dx2 * dy1 - dy2 * dx1) / d2);
//        System.out.println("ztheta=" + ztheta);
//        System.out.println("ztheta2=" + Math.asin(ztheta));
//        System.out.println("ztheta3=" + 180 / Math.PI * Math.asin(ztheta));
        // FIXME a little hard ...
        if (d2 < maxx * maxy / 8) {
            positionCalculator.setXtheta(xtheta);
            positionCalculator.setYtheta(ytheta);
        } else {
            positionCalculator.setZtheta(ztheta);
        }
        viewer.move();
//        viewer.takePicture(field);
        if (false) {                    // FIXME
            positionCalculator.setXtheta(0);
            positionCalculator.setYtheta(0);
            positionCalculator.setZtheta(0);
        }
        prevx = x;
        prevy = y;
    }

    public final void mouseMoved(MouseEvent e) {
        e.consume();
        if (drag) {
            System.out.println("stop");
        }
        drag = false;
    }

    public final void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
            ManualMovement r = viewer.getPositionCalculator();
            if (!button3 && InputEvent.BUTTON3_DOWN_MASK != (e.getModifiersEx()
                    & InputEvent.BUTTON3_DOWN_MASK)) {
                if (e.getUnitsToScroll() < 0) {
                    viewer.setDelta(r.getDelta() - 0.0001);
                } else {
                    viewer.setDelta(r.getDelta() + 0.0001);
                }
            } 
            if (button3) {
                // calculate sensitivity
                final double s;
                if (e.getUnitsToScroll() < 0) {
                    s = viewer.getSensitivity() + 2 * Math.log(1.05);
                } else {
                    s = viewer.getSensitivity() - 2 * Math.log(1.05);
                }
                if (!Double.isInfinite(s)) {
                    viewer.setSensitivity(s);
                }
                /* calculate zoom factor
                final double s;
                if (e.getUnitsToScroll() < 0) {
                    viewer.setZoom(viewer.getZoom() * 1.1);
                } else {
                    viewer.setZoom(viewer.getZoom() / 1.1);
                }
                */
            }
        }
        viewer.move();
        e.consume();
    }

    public final PhotoPlateAttributes getProperties() {
        return viewer.getProperties();
    }

    public final void setXtheta(final double xtheta) {
        viewer.getPositionCalculator().setXtheta(xtheta);
    }

    public final void setYtheta(final double ytheta) {
        viewer.getPositionCalculator().setYtheta(ytheta);
    }

}


