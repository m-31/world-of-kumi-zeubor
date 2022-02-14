package com.meyling.zeubor.core.nerve.basis;


/**
 * Fires axons if neuron fires and random occurs.
 */
public final class FireRandomNeuron extends Neuron {

    private final double trigger;

    public FireRandomNeuron(final PotentialCalculator potentialCalculator, final double trigger) {
        super(potentialCalculator);
        this.trigger = trigger;
    }
    
    public void calculateAxonFire() {
        boolean fire = getFire() && Math.random() <= trigger;
        for (Axon axon : getAxons()) {
            axon.setFire(fire);
        }
        setFire(false);
    }
    
}
