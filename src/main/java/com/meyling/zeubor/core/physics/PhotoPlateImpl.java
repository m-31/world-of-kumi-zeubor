package com.meyling.zeubor.core.physics;

import com.meyling.zeubor.core.common.LightObjectField;
import com.meyling.zeubor.core.common.PhotoPlate;
import com.meyling.zeubor.core.common.PhotoPlateAttributes;
import com.meyling.zeubor.core.common.Picture;
import com.meyling.zeubor.core.log.Log;


/**
 * Photo plate that can take a photograph of a {@link LightObjectField}.
 */
public final class PhotoPlateImpl implements PhotoPlate  {
    private double sensitivity;
    private double zoom;
    private int snapshot;
    private int width;
    private int height;
    private PictureImpl picture;
    private int current;
    private boolean initialized;


    public boolean isInitialized() {
        return initialized;
    }

    public PhotoPlateImpl() {
        final PhotoPlateAttributes attributes = new PhotoPlateAttributes();
        sensitivity = attributes.getSensitivity();
        zoom = attributes.getZoom();
        snapshot = attributes.getSnapshot();
    }

    /**
     * Set size of photo plate.
     *
     * @param   width   Width.
     * @param   height  Height.
     */
    public synchronized void init(final int width, final int height) {
        if (initialized && this.width == width && this.height == height) {
            return;
        }
        this.width = width;
        this.height = height;

        while (width <= 0 || height <= 0) {
            Log.debug("Photo Plate init waiting");
            System.out.println("Photo Plate init waiting");
            return;
        }

        picture = new PictureImpl(width, height);
        
        Log.debug("Photo Plate init successfully ended (" + width + ", " + height + ")");
        System.out.println("Photo Plate init successfully ended (" + width + ", " + height + ")");
        initialized = true;
    }

    public final Picture getPicture() {
        return picture;
    }

    public final PictureImpl getPictureImpl() {
        return picture;
    }

    public boolean increaseCurrent() {
        if (++current > snapshot) {
            current = 0;
            return true;
        }
        return false;
    }
    

    public final double getSensitivity() {
        return sensitivity;
    }

    public final void setSensitivity(double sensitivity) {
        this.sensitivity = sensitivity;
    }

    public final double getZoom() {
        return zoom;
    }

    public final void setZoom(double zoom) {
        Log.debug("zoom=" + zoom);
        this.zoom = zoom;
    }

    public final int getSnapshot() {
        return snapshot;
    }

    public final void setSnapshot(int snapshot) {
        this.snapshot = snapshot;
    }
    
    public final PhotoPlateAttributes getPhotoPlateAttributes() {
        return new PhotoPlateAttributes(getSensitivity(), getZoom(), getSnapshot());
    }


}


