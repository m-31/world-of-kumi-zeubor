package com.meyling.zeubor.core.world;

import com.meyling.zeubor.core.common.*;
import com.meyling.zeubor.core.log.Log;
import com.meyling.zeubor.core.physics.*;
import com.meyling.zeubor.core.player.AbstractPlayer;
import com.meyling.zeubor.core.player.brain.AbstractBrainPlayer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A world contains a landscape and players.
 * Currently, the landscape is made of stars.
 * Later on it might have a terrain, sun, temperature, etc.
 */
public class World implements Runnable {
    
    private final long max_time;
    
    private final double factor;
    
    private long time;
    
    private final LightObjectField field;
    
    private final LightObjectFieldCollisionDetector collisionDetector;
    
    private final List<ViewPoint> viewPoints;
    
    private final List<AbstractPlayer> players;
    
    private final List<WorldListener> listeners;

    private Thread runThread;

	private int lastAlgae;

	public static boolean debug = false;  // FIXME
    

    public World(final long max_time, final int balls, final int squares, final double factor) {
        Log.journal(String.format("world with %d algae (with %d in cube) and factor %f", balls + squares, squares, factor));
        this.max_time = max_time;
        this.factor = factor;
        listeners = new ArrayList<WorldListener>();
        field = new LightObjectField();
        collisionDetector = new LightObjectFieldCollisionDetector();
        final LightObjectFieldFiller filler = new LightObjectFieldFiller(field, collisionDetector);
        filler.fillBall(balls);
        filler.fillSquare(squares, 0.1);
        viewPoints = new ArrayList<ViewPoint>();
        players = new ArrayList<AbstractPlayer>();
    }

    public World(final long max_time, final int balls, final int squares) {
        this(max_time, balls, squares, 10d);
    }    

    public World(final long max_time, final int balls) {
        this(max_time, balls, balls);
    }

    public World() {
        this(0, 1000);
//        this(0, 10);
    }

    public final synchronized void start() {
        if (runThread == null) {
            runThread = new Thread(this);
            runThread.start();
            Log.log("start  Thread started");
        }
    }

    public final synchronized void stop() {
        runThread = null;
        Log.log("stop Thread stopped");
    }

    public final synchronized boolean isRunning() {
        return runThread != null;
    }

    public final void run() {
        try {
            while (isRunning()) {
                iterate();
            }
        } catch (Exception e) {
            Log.log("run " + e);
            e.printStackTrace();
        }
    }
    
    // TODO: create copy?
    public List<ViewPoint> getViewPoints() {
        return viewPoints;
    }

    public void add(final ViewPoint viewPoint) {
        synchronized (field) {
            field.add(viewPoint);
            viewPoints.add(viewPoint);
        }
    }

    public void remove(final LightObject lightObject) {
        synchronized (field) {
            field.remove(lightObject);
            if (lightObject instanceof ViewPoint) {
                viewPoints.remove(lightObject);
                Log.log(((ViewPoint) lightObject).getName() + " removed!");
                for (Player player : players) {    // FIXME decide not because of name! Implement equals for player!
                    if (player.getCamera().getViewPoint().equals(lightObject)) {
                        Log.log("Player " + ((ViewPoint) lightObject).getName() + " removed!");
                    }
                }
            }
        }
    }
    
    public List<AbstractPlayer> getPlayers() {
        return players;
    }

    public synchronized void add(final AbstractPlayer player) {
        Log.log("Adding player " + player.getCamera().getViewPoint().getName());
        players.add(player);
        add(player.getCamera().getViewPoint());
    }
    
    public synchronized void remove(final Player player) {
        players.remove(player);
        remove(player.getCamera().getViewPoint());
    }

