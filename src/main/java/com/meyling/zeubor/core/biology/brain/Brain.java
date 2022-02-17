package com.meyling.zeubor.core.biology.brain;

import com.meyling.zeubor.core.biology.nerve.Neuron;

import java.util.List;

public interface Brain {

    /**
     * Grow nerve net according to genom.
     *
     * @param genom in the following form
     *  (w, h) eye input neurons (width, height)
     *  (o) number of output neurons
     *  (a, l) number of application neurons, number of layers
     */
    public void grow(List<List<Integer>> genom);

    public List<Neuron> getInputNeurons();

    public List<Neuron> getOutputNeurons();

    public int getEyeWidth();

    public int getEyeHeight();
    
    public int getDirectionX();
    
    public int getDirectionY();
    
    public int getSpeed();
    
    public void increaseRandom();
    
    public void resetRandom();

    
    /**
     * One iteration should cost 10 ms.
     * After 2 iterations the output should be correct.
     * 
     */
    public void iterate();

    public void printInput();

    public void printNeurons();

    public void printGenom(List<List<Integer>> genom);

}