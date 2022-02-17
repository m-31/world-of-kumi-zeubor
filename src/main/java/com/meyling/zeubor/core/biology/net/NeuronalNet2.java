package com.meyling.zeubor.core.biology.net;

import com.meyling.zeubor.core.biology.nerve.Axon;
import com.meyling.zeubor.core.biology.nerve.Dendrite;
import com.meyling.zeubor.core.biology.nerve.Glia;
import com.meyling.zeubor.core.biology.nerve.Neuron;

import java.util.ArrayList;
import java.util.List;

public final class NeuronalNet2 {

    private int counter;
    private final List<Glia> glias;
    private List<Neuron> input;
    private List<Neuron> output;

    public static void main(String[] argv) {
        NeuronalNet2 net = new NeuronalNet2();
        final List<List<Integer>> genom = new ArrayList<List<Integer>>();

        final List<Integer> inputParameter = new ArrayList<Integer>();
        inputParameter.add(5);
        genom.add(inputParameter);
        
        final List<Integer> outputParameter = new ArrayList<Integer>();
        outputParameter.add(1);
        genom.add(outputParameter);
        
        final List<Integer> applicationParameter = new ArrayList<Integer>();
        applicationParameter.add(5);
        applicationParameter.add(1);
        genom.add(applicationParameter);
        
        net.grow(genom);
        System.out.println("\nresult: " + net.test());
    }

    public int test() {
        int success = 0;
        for (int i = 0; i < 50 ; i++) {
            move(i, 10, 2);
            iterate();
            
//            printNeurons();
//            System.out.println(counter + " " + i);
            if (getOutputNeurons().get(0).getFire() == ((i) % 10 == 0)) {
                success++;
                System.out.print("  ok");
            } else {
                System.out.print("  failure");
            }
//            System.out.println("   " + getOutputNeurons().get(0).getFire());
        }
        return success;
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
        
    public NeuronalNet2() {
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
        
        // add initial connections 
        for (int i = 0; i < application.size(); i++) {
            if (i < input.size()) {
                application.get(i).addDentrite(input.get(i), 100);
/*                
                application.get(i).addDentrite(application.get(i), -400);  // FIXME recursive trigger
*/
/*                
                if (i - 1 > 0) {
                    application.get(i).addDentrite(input.get(i - 1), 50);
                }
                if (i + 1 < input.size()) {
                    application.get(i).addDentrite(input.get(i + 1), 50);
                }
               
                if (i - 2 > 0) {
                    application.get(i).addDentrite(input.get(i - 2), 25);
                }
                if (i + 2 < input.size()) {
                    application.get(i).addDentrite(input.get(i + 2), 25);
                }
/*                
                if (i - 3 > 0) {
                    application.get(i).addDentrite(input.get(i - 3), -100);
                }
                if (i + 3 < input.size()) {
                    application.get(i).addDentrite(input.get(i + 3), -100);
                }
                if (i - 4 > 0) {
                    application.get(i).addDentrite(input.get(i - 4), 1);
                }
                if (i + 4 < input.size()) {
                    application.get(i).addDentrite(input.get(i + 4), 1);
                }
*/                
            }
            for (int j = 0; j < output.size(); j++) {
                output.get(j).addDentrite(application.get(i), 100);
//                application.get(i).addDentrite(output.get(j), -400);
                if (i < input.size()) {
                    output.get(j).addDentrite(input.get(i), 100);
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