    public void iterate() {
// must be synchronized, so remove and add don't interfere
// only if you need realtime
/*
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
*/
        synchronized (field) {
            time++;
            for (AbstractPlayer player : players) {
                move(player, player.calculateMovement());
                takePicture(player.getCamera());

            }
            for (WorldListener listener : listeners) {
                listener.newIteration();
            }
            if (finished() || time % 1000 == 0) {
                int algae = getAlgaeNumber();
                if (algae != lastAlgae) {
                    Log.log("time:  %d", time);
                    Log.log("algae: %d", getAlgaeNumber());
                    int eaten = 0;
                    for (Player player : players) {
                        eaten += player.getAlgae();
                    }
                    for (Player player : players) {
                        if (eaten <= 0) {
                            Log.log("  %-7s%5d", player.getCamera().getViewPoint().getName(), player.getAlgae());
                        } else {
                            Log.log("  %-7s%5d %6.2f", player.getCamera().getViewPoint().getName(), player.getAlgae(), (double) (100 * player.getAlgae()) / eaten);
                        }
                    }
                    lastAlgae = algae;
                }
                if (finished()) {
                    Log.journal("that's all folks!");
                    stop();
                }
            }
        }
    }

    private boolean finished() {
        return (max_time > 0 && time >= max_time) || field.getNumberOfObjects() <= players.size();
    }

    /**
     * Can viewpoint move to new position or is there a collision?
     *
     * @param viewPoint
     * @param newPosition
     * @return  Collision object or null.
     */
    public LightObject move(final ViewPoint viewPoint, final double newPosition[]) {
        return collisionDetector.collision(field, viewPoint, newPosition);
    }
    
    public long getTime() {
        return time;
    }
    
    public synchronized void addListener(final WorldListener listener) {
        listeners.add(listener);
    }

    public synchronized void removeListener(final WorldListener listener) {
        listeners.remove(listener);
    }

    public synchronized int getAlgaeNumber() {
        return field.getNumberOfObjects() - getPlayers().size();
    }
    
    
    /**
     * Take a picture from given view point from given light object field.
     * 
     * @param camera    A camera with view point and photo plate.
     */
    public final void takePicture(final Camera camera) {
        if (!camera.getPhotoPlate().isInitialized()) {
            return;
        }
        synchronized (field) {
            final PhotoPlateImpl photoPlate = (PhotoPlateImpl) camera.getPhotoPlate();
            final PictureImpl picture = photoPlate.getPictureImpl();
            Graphics2D graphics = picture.getImage().createGraphics();
            try {
                drawWorld(camera.getViewPoint(), picture.getWidth(), picture.getHeight(), photoPlate.increaseCurrent(),
                        graphics, photoPlate.getZoom(), photoPlate.getSensitivity());
                picture.getImage().flush();
            } finally {
                graphics.dispose();
            }
        }
    }

    public void drawPlayerView(final AbstractPlayer player, int width, int height, boolean clear,
            Graphics2D graphics, final boolean info, final boolean global) {
        drawPlayerView(player, 0, 0, width, height, clear, graphics, info, global);
    }

    public void drawPlayerView(final AbstractPlayer player, int x0, int y0, int width, int height, boolean clear,
            Graphics2D graphics, final boolean info, final boolean global) {
        double zoom = 0.8 * Math.min(width, height) /
                Math.max(player.getCamera().getPhotoPlate().getPicture().getWidth(), 
                    player.getCamera().getPhotoPlate().getPicture().getHeight()) * 
                    player.getCamera().getPhotoPlate().getZoom();
        double sensitivity = player.getCamera().getPhotoPlate().getSensitivity(); 
        drawWorld(player.getViewPoint(), x0, y0, width, height, clear, graphics, zoom, sensitivity);
        if (player instanceof AbstractBrainPlayer) {
            drawPlayerNet((AbstractBrainPlayer) player, x0, y0, width, height, graphics);
        }
        if (info) {
            drawPlayerInfo(player, x0, y0, width, height, graphics, global);
        }
    }

    public void drawWorld(final ViewPoint viewpoint, int width, int height, boolean clear,
            Graphics2D graphics, final double zoom, final double sensitivity) {
        drawWorld(viewpoint, 0, 0, width, height, clear, graphics, zoom, sensitivity);
    }

