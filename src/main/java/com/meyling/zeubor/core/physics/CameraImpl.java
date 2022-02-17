package com.meyling.zeubor.core.physics;

import com.meyling.zeubor.core.common.Camera;
import com.meyling.zeubor.core.common.PhotoPlate;
import com.meyling.zeubor.core.common.ViewPoint;
import com.meyling.zeubor.core.physics.PhotoPlateImpl;

/**
 * Camera. Has a position, orientation and a photo plate.
 */
public final class CameraImpl implements Camera {

    private final PhotoPlateImpl photoPlate;

    private final ViewPoint viewpoint;

    public CameraImpl(final PhotoPlateImpl photoPlate, final ViewPoint viewPoint) {
        this.photoPlate = photoPlate;
        this.viewpoint = viewPoint;
    }

    public final ViewPoint getViewPoint() {
        return viewpoint;
    }

    public final PhotoPlate getPhotoPlate() {
        return photoPlate;
    }
    
    public final PhotoPlateImpl getPhotoPlateImpl() {
        return photoPlate;
    }
    
}

