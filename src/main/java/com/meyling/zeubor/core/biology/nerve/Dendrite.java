package com.meyling.zeubor.core.biology.nerve;


public final class Dendrite {

    private int weight = 100;
    
    private boolean fire;

    private Axon axon;
    
    public Dendrite() {
    }

    public Dendrite(final int weight) {
        this.weight = weight;
    }

    /**
     * Set the axon that fires this dendrite.
     *
     * @param axon
     */
    public void setAxon(final Axon axon) {
        this.axon = axon;
    }

    /**
     * Get axon that fires this dendrite.
     *
     * @return
     */
    public Axon getAxon() {
        return axon;
    }
    
    public final boolean getFire() {
        return fire;
    }
    
    public final void setFire(final boolean fire) {
        this.fire = fire;
    }
    
    public final int getWeight() {
        return weight;
    }

    public final void setWeight(final int weight) {
        this.weight = weight;
    }

}
