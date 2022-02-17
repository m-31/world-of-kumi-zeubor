package com.meyling.zeubor.core.nerve.brain;

import com.meyling.zeubor.core.nerve.SimplePotentialCalculator;
import com.meyling.zeubor.core.nerve.basis.*;

import java.util.ArrayList;
import java.util.List;


public abstract class AbstractBrain implements Brain {

    private long liveTime;
    private final List<Glia> glias;
    private final List<Neuron> input;
    private final List<Neuron> output;
    private final List<Neuron> randoms;
    private final SimplePotentialCalculator simplePotentialCalculator;
    private int eyeHeight;
    private int eyeWidth;

    public AbstractBrain() {
        input = new ArrayList<Neuron>();
        output = new ArrayList<Neuron>();
        randoms = new ArrayList<Neuron>();
        glias = new ArrayList<Glia>();
        simplePotentialCalculator = new SimplePotentialCalculator();
    }

    public int getEyeWidth() {
        return eyeWidth;
    }
    
    public void setEyeWidth(int eyeWidth) {
        this.eyeWidth = eyeWidth;
    }
    
    public int getEyeHeight() {
        return eyeHeight;
    }
    
    public void setEyeHeight(int eyeHeight) {
        this.eyeHeight = eyeHeight;
    }
    
    public List<Neuron> getInputNeurons() {
        return input;
    }
    
    public List<Neuron> getOutputNeurons() {
        return output;
    }
    
    public List<Neuron> getRandomNeurons() {
        return randoms;
    }
    
    public List<Glia> getGlias() {
        return glias;
    }
    
    protected Neuron createNeuron() {
        return createNeuron(simplePotentialCalculator);
    }
    
    protected Neuron createNeuron(final PotentialCalculator potentialCalculator) {
        final Neuron neuron = new Neuron(potentialCalculator);
        final Glia glia = new Glia(neuron);
        glias.add(glia);
        return neuron;
    }

    protected RandomFireNeuron createRandomFireNeuron() {
        final RandomFireNeuron neuron = new RandomFireNeuron(simplePotentialCalculator);
        final Glia glia = new Glia(neuron);
        glias.add(glia);
        return neuron;
    }

    protected SupressRandomFireNeuron createSupressRandomFireNeuron(final int max) {
        final SupressRandomFireNeuron neuron = new SupressRandomFireNeuron(simplePotentialCalculator, max);
        final Glia glia = new Glia(neuron);
        glias.add(glia);
        return neuron;
    }

    protected FireRandomNeuron createFireRandomNeuron(final double trigger) {
        final FireRandomNeuron neuron = new FireRandomNeuron(simplePotentialCalculator, trigger);
        final Glia glia = new Glia(neuron);
        glias.add(glia);
        return neuron;
    }

    protected CounterNeuron createCounterNeuron(final int max) {
        final CounterNeuron neuron = new CounterNeuron(simplePotentialCalculator, max);
        final Glia glia = new Glia(neuron);
        glias.add(glia);
        return neuron;
    }

    public void iterate() {
        liveTime++;
        // route neuron fire to axons
        for (Glia glia : glias) {
            Neuron neuron = glia.getNeuron();
            glia.addFireEvent(neuron.getFire());
            neuron.calculateAxonFire();
/*            
            for (Axon axon : neuron.getAxons()) {
                axon.setFire(neuron.getFire());
            }
            neuron.setFire(false);
*/            
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
            neuron.calculateFire();
        }
    }

    public void printNeurons() {
        System.out.println();
        System.out.printf("%5d |", liveTime);
        for (Glia glia : glias) {
            Neuron neuron = glia.getNeuron();
            System.out.print(" " + (neuron.getFire() ? 'X' : '0'));
            System.out.printf("%4d |", neuron.getPotential());
        }
    }
    
    public void printInput() {
        System.out.println();
        for (int i = 0; i < getEyeHeight(); i++) {
            for (int j = 0; j < getEyeWidth(); j++) {
                Neuron neuron = input.get(i * getEyeWidth() + j);
                System.out.print(" " + (neuron.getFire() ? 'X' : '0'));
            }
            System.out.println();
        }
    }

    public void printGenom(List<List<Integer>> genom) {
        System.out.println("genom:");
        for (List<Integer> line : genom) {
            for (int value : line) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void increaseRandom() {
    }

    public void resetRandom() {
    }
    
}