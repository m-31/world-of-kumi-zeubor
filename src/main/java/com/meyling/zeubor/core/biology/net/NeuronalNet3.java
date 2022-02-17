package com.meyling.zeubor.core.biology.net;

import com.meyling.zeubor.core.biology.nerve.Axon;
import com.meyling.zeubor.core.biology.nerve.Dendrite;
import com.meyling.zeubor.core.biology.nerve.Glia;
import com.meyling.zeubor.core.biology.nerve.Neuron;

import java.util.ArrayList;
import java.util.List;

public final class NeuronalNet3 {

    private int counter;
    private final List<Glia> glias;
    private List<Neuron> input;
    private List<Neuron> output;

    public static void main(String[] argv) {
        NeuronalNet3 net = new NeuronalNet3();
        final List<List<Integer>> genom = new ArrayList<List<Integer>>();

        final List<Integer> inputParameter = new ArrayList<Integer>();
        inputParameter.add(5);
        genom.add(inputParameter);
        
        final List<Integer> outputParameter = new ArrayList<Integer>();
        outputParameter.add(1);
        genom.add(outputParameter);
        
        final List<Integer> applicationParameter = new ArrayList<Integer>();
        applicationParameter.add(5);
        applicationParameter.add(2);
        genom.add(applicationParameter);
        
        net.grow(genom);
        double s = 0;
        double r = 0;
        s += (r = net.test(10, 1));
        System.out.println("\nresult: " + r);
        s += (r = net.test(10, 2));
        System.out.println("\nresult: " + r);
        s += (r = net.test(10, 3));
        System.out.println("\nresult: " + r);
        s += (r = net.test(10, 4));
        System.out.println("\nresult: " + r);
        s += (r = net.test(5, 1));
        System.out.println("\nresult: " + r);
        s += (r = net.test(7, 2));
        System.out.println("\nresult: " + r);
        s += (r = net.test(9, 3));
        System.out.println("\nresult: " + r);
        s += (r = net.test(12, 4));
        System.out.println("\nresult: " + r);
        System.out.println();
        System.out.println("\nOverall: " + ( s / 8));
    }

    public double test(int frequency, int width) {
        int success = 0;
        int number = 150;
        for (int i = 0; i < number ; i++) {
            move(i, frequency, width);
            iterate();
            if (getOutputNeurons().get(0).getFire() == ((i / frequency + width > 1) && (i / frequency - 1 <= input.size()) && ((i - 1) % frequency == 0))) {
                success++;
                System.out.print("  ok");
            } else {
                System.out.print("  failure " + i + ", " + frequency + ", " + width);
            }
        }
        return (double) success / number * 100;
    }

    public void move(int time, int frequence, int width) {
        int j = time / frequence - 1;
        for (int k = 0; k < getInputNeurons().size(); k++) {
            boolean fire = false;
            for (int w = 0; w < width; w++) {
                if (k == j + w) {
                    fire = true;
                }
            }
            getInputNeurons().get(k).setFire(fire);
        }
    }
        
    public NeuronalNet3() {
        input = new ArrayList<Neuron>();
        output = new ArrayList<Neuron>();
        glias = new ArrayList<Glia>();
    }
 
    /**
     * Grow nerve net according to genom.
     *
     * @param genom in the following form
     *  (5) number of input neurons
     *  (1) number of output neurons
     *  (5, 1) number of application neurons, number of layers
     * 
     */
    public void grow(List<List<Integer>> genom) {
        System.out.println("grow neuronal net");
        
        printGenom(genom);
        
        counter = 0;
        input.clear();
        output.clear();
        glias.clear();
        
        final List<Neuron> application = new ArrayList<Neuron>();
        
        int inputNumber = genom.get(0).get(0);
        int outputNumber = genom.get(1).get(0);
        int applicationNumber = genom.get(2).get(0);
        int applicationLayers = genom.get(2).get(1);
        
        // create input layer neurons
        //  01 02 03 04 05
        for (int i = 0; i < inputNumber; i++) {
            input.add(createNeuron());
        }

        // create application layer neurons
        for (int i = 0; i < applicationLayers; i++) {
            for (int j = 0; j < applicationNumber; j++) {
                application.add(createNeuron());
            }
        }
        
        // create application layer neurons
        for (int i = 0; i < outputNumber; i++) {
            output.add(createNeuron());
        }

        // first application layer
        if (applicationLayers > 0) {
            for (int i = 0; i < applicationNumber; i++) {
                if (i < input.size()) {
                    application.get(i).addDentrite(input.get(i), 100);
                }
            }
        }
        // second application layer
        if (applicationLayers > 1) {
            for (int i = 0; i < applicationNumber; i++) {
                application.get(i + applicationNumber).addDentrite(application.get(i), 100);
                if (i < input.size()) {
                    application.get(i + applicationNumber).addDentrite(input.get(i), 100);
                }
            }
        }
        // output layer
        for (int i = 0; i < applicationNumber; i++) {
            for (int k = 0; k < output.size(); k++) {
                output.get(k).setHigherThreshold(applicationNumber * 100 * 2);
                output.get(k).addDentrite(application.get(i + applicationNumber * (applicationLayers - 1)), 100);
                if (applicationLayers < 2) {
                    if (i < input.size()) {
                        output.get(k).addDentrite(input.get(i), 100);
                    }
                }
            }
        }
    }
    
    public List<Neuron> getInputNeurons() {
        return input;
    }
    
    public List<Neuron> getOutputNeurons() {
        return output;
    }
    
    public Neuron createNeuron() {
        final Neuron neuron = new Neuron();
        final Glia glia = new Glia(neuron);
        glias.add(glia);
        return neuron;
    }

    /**
     * One iteration should cost 10 ms.
     * After 2 iterations the output should be correct.
     * 
     */
    public void iterate() {
        // route neuron fire to axons
        for (Glia glia : glias) {
            Neuron neuron = glia.getNeuron();
            glia.addFireEvent(neuron.getFire());
            for (Axon axon : neuron.getAxons()) {
                axon.setFire(neuron.getFire());
            }
//            neuron.setFire(false);
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
                    c = dendrite.getWeight();   // FIXME
                    neuron.setPotential(neuron.getPotential() + c);
                }
            }
        }
        printNeurons();
        for (Glia glia : glias) {
            Neuron neuron = glia.getNeuron();
            if (input.contains(neuron)) {
                continue;
            }
            neuron.setFire(neuron.getPotential() >= neuron.getLowerThreshold() && neuron.getPotential() < neuron.getHigherThreshold());
            if (neuron.getPotential() >= neuron.getLowerThreshold()) {
                neuron.setPotential(neuron.getPotential() - neuron.getLowerThreshold());
                neuron.setPotential(0);   // reset potential if firing  // FIXME
            } else {
                neuron.setPotential(0);   // reset potential if not firing
            }
        }
    }
    
    public void printNeurons() {
        System.out.println();
        System.out.printf("%5d |", ++counter);
        for (Glia glia : glias) {
            Neuron neuron = glia.getNeuron();
            System.out.print(" " + (neuron.getFire() ? 'X' : '0'));
            System.out.printf("%4d |", neuron.getPotential());
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
}
