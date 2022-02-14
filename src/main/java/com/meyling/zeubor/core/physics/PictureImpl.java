package com.meyling.zeubor.core.physics;

import com.meyling.zeubor.core.common.Picture;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PictureImpl implements Picture {

    private final BufferedImage im;

    // direct manipulation:
    //   int[] pixels = ??
    //   WritableRaster raster = (WritableRaster) image.getData();
    //   raster.setPixels(0,0,width,height,pixels);
    
    public PictureImpl(int width, int height) {
        im = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }
    
    public int getWidth() {
        return im.getWidth();
    }

    public int getHeight() {
        return im.getHeight();
    }

    public int getColor(int x, int y) {
        return im.getRGB(x, getHeight() - 1 - y);   // mathematical coordinates!
    }

    public BufferedImage getImage() {
        return im;
    }
    
    public Picture getScaledPicture(int width, int height) {

/*
        // not good working:        
        PictureImpl dimg = new PictureImpl(width, height);
        Graphics2D g2d = dimg.getImage().createGraphics();
        g2d.drawImage(this.getImage(), 0, 0, null);
        g2d.dispose();
*/        
        
        Image tmp = im.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
//        Image tmp = im.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        PictureImpl dimg = new PictureImpl(width, height);
        Graphics2D g2d = dimg.getImage().createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return dimg;
    }

}


