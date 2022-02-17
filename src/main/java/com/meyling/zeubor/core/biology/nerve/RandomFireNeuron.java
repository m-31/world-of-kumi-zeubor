package com.meyling.zeubor.core.biology.nerve;


/**
 * Fires only one axon if neuron fires.
 */
public final class RandomFireNeuron extends Neuron {

    public RandomFireNeuron(final PotentialCalculator potentialCalculator) {
        super(potentialCalculator);
    }
    
    public void calculateAxonFire() {
        for (Axon axon : getAxons()) {
            axon.setFire(false);
        }
        if (getFire()) {
            int max = getAxons().size();
            int fire = (int) (Math.random() * max);
            getAxons().get(fire).setFire(true);
        }
        setFire(false);
    }
    
}
