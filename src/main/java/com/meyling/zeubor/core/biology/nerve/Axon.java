package com.meyling.zeubor.core.biology.nerve;


public final class Axon {

    /** we belong to this neuron */
    private final Neuron neuron;

    /** to this dendrite we connect */
    private final Dendrite dendrite;
    
    
    private boolean fire;
    

    /**
     * Constructor.
     * 
     * @param neuron   we belong to this neuron
     * @param dendrite to this dendrite from another neuron we connect
     */
    public Axon(final Neuron neuron, final Dendrite dendrite) {
        this.neuron = neuron;
        this.dendrite = dendrite;
    }

    public final boolean getFire() {
        return fire;
    }
    
    public final void setFire(boolean fire) {
        this.fire = fire;
    }
    
    public Neuron getNeuron() {
        return neuron;
    }

    public final Dendrite getDentride() {
        return dendrite;
    }

}
