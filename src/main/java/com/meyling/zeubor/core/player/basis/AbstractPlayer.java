package com.meyling.zeubor.core.player.basis;

import com.meyling.zeubor.core.common.MovementCalculator;
import com.meyling.zeubor.core.common.PhotoPlateAttributes;
import com.meyling.zeubor.core.common.Player;
import com.meyling.zeubor.core.common.ViewPoint;
import com.meyling.zeubor.core.physics.PhotoPlateImpl;
import com.meyling.zeubor.core.physics.CameraImpl;

import java.awt.*;

public abstract class AbstractPlayer implements Player, MovementCalculator {
    
    private final CameraImpl camera;
    
    private int alga;
    
    
    public AbstractPlayer(final String name, final int width, final int height) {
        final PhotoPlateImpl photoPlate = new PhotoPlateImpl();
        final ViewPoint viewPoint = new ViewPoint(name);
        viewPoint.setColor(Color.red.getRGB() + Color.blue.getRGB());
        PhotoPlateAttributes attributes = new PhotoPlateAttributes();
        photoPlate.setSensitivity(attributes.getSensitivity());
        photoPlate.setZoom(attributes.getZoom());
        photoPlate.setSnapshot(attributes.getSnapshot());
        photoPlate.init(width, height);
        camera = new CameraImpl(photoPlate, viewPoint);
    }
    
    public String getName() {
        return camera.getViewPoint().getName();
    }
    
    public int getAlgae() {
        return alga;
    }
    
    public void incrementAlga() {
        alga++;
    }
    
    public CameraImpl getCamera() {
        return camera;
    }
    
    public final ViewPoint getViewPoint() {
        return camera.getViewPoint();
    }

}