    public void drawWorld(final ViewPoint viewpoint, int x0, int y0, int width, int height, boolean clear,
            Graphics2D graphics, final double zoom, final double sensitivity) {
        graphics.setClip(x0, y0, width, height);
        final double[] position = viewpoint.getPosition();
        final double[] x = viewpoint.getX();
        final double[] y = viewpoint.getY();
        final double[] z = viewpoint.getZ();
        final double s = Math.exp(sensitivity);
        if (clear) {    // clear old image
//            graphics.clearRect(0, 0, width, height);
            graphics.setColor(Color.black);
            graphics.fillRect(x0, y0, width, height);
        }
        List<DistanceEntry> sorted = new ArrayList<DistanceEntry>();
        for (int i = 0; i < field.getNumberOfObjects(); i++) {
            final LightObject lightObject = field.getLightObject(i);
            final double d = CalculatorUtility.minusScalar(lightObject.getPosition(),
                position, z);
            if (d > 0) {    // TODO enough? what about big balls from behind?
                sorted.add(new DistanceEntry(-d, lightObject));
            }
        }
        final double halfWidth = width / 2;
        final double halfHeight = height / 2;
   
        Collections.sort(sorted);
        for (DistanceEntry entry: sorted) {
            
            final LightObject lightObject = entry.getLightObject();
   //            if (lightObject instanceof ViewPoint) {
   //                Log.log(((ViewPoint) lightObject).getName());
   //            }
            final double d = - entry.getDistance();
            double xr = zoom * CalculatorUtility.minusScalar(lightObject.getPosition(),
                position, x) / d + halfWidth;
            double yr = zoom * CalculatorUtility.minusScalar(lightObject.getPosition(),
                position, y) / d + halfHeight;
            double distance = CalculatorUtility.distance(lightObject.getPosition(), position);
            double radius = lightObject.getRadius() * zoom / distance;
            // http://stackoverflow.com/questions/596216/formula-to-determine-brightness-of-rgb-color
            
   //                double brightness = s / 100000 / Math.pow(distance, 0.5); // looks good
   //            double brightness = s / 500000 / Math.pow(distance, 0.9);  // looks good
            double brightness = s / 1000000 / Math.pow(distance, 0.9);  // looks good
            Color color = new Color(lightObject.getColor());
            if (!World.debug) {
   //                Log.log(brightness);
                int red = (int) (color.getRed() * brightness);
                if (red > 255) {
      //                    Log.log(brightness);
                    red = 255;
                }
                int green = (int) (color.getGreen() * brightness);
   //                Log.log(green);
                if (green > 255) {
      //                    Log.log(brightness);
                    green = 255;
                }
                int blue = (int) (color.getBlue() * brightness);
                if (blue > 255) {
      //                    Log.log(brightness);
                    blue = 255;
                }
                color = new Color(red, green, blue);
            }
            // mathematical coordinates: height - yr
            draw(lightObject, graphics, xr + x0, y0 + height - yr, radius, color, viewpoint);
        }
        graphics.setClip(null);
    }
    
    private void drawPlayerNet(final AbstractBrainPlayer player, final int x0, final int y0, int width, int height, final Graphics2D g) {
        g.setColor(Color.yellow);
        PhotoPlate plate = player.getCamera().getPhotoPlate();
        double d = 0.8 * Math.min(width, height) * plate.getZoom() / player.getCamera().getPhotoPlate().getZoom();              
        g.drawRect(x0 + (int) ((width - d) / 2), y0 + (int) ((height -  d) / 2), (int) d, (int) d);
        g.setColor(Color.blue);
//        Color c = new Color(0.0f, 0.0f, 0.1f, 0.5f);  // Blue with alpha = 0.5
        g.setPaint(Color.blue);                                // Use this translucent color
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
        for (int j = 0; j < player.getBrain().getEyeHeight(); j++) {
            for (int i = 0; i < player.getBrain().getEyeWidth(); i++) {
                if (player.getLastInputFire()[i + j * player.getBrain().getEyeWidth()]) {
                    g.fillRect(
                        x0 + 1 + (int) ((width - d) / 2 + d * i / 3 ),
                        y0 + 1 + (int) ((height -  d) / 2 + d * j / 3),
                        -2 + (int) d / player.getBrain().getEyeWidth(),
                        -2 + (int) d / player.getBrain().getEyeHeight());
                }
            }
        }
        g.setComposite(AlphaComposite.Src);
    }    

    private void drawPlayerInfo(AbstractPlayer player, int x0, int y0, int width, int height, final Graphics2D g, final boolean global) {
        g.setColor(Color.yellow);
//        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        g.setFont(Font.getFont(Font.SANS_SERIF));
        String str;
        if (global) {
            str = String.format("Time: %07d  Algae World: %5d  %s: %3d", time, getAlgaeNumber(), player.getCamera().getViewPoint().getName(), player.getAlgae());
        } else {    
            str = String.format("%s: %3d", player.getCamera().getViewPoint().getName(), player.getAlgae());
        }    
        g.drawString(str, x0 + 5, y0 + height - 7);
    }
    
