package com.meyling.zeubor.core.nerve;

import com.meyling.zeubor.core.nerve.basis.Axon;
import com.meyling.zeubor.core.nerve.basis.Dendrite;
import com.meyling.zeubor.core.nerve.basis.Glia;
import com.meyling.zeubor.core.nerve.basis.Neuron;

import java.util.ArrayList;
import java.util.List;


public final class NeuronalNet {

    private final List<Glia> glias;
    private Neuron neu11;
    private Neuron neu12;
    private Neuron neu13;
    private Neuron neu14;
    
    public static void main(String[] argv) {
        NeuronalNet net = new NeuronalNet();
        net.init();
        net.neu11.setFire(true);
        for (int i = 0; i < 50 ; i++) {
            if (i % 5 == 0) {
                net.neu11.setFire(true);
                net.neu12.setFire(true);
            }
            net.iterate();
        }
        net.printNeurons();
    }

    public NeuronalNet() {
        glias = new ArrayList<Glia>();
    }
 
    public void init() {
        System.out.println("init");
        
        // create input layer neurons
        //  01 02
        //  03 04
        neu11 = createNeuron();
        neu12 = createNeuron();
        neu13 = createNeuron();
        neu14 = createNeuron();

        // create application layer neurons
        Neuron neu21 = createNeuron();
        Neuron neu22 = createNeuron();
        Neuron neu23 = createNeuron();
        Neuron neu24 = createNeuron();

        // create output layer neurons
        Neuron neu31 = createNeuron();

        // add initial connections 
        
        neu21.addDentrite(neu11,  100);
//        neu21.addDentrite(neu21,  100);     // recursive trigger ! FIXME
//        neu21.addDentrite(neu21,  -900);     // recursive trigger ! FIXME
        neu21.addDentrite(neu12,   10);
        neu21.addDentrite(neu13,   10);
        neu21.addDentrite(neu14,    5);
        neu22.addDentrite(neu11,   10);
        neu22.addDentrite(neu12,  100);
        neu22.addDentrite(neu13,    5);
        neu22.addDentrite(neu14,   10);
        neu23.addDentrite(neu11,   10);
        neu23.addDentrite(neu12,    5);
        neu23.addDentrite(neu13,  100);
        neu23.addDentrite(neu14,    5);
        neu24.addDentrite(neu11,    5);
        neu24.addDentrite(neu12,   10);
        neu24.addDentrite(neu13,   10);
        neu24.addDentrite(neu14,  100);
        neu31.addDentrite(neu21,  100);
        neu31.addDentrite(neu22,  100);
        neu31.addDentrite(neu23,  100);
        neu31.addDentrite(neu24,  100);
    }
    
    public Neuron createNeuron() {
        final Neuron neuron = new Neuron();
        final Glia glia = new Glia(neuron);
        glias.add(glia);
        return neuron;
    }

    public void iterate() {
        printNeurons();
        // route neuron fire to axons
        for (Glia glia : glias) {
            Neuron neuron = glia.getNeuron();
            glia.addFireEvent(neuron.getFire());
            for (Axon axon : neuron.getAxons()) {
                axon.setFire(neuron.getFire());
            }
            neuron.setFire(false);
        }
        // firing axon triggers dendrite fire
        for (Glia glia : glias) {
            Neuron neuron = glia.getNeuron();
            for (Dendrite dendrite : neuron.getDendrites()) {
                dendrite.setFire((dendrite.getAxon().getFire()));
            }
        }
        // accumulate action potential for firing dendrites
        for (Glia glia : glias) {
            Neuron neuron = glia.getNeuron();
            for (Dendrite dendrite : neuron.getDendrites()) {
                if (dendrite.getFire()) {
                    final Glia g = dendrite.getAxon().getNeuron().getGlia();
                    List<Boolean> fireHistory = g.getFireHistory();
                    int c = 0;
                    int d = 0;
                    for (boolean fire : fireHistory) {
                        c++;
                        if (fire) {
                            d++;
                        }
                    }
                    // short term memory?
                    if (d > 5 && c > 0) {
                        c = dendrite.getWeight() * (100 + (d + c) * 100 / 2 / c)  / 100;
//                        System.out.println(c);
                    } else {
                        c = dendrite.getWeight();
                    }
                    neuron.setPotential(neuron.getPotential() + c);
                }
            }
            neuron.setFire(neuron.getPotential() >= neuron.getLowerThreshold() && neuron.getPotential() < neuron.getHigherThreshold());
            if (neuron.getPotential() >= neuron.getLowerThreshold()) {
                neuron.setPotential(neuron.getPotential() - neuron.getLowerThreshold());
            }
        }
    }
    
    public void printNeurons() {
        // route neuron fire to axons
        int i = 1;
        for (Glia glia : glias) {
            Neuron neuron = glia.getNeuron();
            System.out.print(" " + (neuron.getFire() ? 'X' : '-'));
        }
        System.out.println();
    }

}
