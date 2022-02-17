package com.meyling.zeubor.core.gui;

import com.meyling.zeubor.core.common.ObjectWithCamera;
import com.meyling.zeubor.core.common.PhotoPlateAttributes;
import com.meyling.zeubor.core.common.ViewPoint;
import com.meyling.zeubor.core.physics.PhotoPlateImpl;
import com.meyling.zeubor.core.world.CameraImpl;
import com.meyling.zeubor.core.world.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class LightObjectFieldViewer extends JPanel implements MouseListener, MouseMotionListener,
        MouseWheelListener, ObjectWithCamera, Runnable {
    private final ViewPoint viewPoint;
    private final World world;
    private final ManualMovement positionCalculator;
    private final CameraImpl camera;
    private final PhotoPlateImpl photoPlate;
    private Thread runThread;
    private boolean drag = false;
    private int prevx;
    private int prevy;
    private double ytheta;
    private double xtheta;
    private double ztheta;

    private boolean threadSuspended;
    private boolean button3;


    public LightObjectFieldViewer(final ViewPoint viewPoint, final World world,
            final double sensitivity, final double zoom, final int snapshot,
            final int width, final int height) {
        this.viewPoint = viewPoint;
        this.world = world;
        photoPlate = new PhotoPlateImpl();
        positionCalculator = new ManualMovement();
        photoPlate.setSensitivity(sensitivity);
        photoPlate.setZoom(zoom);
        photoPlate.setSnapshot(snapshot);
        photoPlate.init(width, height);
        camera = new CameraImpl(photoPlate, viewPoint);
        setDoubleBuffered(true);
        start();
    }

    public LightObjectFieldViewer(final ViewPoint viewPoint, final World world,
            final int width, final int height) {
        this.viewPoint = viewPoint;
        this.world = world;
        photoPlate = new PhotoPlateImpl();
        positionCalculator = new ManualMovement();
        photoPlate.init(width, height);
        camera = new CameraImpl(photoPlate, viewPoint);
        setDoubleBuffered(true);
        start();
    }

    public LightObjectFieldViewer(final ViewPoint viewPoint, final World world) {
        this(viewPoint, world, 0, 0);
    }
    
    public final CameraImpl getCamera() {
    	return camera;
    }

    public final void resize(final int width, final int height) {
        camera.getPhotoPlateImpl().init(width, height);
    }

    public final void applyVisualChanges(final PhotoPlateAttributes properties) {
        applyVisualChanges(properties.getSensitivity(), properties.getZoom(), properties.getSnapshot());
    }
    
    public final void applyVisualChanges(final double sensitivity, 
            final double zoom, final int snapshot) {
        camera.getPhotoPlate().setSensitivity(sensitivity);
        camera.getPhotoPlate().setZoom(zoom);
        camera.getPhotoPlate().setSnapshot(snapshot);
        takePicture();
        paint(getGraphics());
    }

    /**
     * Move viewpoint.
     */
    public final void move() {
        positionCalculator.move(camera);
    }

    public final ManualMovement getPositionCalculator() {
        return positionCalculator;
    }

    public final void takePicture() {
        world.takePicture(camera);
    }

    public final ViewPoint getViewPoint() {
        return camera.getViewPoint();
    }

    public final void setSensitivity(final double sensitivity) {
        System.out.println("setting sensitivity to " + sensitivity);
        camera.getPhotoPlate().setSensitivity(sensitivity);
    }

    public final double getSensitivity() {
        return camera.getPhotoPlate().getSensitivity();
    }

    public final void setZoom(final double zoom) {
        camera.getPhotoPlate().setZoom(zoom);
    }

    public final double getZoom() {
        return camera.getPhotoPlate().getZoom();
    }

    public final void setSnapshot(final int snapshot) {
        camera.getPhotoPlate().setSnapshot(snapshot);
    }

    private void init() {
        drag = false;
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        addComponentListener(new ComponentAdapter() {
            public void componentResized(final ComponentEvent e) {
               resize(getWidth(), getHeight());
            }
        });
        threadSuspended = false;
    }

    private void destroy() {
        removeMouseListener(this);
        removeMouseMotionListener(this);
        removeMouseWheelListener(this);
    }

    public final void paint(Graphics g) {
//        System.out.println("LightObjectFieldViewer.paint start");
        // super.paint(g);
        PhotoPlateImpl r = camera.getPhotoPlateImpl();
        if (g != null && r.isInitialized()) {
//            System.out.println("LightObjectFieldViewer.paint");
            g.drawImage(r.getPictureImpl().getImage(), 0, 0, this);
            g.setColor(Color.yellow);
            g.drawRect((r.getPicture().getWidth() - 100) / 2, (r.getPicture().getHeight() -  100) / 2, 100, 100);
//            g.setColor(Color.blue);
//            g.drawRect((r.getPicture().getWidth() - 100) / 2 + 100, (r.getPicture().getHeight() -  100) / 2 + 100, 100, 100);
        } else {
            System.out.println("LightObjectFieldViewer.paint: could not draw because g is null or r.isInitialized is false");
        }
//        System.out.println("LightObjectFieldViewer.paint end");
    }

    public final void start() {
        init();
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
        destroy();
        System.out.println("stop Thread stopped");
    }

    public final boolean isRunning() {
        return runThread != null;
    }

    public final void run() {
        try {
            while (null != runThread) {
//                System.out.println("LightOjectFieldViewer.run.step start " + System.currentTimeMillis());
                move();
//                System.out.println("LightOjectFieldViewer.run.step moved " + System.currentTimeMillis());
                takePicture();
//                System.out.println("LightOjectFieldViewer.run.step picture taken " + System.currentTimeMillis());
                // invalidate();
                // repaint(this.getVisibleRect());
                // repaint(new Rectangle(getWidth(), getHeight()));
                repaint();
//                final Graphics graphics = getGraphics();
//                if (graphics != null) {
//                    paint(graphics);
//                }
//                System.out.println("LightOjectFieldViewer.run.step before sleep " + System.currentTimeMillis());
                try {
                    Thread.sleep(30);
                    synchronized (this) {
                        while (threadSuspended) {
                            wait();
                        }
                    }
                } catch (InterruptedException e) {
                }
//                System.out.println("LightOjectFieldViewer.run.step after sleep " + System.currentTimeMillis());

            }
            System.out.println("LightObjectFieldViewer.run.while ended");
        } catch (Exception e) {
            System.out.println("run " + e);
            e.printStackTrace();
        }
    }

    /* event handling */
    public final void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            positionCalculator.pointToZero(viewPoint);
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
        int x = e.getX();
        int y = e.getY();
        if (x == prevx && y == prevy) {
            return;
        }
        final double maxx = getWidth();
        final double maxy = getHeight();
//        final double max = Math.max(maxx, maxy);
        double dx1 = prevx - maxx / 2;
        double dx2 = x - maxx / 2;
        double dy1 = prevy - maxy / 2;
        double dy2 = y - maxy / 2;
        xtheta = Math.atan(dx2 / getZoom()) - Math.atan(dx1 /getZoom());
        ytheta = Math.atan(dy2 / getZoom()) - Math.atan(dy1 /getZoom());
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
        move();
//        takePicture(field);
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
            if (!button3 && InputEvent.BUTTON3_DOWN_MASK != (e.getModifiersEx()
                    & InputEvent.BUTTON3_DOWN_MASK)) {
                if (e.getUnitsToScroll() < 0) {
                    positionCalculator.setDelta(positionCalculator.getDelta() - 0.0001);
                } else {
                    positionCalculator.setDelta(positionCalculator.getDelta() + 0.0001);
                }
            } 
            if (button3) {
                // calculate sensitivity
                final double s;
                if (e.getUnitsToScroll() < 0) {
                    s = camera.getPhotoPlate().getSensitivity() + 2 * Math.log(1.05);
                } else {
                    s = camera.getPhotoPlate().getSensitivity() - 2 * Math.log(1.05);
                }
                if (!Double.isInfinite(s)) {
                    camera.getPhotoPlate().setSensitivity(s);
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
//        takePicture();
        move();
        e.consume();
    }

    public final void setXtheta(final double xtheta) {
        getPositionCalculator().setXtheta(xtheta);
    }

    public final void setYtheta(final double ytheta) {
        getPositionCalculator().setYtheta(ytheta);
    }


}
