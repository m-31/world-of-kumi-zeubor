package com.meyling.zeubor.core.gui;


import com.meyling.zeubor.core.common.PhotoPlateAttributes;
import com.meyling.zeubor.core.common.ViewPoint;
import com.meyling.zeubor.core.physics.PhotoPlateImpl;
import com.meyling.zeubor.core.world.CameraImpl;
import com.meyling.zeubor.core.world.World;

import java.awt.*;

/**
 * Star field viewer.
 */
public final class SimulatorViewer {

    private final World world;
    
    private ManualMovement positionCalculator;

    private CameraImpl camera;

    public SimulatorViewer(final World world, final ViewPoint viewPoint,
            final double sensitivity, final double zoom, final int snapshot,
            final int width, final int height) {
        this.world = world;
        final PhotoPlateImpl photoPlate = new PhotoPlateImpl();
        positionCalculator = new ManualMovement();
        photoPlate.setSensitivity(sensitivity);
        photoPlate.setZoom(zoom);
        photoPlate.setSnapshot(snapshot);
        photoPlate.init(width, height);
        camera = new CameraImpl(photoPlate, viewPoint);
    }

    public SimulatorViewer(final World world, final ViewPoint viewPoint,
            final PhotoPlateAttributes properties, final int width, final int height) {
        this(world, viewPoint, properties.getSensitivity(), properties.getZoom(),
            properties.getSnapshot(), width, height);
    }


    public final void resize(final int width, final int height) {
        camera.getPhotoPlateImpl().init(width, height);
    }

    public final void close() {
        camera = null;
        positionCalculator = null;
    }

    public final PhotoPlateAttributes getProperties() {
        return camera.getPhotoPlate().getPhotoPlateAttributes();
    }

    public final void applyVisualChanges(final PhotoPlateAttributes properties) {
        applyVisualChanges(properties.getSensitivity(), properties.getZoom(),
        properties.getSnapshot());
    }

    
    public final void applyVisualChanges(final double sensitivity, final double zoom,
            final int snapshot) {
        camera.getPhotoPlate().setSensitivity(sensitivity);
        camera.getPhotoPlate().setZoom(zoom);
        camera.getPhotoPlate().setSnapshot(snapshot);
        takePicture();
    }

    public final void paintPicture(Graphics g) {
        PhotoPlateImpl r = camera.getPhotoPlateImpl();
        if (g != null && r.isInitialized()) {
            g.drawImage(r.getPictureImpl().getImage(), 0, 0, null);
        }
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

    public final void setDelta(final double delta) {
        positionCalculator.setDelta(delta);
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

}

