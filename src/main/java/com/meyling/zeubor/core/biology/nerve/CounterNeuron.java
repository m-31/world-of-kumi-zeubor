package com.meyling.zeubor.core.biology.nerve;


/**
 * Fires if counter is max. Resets counter if neuron is activated. 
 */
public final class CounterNeuron extends Neuron {

    private int max;
    
    private int counter;

    public CounterNeuron(final PotentialCalculator potentialCalculator, final int max) {
        super(potentialCalculator);
        this.max = max;
    }
    
    public void calculateAxonFire() {
        if (getFire()) {
            counter = 0;
            for (Axon axon : getAxons()) {
                axon.setFire(false);
            }
        } else {
            counter = ++counter % max;
            boolean fire = (0 == counter);
            for (Axon axon : getAxons()) {
                axon.setFire(fire);
            }
        }
        setFire(false);
    }
    
}
