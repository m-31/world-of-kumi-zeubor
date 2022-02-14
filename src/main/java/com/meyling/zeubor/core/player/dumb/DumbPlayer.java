package com.meyling.zeubor.core.player.dumb;

import com.meyling.zeubor.core.common.Movement;
import com.meyling.zeubor.core.common.Picture;
import com.meyling.zeubor.core.player.AbstractPlayer;

import java.awt.*;

public class DumbPlayer extends AbstractPlayer {
    
    public DumbPlayer(final String name, final int width, final int height) {
        super(name, width, height);
    }

    public Movement calculateMovement() {
        int deltaX = 0;
        int deltaY = 0;
        final Picture im = getCamera().getPhotoPlate().getPicture();
        int width = im.getWidth();
        int height = im.getHeight();
        int cx = (width - 1) / 2;
        int cy = (height -1) / 2;
        int x = cx;
        int y = cy;
//            System.out.println()
//            System.out.println("x=" + x + " y= " + y);
        int w = 0;
        do {            
            for (int direction = 0; direction < 4; direction++) {
                if (x < 0 && width >= height) {
                    break;
                }
                if (y < 0 && height >= width) {
                    break;
                }
                int dx = ((direction + 1) % 2) * (1 - 2 * (direction / 2)); 
                int dy = (direction % 2) * (1 - 2 * (direction / 2));
//                System.out.println("direction=" + direction + " dx=" + dx + " dy=" + dy);
                if (direction % 2  == 0) {
                    w++;
                }
                for (int j = 0; j < w; j++) {
                    if (x >= 0 && x < width && y >= 0 && y < height) {
                        final Color color = new Color(im.getColor(x, y));
//                        if (color.getGreen() > 200) {     // this would make anton to good ...
                        if (color.getGreen() > 0) {
//                            System.out.println("x=" + x + " y= " + y);
                            deltaX = x - cx;
                            deltaY = y - cy;
//                            System.out.println("deltaX="  + deltaX + "  deltaY=" + deltaY);
                            int deltaZ = 1;
                            if (Math.abs(deltaX) > 0) {
                                deltaX = deltaX / Math.abs(deltaX);
                            }
                            if (Math.abs(deltaY) > 0) {
                                deltaY = deltaY / Math.abs(deltaY);
                            }
                            return new Movement(deltaX, deltaY, deltaZ);
                        }
                    }
                    x += dx;
                    y += dy;
                }
            }
        } while (w < Math.max(width, height));
        return new Movement(1, 1, 0);
    }
    
}
