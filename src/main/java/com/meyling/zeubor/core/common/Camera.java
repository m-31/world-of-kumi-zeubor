package com.meyling.zeubor.core.common;


/**
 * Camera. Has a photo plate and a viewpoint (that is position and orientation).
 */
public interface Camera {

    /** 
     * Get position and orientation of camera.
     * 
     * @return  View point.
     */
    public ViewPoint getViewPoint();

    /**
     * Get photo plate.
     * 
     * @return  Photo plate.
     */
    public PhotoPlate getPhotoPlate();


}

