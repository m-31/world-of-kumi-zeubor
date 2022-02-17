package com.meyling.zeubor.core.biology.brain;

import java.util.List;

/**
 * Encapsulates a neural net and manages interplay with sensors and muscles. 
 */
public final class BrainImpl10 extends AbstractBrain5Output {

    private final static double LOW_RANDOM = 0.01;
    
    private double random;
    

    public BrainImpl10() {
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
        
        // create input layer neurons
        //  0 1 2 
        //  3 4 5
        //  6 7 8
        for (int i = 0; i < inputNumber; i++) {
            getInputNeurons().add(createNeuron());
        }

        // create output layer neurons
        //     0  
        //   1 2 3
        //     4 
        for (int i = 0; i < outputNumber; i++) {
            getOutputNeurons().add(createNeuron());
        }

        // random output if no input there
        getRandomNeurons().add(createSupressRandomFireNeuron(1000));
        for (int i = 0; i < inputNumber; i++) {
            int weight = 100;
            getRandomNeurons().get(0).addDentrite(getInputNeurons().get(i), weight);
        }    
        for (int i = 0; i < outputNumber; i++) {
            int weight = 100;
            if (i != 2) {
                getOutputNeurons().get(i).addDentrite(getRandomNeurons().get(0), weight);
            }
        }    

        // decide between up and down
        getRandomNeurons().add(createRandomFireNeuron());
        {
            getOutputNeurons().get(0).addDentrite(getRandomNeurons().get(1), -100);
            getRandomNeurons().get(1).addDentrite(getOutputNeurons().get(0), 50);
            getOutputNeurons().get(4).addDentrite(getRandomNeurons().get(1), -100);
            getRandomNeurons().get(1).addDentrite(getOutputNeurons().get(4), 50);
        } 

        // decide between left and right
        getRandomNeurons().add(createRandomFireNeuron());
        {    
            getOutputNeurons().get(1).addDentrite(getRandomNeurons().get(2), -100);
            getRandomNeurons().get(2).addDentrite(getOutputNeurons().get(1), 50);
            getOutputNeurons().get(3).addDentrite(getRandomNeurons().get(2), -100);
            getRandomNeurons().get(2).addDentrite(getOutputNeurons().get(3), 50);
        }

        // flip coin to go forward if decision has to be made between up and down
        getRandomNeurons().add(createFireRandomNeuron(0.5));
        {
            getOutputNeurons().get(2).addDentrite(getRandomNeurons().get(3), 100);
            getRandomNeurons().get(3).addDentrite(getOutputNeurons().get(0), 50);
            getRandomNeurons().get(3).addDentrite(getOutputNeurons().get(4), 50);
        } 
        
        // flip coin to go forward if decision has to be made between left and right
        getRandomNeurons().add(createFireRandomNeuron(0.5));
        {
            getOutputNeurons().get(2).addDentrite(getRandomNeurons().get(4), 100);
            getRandomNeurons().get(4).addDentrite(getOutputNeurons().get(1), 50);
            getRandomNeurons().get(4).addDentrite(getOutputNeurons().get(3), 50);
        } 
        
        // move forward if we hadn't for 2 cycles 
        getRandomNeurons().add(createCounterNeuron(2));
        {
            getRandomNeurons().get(5).addDentrite(getOutputNeurons().get(2), 100);
            getOutputNeurons().get(2).addDentrite(getRandomNeurons().get(5), 100);
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
            int weight = 40;
            outputInput(2, 1, weight);
            outputInput(2, 3, weight);
            outputInput(2, 5, weight);
            outputInput(2, 7, weight);
        }   
        {
            int weight = 40;
            outputInput(2, 0, weight);
            outputInput(2, 2, weight);
            outputInput(2, 6, weight);
            outputInput(2, 8, weight);
        }
        {
            int weight = -100;
            outputInput(4, 1, weight);
            outputInput(3, 3, weight);
            outputInput(1, 5, weight);
            outputInput(0, 7, weight);
        }   
        {
            int weight = -100;
            outputInput(4, 0, weight);
            outputInput(3, 0, weight);
            outputInput(1, 2, weight);
            outputInput(4, 2, weight);
            outputInput(0, 6, weight);
            outputInput(3, 6, weight);
            outputInput(0, 8, weight);
            outputInput(1, 8, weight);
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
/*        
        // random noise
        for (int i = 0; i < getInputNeurons().size(); i++) {
            if (Math.random() < random) {
                getInputNeurons().get(i).setFire(!getInputNeurons().get(i).getFire());
            }
        }
*/        
//        printOutput();
        super.iterate();
    }

    public void increaseRandom() {
        random = random * 3;
    }

    public void resetRandom() {
        random = LOW_RANDOM;
    }
}
