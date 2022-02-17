package com.meyling.zeubor.core.player.brain;

import com.meyling.zeubor.core.common.Movement;
import com.meyling.zeubor.core.common.Picture;
import com.meyling.zeubor.core.biology.brain.Brain;
import com.meyling.zeubor.core.biology.nerve.Neuron;
import com.meyling.zeubor.core.player.basis.AbstractPlayer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBrainPlayer extends AbstractPlayer {
    
    private final Brain brain;
    
    private final List<Boolean> lastInputFire;
    
    private int zCounter;
    
    public AbstractBrainPlayer(final String name, final int width, final int height, final Brain brain) {
        super(name, width, height);
        this.brain = brain;
        this.lastInputFire = new ArrayList<Boolean>();
    }
    
    public Brain getBrain() {
        return brain;
    }
    
    public synchronized Boolean[] getLastInputFire() {
        return lastInputFire.toArray(new Boolean[0]);
    }
    
    public Movement calculateMovement() {
        final Picture im = getCamera().getPhotoPlate().getPicture();
        
        Picture dimg = im.getScaledPicture(getBrain().getEyeWidth(), getBrain().getEyeHeight());

        int[][] green = new int[getBrain().getEyeHeight()][getBrain().getEyeWidth()]; 
        int max = 0;
        for (int i = 0; i < getBrain().getEyeHeight(); i++) {
            for (int j = 0; j < getBrain().getEyeWidth(); j++) {
                final Color color = new Color(dimg.getColor(j, getBrain().getEyeHeight() - 1 - i));
                green[i][j] = color.getGreen();
                if (green[i][j] > max) {
                    max = green[i][j];
                }
            }
        }
/*        
        for (int i = 0; i < getBrain().getEyeHeight(); i++) {
            for (int j = 0; j < getBrain().getEyeWidth(); j++) {
                System.out.print(green[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();

        System.out.println(max);
*/
        if (max > 0) {
            for (int i = 0; i < getBrain().getEyeHeight(); i++) {
                for (int j = 0; j < getBrain().getEyeWidth(); j++) {
                    if (green[i][j] >= max) {
                        getBrain().getInputNeurons().get(i * getBrain().getEyeWidth() + j).setFire(true);
                    }
                }
            }
        } else {    // ok, we have no input
            getBrain().getInputNeurons().get(0).setFire(true);
            if (Math.random() < 0.1) {
                getBrain().getInputNeurons().get(1).setFire(true);
            }
        }
        
        saveInputNeuronFire();
        
//        getBrain().printInput();
        getBrain().iterate();
        int deltaX = getBrain().getDirectionX();
        int deltaY = getBrain().getDirectionY();
        int deltaZ = getBrain().getSpeed();
        if (deltaZ != 0) {
            getBrain().resetRandom();
            zCounter = 0;
        } else {
            zCounter++;
            if (zCounter > 5) {
                zCounter = 0;
                getBrain().increaseRandom();
            }
        }
//        System.out.println(deltaX + " " + deltaY + " " + deltaZ);
        return new Movement(deltaX, deltaY, deltaZ);
    }

    protected void grow() {   // FIXME integrate into constructor?
        final List<List<Integer>> genom = new ArrayList<List<Integer>>();

        final List<Integer> inputParameter = new ArrayList<Integer>();
        inputParameter.add(3);
        inputParameter.add(3);
        genom.add(inputParameter);
        
        final List<Integer> outputParameter = new ArrayList<Integer>();
        outputParameter.add(5);
        genom.add(outputParameter);
        
        getBrain().grow(genom);
    }


    protected synchronized void saveInputNeuronFire() {
        lastInputFire.clear();
        for (final Neuron neuron : getBrain().getInputNeurons()) {
            lastInputFire.add(neuron.getFire());
        }
    }
}
