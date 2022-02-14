package com.meyling.zeubor.core.common;


/**
 * A picture has width and height and contains various color pixels. 
 */
public interface Picture  {
    
    /**
     * Get width of picture.
     *
     * @return Width.
     */
    public int getWidth();

    /**
     * Get height of picture.
     *
     * @return Height.
     */
    public int getHeight();

    /**
     * Get color of pixel.
     * 
     * @param x X-Position. Must be between 0 and {@link #getWidth()} - 1.
     * @param y Y-Position. Must be between 0 and {@link #getHeight()} - 1.
     * @return Color.
     */
    public int getColor(int x, int y);

    /**
     * Get a new picture from the old one.
     * Usually used to get a smaller picture.
     * 
     * @param width New width.
     * @param height New height.
     * @return  New picture.
     */
    public Picture getScaledPicture(int width, int height);

}


