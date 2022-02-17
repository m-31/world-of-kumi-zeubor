package com.meyling.zeubor.core.nerve.brain;


/**
 * For brains with 5 output neurons:
 * 
 *    0
 *  1 2 3
 *    4
 */
public abstract class AbstractBrain5Output extends AbstractBrain {

    public AbstractBrain5Output() {
        super();
    }

    public void outputInput(int out, int in, int weight) {
        getOutputNeurons().get(out).addDentrite(getInputNeurons().get(in), weight);
    }

    // output layer neurons
    //     0  
    //   1 2 3
    //     4 
    
    
    public int getDirectionX() {
        if (getOutputNeurons().get(1).getFire()) {
            return (getOutputNeurons().get(3).getFire() ? 0 : -1);
        } else {
            return (getOutputNeurons().get(3).getFire() ? 1 : 0);
        }
    }

    public int getDirectionY() {
        if (getOutputNeurons().get(0).getFire()) {
            return (getOutputNeurons().get(4).getFire() ? 0 : 1);
        } else {
            return (getOutputNeurons().get(4).getFire() ? -1 : 0);
        }
    }

    public int getSpeed() {
        return (getOutputNeurons().get(2).getFire() ? 1 : 0);
    }
    
    public void printOutput() {
        System.out.println();
        System.out.print((getInputNeurons().get(0).getFire() ? "X" : "O"));
        System.out.print((getInputNeurons().get(1).getFire() ? "X" : "O"));
        System.out.print((getInputNeurons().get(2).getFire() ? "X" : "O"));
        System.out.print("   ");
        System.out.println(" " + (getOutputNeurons().get(0).getFire() ? "X" : "O") + " ");
        System.out.print((getInputNeurons().get(3).getFire() ? "X" : "O"));
        System.out.print((getInputNeurons().get(4).getFire() ? "X" : "O"));
        System.out.print((getInputNeurons().get(5).getFire() ? "X" : "O"));
        System.out.print("   ");
        System.out.print((getOutputNeurons().get(1).getFire() ? "X" : "O"));
        System.out.print((getOutputNeurons().get(2).getFire() ? "X" : "O"));
        System.out.print((getOutputNeurons().get(3).getFire() ? "X" : "O"));
        System.out.println();
        System.out.print((getInputNeurons().get(6).getFire() ? "X" : "O"));
        System.out.print((getInputNeurons().get(7).getFire() ? "X" : "O"));
        System.out.print((getInputNeurons().get(8).getFire() ? "X" : "O"));
        System.out.print("   ");
        System.out.println(" " + (getOutputNeurons().get(4).getFire() ? "X" : "O") + " ");
    }

}