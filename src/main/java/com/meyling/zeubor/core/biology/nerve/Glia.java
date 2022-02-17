package com.meyling.zeubor.core.biology.nerve;

import java.util.ArrayList;
import java.util.List;


public final class Glia {

    private final static int MAXIMUM = 3;
    
    private List<Boolean> fireHistory;

    private final Neuron neuron;
    
    public Glia(final Neuron neuron) {
        this.neuron = neuron;
        neuron.setGlia(this);
        fireHistory = new ArrayList<Boolean>();
    }

    public Neuron getNeuron() {
        return neuron;
    }
    
    /**
     * Remember fire event status.
     * 
     * @param   fire    <code>true</code> for firing or <code>false</code> for not firing
     */
    public final void addFireEvent(final boolean fire) {
        fireHistory.add(0, fire);
        if (fireHistory.size() >= MAXIMUM) {
            fireHistory.remove(MAXIMUM - 1);
        }
    }
    
    public final List<Boolean> getFireHistory() {
        return fireHistory;
    }

}
