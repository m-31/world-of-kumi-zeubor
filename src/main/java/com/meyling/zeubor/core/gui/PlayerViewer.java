package com.meyling.zeubor.core.gui;

import com.meyling.zeubor.core.common.ViewPoint;
import com.meyling.zeubor.core.common.WorldListener;
import com.meyling.zeubor.core.physics.PhotoPlateImpl;
import com.meyling.zeubor.core.physics.PictureImpl;
import com.meyling.zeubor.core.player.AbstractPlayer;
import com.meyling.zeubor.core.player.brain.AbstractBrainPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;


@SuppressWarnings("serial")
public class PlayerViewer extends JComponent implements WorldListener {

    private final AbstractPlayer player;

    public PlayerViewer(final AbstractPlayer player) {
        super();
        this.player = player;
    }

    public ViewPoint getViewPoint() {
        return player.getViewPoint();
    }

    public final void paintComponent(Graphics g) {
    	if (player instanceof AbstractBrainPlayer) {
    		AbstractBrainPlayer bp = (AbstractBrainPlayer) player;
    		paintPicture(g, bp.getBrain().getEyeWidth(), bp.getBrain().getEyeHeight());
    	} else {
            PhotoPlateImpl r = player.getCamera().getPhotoPlateImpl();
            if (g != null && r.isInitialized()) {
                g.drawImage(r.getPictureImpl().getImage(), 0, 0, getWidth(), getHeight(), null);
            }
    	}	
    }
    
    public void paintPicture2(final Graphics g, AbstractBrainPlayer player) {
        
    }
    
    public void paintPicture(final Graphics g, final int pixelWidth, final int pixelHeight) {
        PhotoPlateImpl r = player.getCamera().getPhotoPlateImpl();
		if (g != null && r.isInitialized()) {
//		    g.drawImage(r.getPictureImpl().getImage(), 0, 0, getWidth(), getHeight(), null); // helps against flickering???
		    
		    PictureImpl tmp = (PictureImpl) r.getPictureImpl().getScaledPicture(pixelWidth, pixelHeight);
		    BufferedImage im2 = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		    Graphics2D g2d = (Graphics2D) im2.getGraphics();
		    g2d.drawImage(tmp.getImage(),  0, 0,  getWidth(),  getHeight(),  null);

		    // increase brightness
            RescaleOp op = new RescaleOp(15f, 0f, null);
            op.filter(im2, im2);
            
            BufferedImage tmp2 = copyImage(r.getPictureImpl().getImage());
            // overlay original image by  making black pixels invisible
            for (int j = 0; j < r.getPicture().getHeight(); j++) {
                for (int i = 0; i < r.getPicture().getWidth(); i++) {
                    if (tmp2.getRGB(i, j) == Color.black.getRGB()) {
                        tmp2.setRGB(i, j, 0x8F1C1C);
                    }
                }
            }

		    g2d.drawImage(tmp2, 0, 0, getWidth(), getHeight(), null);
            g2d.dispose();
		    
		    
		    g.drawImage(im2, 0, 0, getWidth(), getHeight(), null);
		}
		
    }
    
    // FIXME move to helper class
    public static BufferedImage copyImage(final BufferedImage source) {
        BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics g = b.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return b;
    }
    
    public void newIteration() {
        repaint();
    }

}


