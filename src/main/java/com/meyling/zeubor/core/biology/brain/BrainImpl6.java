package com.meyling.zeubor.core.biology.brain;

import java.util.List;

/**
 * Encapsulates a neuronal net and manages interplay with sensors and muscles. 
 * 
 * TODO based on NeuronalNet3
 * 
 */
public final class BrainImpl6 extends AbstractBrain5Output {

    private final static double LOW_RANDOM = 0.01;
    private double random;
    

    public BrainImpl6() {
        super();
        random = LOW_RANDOM;
    }
 
    /**
     * Grow nerve net according to genom.
     *
     * @param genom in the following formBrainImpl6
     *  (3, 3) eye input neurons (width, height)
     *  (5) number of output neurons
     *  (0, 0) number of application neurons per layer, number of layers
     * 
     */
    public void grow(List<List<Integer>> genom) {
        System.out.println("grow neuronal net");
       
        printGenom(genom);
       
        getInputNeurons().clear();
        getOutputNeurons().clear();
        getGlias().clear();
       
        setEyeWidth(genom.get(0).get(0));
        setEyeHeight(genom.get(0).get(1));
        int inputNumber = getEyeWidth() * getEyeHeight();
        int outputNumber = genom.get(1).get(0);
       
        // create getInputNeurons() layer neurons
        //  0 1 2 
        //  3 4 5
        //  6 7 8
        for (int i = 0; i < inputNumber; i++) {
           getInputNeurons().add(createNeuron());
        }

        // create getOutputNeurons() layer neurons
        //     0  
        //   1 2 3
        //     4 
        for (int i = 0; i < outputNumber; i++) {
            getOutputNeurons().add(createNeuron());
        }
        {
            int weight = 200;
            outputInput(2, 4, weight);

        }
        {
            int weight = 200;
            outputInput(0, 1, weight);
            outputInput(1, 3, weight);
            outputInput(3, 5, weight);
            outputInput(4, 7, weight);
        }   
        {
            int weight = -101;
            outputInput(4, 1, weight);
            outputInput(3, 3, weight);
            outputInput(1, 5, weight);
            outputInput(0, 7, weight);
        }   
        {
            int weight = -101;
//            output.get(4).addDentrite(input.get(0), weight);
//            output.get(4).addDentrite(input.get(2), weight);
        }   
        {
            int weight = 100;
            outputInput(0, 0, weight);
            outputInput(1, 0, weight);
            outputInput(0, 2, weight);
            outputInput(3, 2, weight);
            outputInput(1, 6, weight);
            outputInput(4, 6, weight);
            outputInput(3, 8, weight);
            outputInput(4, 8, weight);
        }
        {
            int threshold = 100;
            getOutputNeurons().get(0).setLowerThreshold(threshold);
            getOutputNeurons().get(1).setLowerThreshold(threshold);
            getOutputNeurons().get(2).setLowerThreshold(threshold);
            getOutputNeurons().get(3).setLowerThreshold(threshold);
            getOutputNeurons().get(4).setLowerThreshold(threshold);
        }    
        {
            int threshold = 10000;
            getOutputNeurons().get(0).setHigherThreshold(threshold);
            getOutputNeurons().get(1).setHigherThreshold(threshold);
            getOutputNeurons().get(2).setHigherThreshold(threshold);
            getOutputNeurons().get(3).setHigherThreshold(threshold);
            getOutputNeurons().get(4).setHigherThreshold(threshold);
        }    
   }
    
    public void iterate() {
        // random noise
        for (int i = 0; i < getInputNeurons().size(); i++) {
            if (Math.random() < random) {
                getInputNeurons().get(i).setFire(!getInputNeurons().get(i).getFire());
            }
        }
        super.iterate();
    }
    
    public void increaseRandom() {
        random = random * 2;
    }

    public void resetRandom() {
        random = LOW_RANDOM;
    }
}