    public void draw(LightObject lightObject, Graphics2D graphics, double xr, double yr, double size, final Color color,
            final ViewPoint vp) {
        graphics.setColor(color);
        if (World.debug) {
            size += 2;
        } else {
        	size += 2;
        }
        graphics.fillOval((int) (xr - size / 2), (int) (yr - size / 2), (int) size, (int) size);
//        graphics.setColor(Color.yellow);
//        graphics.fillOval((int) (xr - size / 2 + size / 4), (int) (yr - size / 2 + size / 4), (int) radius / 2, (int) radius / 2);
        
        if (lightObject instanceof ViewPoint) {
            ViewPoint viewPoint = (ViewPoint) lightObject;
            double d = CalculatorUtility.scalar(viewPoint.getZ(), vp.getZ());
            if (d < 0) {
                double size2 = Math.sin(Math.acos(-d)) * size;
                
                double dxr2 = CalculatorUtility.minusScalar(viewPoint.getZ(),
                        vp.getZ(), vp.getX()) * size / 2;
                double dyr2 = CalculatorUtility.minusScalar(viewPoint.getZ(),
                        vp.getZ(), vp.getY()) * size / 2;
                
                graphics.setColor(Color.red);
                size2 = size / 10 + 2;
//                graphics.fillOval((int) (xr + dxr2 - size2 / 2), (int) (yr + dyr2 - size2 / 2), (int) ((1 - 2 * dxr2 / size) * size2), (int) ((1 - 2 * dyr2 / size) * size2));
                graphics.fillOval((int) (xr + dxr2 - size2 / 2), (int) (yr + dyr2 - size2 / 2), (int) size2, (int) size2);
            }
            String name = viewPoint.getName();
            if (name.length() > 0) {
//                graphics.setXORMode(Color.red);
                graphics.setColor(Color.blue);
                graphics.drawString(name, (int) (xr), (int) yr);
//                graphics.setPaintMode();
            }
            
        }
    }

    private double delta = 0.00001;
    
    public void move(final ObjectWithCamera object, final Movement movement) {
        final Picture im = object.getCamera().getPhotoPlate().getPicture();

        double thetax = -(double) movement.getDx() * factor / 1000;
        double thetay = -(double) movement.getDy() * factor / 1000;
        double thetaz = 0;
        double deltaz = movement.getDz() * factor * delta;
        final ViewPoint viewPoint = object.getCamera().getViewPoint();
        final double[] position = viewPoint.getPosition();

        final double[] x = viewPoint.getX();
        final double[] y = viewPoint.getY();
        final double[] z = viewPoint.getZ();

        double[] newPosition = new double[] { 
            z[0] * deltaz + position[0],
            z[1] * deltaz + position[1],
            z[2] * deltaz + position[2]
        };
        LightObject collision = move(viewPoint, newPosition);
        if (collision instanceof AlgaLightObject) {
            if (object instanceof AbstractPlayer) {
                Log.log("%s eats alga: delicious", viewPoint.getName());
            	AbstractPlayer player = (AbstractPlayer) object;
            	player.incrementAlga();
                remove(collision);
        	} else {
                Log.log("%s collides with alga", viewPoint.getName());
        	}
        }
        position[0] = newPosition[0];
        position[1] = newPosition[1];
        position[2] = newPosition[2];
        
        // rotate around z axis
        rotate(thetaz, x, y);

        // rotate around x axis
        rotate(thetax, x, z);

        // rotate around y axis
        rotate(thetay, y, z);
    }

    private void rotate(double ztheta, final double[] x, final double[] y) {
        // rotate around z axis
        double ct = Math.cos(ztheta);
        double st = Math.sin(ztheta);

        double[] xn = new double[3];
        for (int i = 0; i < 3; i++) {
            xn[i] = x[i] * ct + y[i] * st;
        }
        for (int i = 0; i < 3; i++) {
            y[i] = y[i] * ct - x[i] * st;
        }
        for (int i = 0; i < 3; i++) {
            x[i] = xn[i];
        }
    }

}
