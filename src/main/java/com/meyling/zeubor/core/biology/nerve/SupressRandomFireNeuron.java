package com.meyling.zeubor.core.biology.nerve;


/**
 * Fires only one axon if neuron fires not.
 */
public final class SupressRandomFireNeuron extends Neuron {

    // remember last decision, FIXME change by random when too long
    private int last = -1;
    private int max;
    private int limit;
    private int counter = 0;
    
    public SupressRandomFireNeuron(final PotentialCalculator potentialCalculator, final int max) {
        super(potentialCalculator);
        this.max = max;
        this.limit = (int) ((1 + Math.random()) * max);
    }
    
    public void calculateAxonFire() {
        for (Axon axon : getAxons()) {
            axon.setFire(false);
        }
        if (!getFire()) {
        	counter = ++counter % limit;
            int fire = last;
            int m = getAxons().size();
            if (m > 0 && (fire < 0 || counter == 0)) {
                fire = (int) (Math.random() * m);
            }
            if (fire >= 0) {
                getAxons().get(fire).setFire(true);
            }
            last = fire;
        } else {
            last = -1;
            counter = 0;
            limit = (int) ((1 + Math.random()) * max);
        }
        setFire(false);
    }
    
}
