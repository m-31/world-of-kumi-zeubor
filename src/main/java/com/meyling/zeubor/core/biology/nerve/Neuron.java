package com.meyling.zeubor.core.biology.nerve;

import java.util.ArrayList;
import java.util.List;


public class Neuron {
    

    private final List<Dendrite> dendrites;

    private final List<Axon> axons;

    private final PotentialCalculator potentialCalculator;
    
    private boolean fire;

    private int lowerThreshold; 
    
    private int higherThreshold; 
    
    private int potential;

    private Glia glia;
    
    
    public Neuron() {
        this(null);
    }
        
    public Neuron(final PotentialCalculator potentialCalculator) {
        this.potentialCalculator = potentialCalculator;
        dendrites = new ArrayList<Dendrite>();
        axons = new ArrayList<Axon>();
        lowerThreshold = 100;
        higherThreshold = 10000;
    }

    /**
     * Add dendrite.
     *
     * @param neuron    If this neuron fires we get input.
     * @param weight     This is the difference for the action potential.
     */
    public void addDentrite(final Neuron neuron, final int weight) {
        final Dendrite dendrite = new Dendrite(weight);
        final Axon axon = new Axon(neuron, dendrite);
        dendrite.setAxon(axon);
        neuron.addAxon(axon);       // the other neuron gets an axon
        dendrites.add(dendrite);    // we get a new dendrite
    }
    
    public void removeDendrite(final Dendrite dendrite) {
        dendrites.remove(dendrite);
        Axon axon = dendrite.getAxon();
        if (axon != null) {
            axon.getNeuron().removeAxon(axon);
        }
    }

    public void addAxon(final Axon axon) {
        axons.add(axon);
    }

    public void removeAxon(final Axon axon) {
        axons.remove(axon);
    }

    public List<Axon> getAxons() {
        return axons;
    }
    
    public List<Dendrite> getDendrites() {
        return dendrites;
    }

    
    public final void calculateFire() {
        potentialCalculator.accumulate(this);
    }
    
    public final int getPotential() {
        return potential;
    }

    public final void setPotential(final int potential) {
        this.potential = potential;
    }

    public final int getLowerThreshold() {
        return lowerThreshold;
    }

    public final void setLowerThreshold(final int threshold) {
        this.lowerThreshold = threshold;
    }

    public final int getHigherThreshold() {
        return higherThreshold;
    }

    public final void setHigherThreshold(final int threshold) {
        this.higherThreshold = threshold;
    }

    public final boolean getFire() {
        return fire;
    }
    
    public final void setFire(boolean fire) {
        this.fire = fire;
    }

    public void setGlia(final Glia glia) {
        this.glia = glia;
    }

    public final Glia getGlia() {
        return glia;
    }

    public void calculateAxonFire() {
        for (Axon axon : getAxons()) {
            axon.setFire(getFire());
        }
        setFire(false);
    }
    
}
