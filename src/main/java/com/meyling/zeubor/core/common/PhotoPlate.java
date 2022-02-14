package com.meyling.zeubor.core.common;

/**
 * A photo plate is like a camera but without view point. 
 *
 */
public interface PhotoPlate {

    /**
     * Get last picture taken.
     * 
     * @return  Last picture.
     */
    public Picture getPicture();


    /**
     * Get sensitivity of the photo plate. If an object is too far away it's brightness might
     * not be high enough to create a dot on the photo plate. Increasing the sensitivity of
     * the photo plate helps to solve the problem.
     * 
     * @return Returns the sensitivity.
     */
    public double getSensitivity();

    /**
     * @param sensitivity The sensitivity to set.
     */
    public void setSensitivity(double sensitivity);

    /**
     * Get zoom factor for photo plate. The projection size on the photo plate can be changed
     * with the value of this parameter. The optimal value for this parameter depends on the
     * screen resolution and your distance from the monitor. A projection size of 1 should be 
     * seen in an 45 degree angle. If we assume that you see your monitor (height) in an
     * 20 degree angle, we can calculate as follows:
     * 
     *    zoom = screen height in pixel / 2 / tan(10)
     *    
     * That means zoom should have a value between 2000 and 3500. Other values are possible
     * but create an unnatural perspective.
     * 
     * @return Returns the zoom factor.
     */
    public double getZoom();

    /**
     * @param zoom The zoom factor to set.
     */
    public void setZoom(double zoom);

    /**
     * @return Returns the snapshot.
     */
    public int getSnapshot();

    /**
     * @param snapshot The snapshot to set.
     */
    public void setSnapshot(int snapshot);

    public PhotoPlateAttributes getPhotoPlateAttributes();

    public boolean isInitialized();

}