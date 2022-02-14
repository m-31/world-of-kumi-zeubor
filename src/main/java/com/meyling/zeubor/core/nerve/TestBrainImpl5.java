package com.meyling.zeubor.core.nerve;

import java.util.ArrayList;
import java.util.List;

public final class TestBrainImpl5 {


    public static void main(String[] argv) {
        Brain brain = getBrain();
        
        // 0 1 2
        // 3 4 5
        // 6 7 8
        
        brain.getInputNeurons().get(1).setFire(true);
        step(brain);
        
        brain.getInputNeurons().get(7).setFire(true);
        step(brain);

        brain.getInputNeurons().get(3).setFire(true);
        step(brain);

        brain.getInputNeurons().get(5).setFire(true);
        step(brain);

        brain.getInputNeurons().get(4).setFire(true);
        step(brain);

        brain.getInputNeurons().get(1).setFire(true);
        brain.getInputNeurons().get(7).setFire(true);
        step(brain);

        for (int i = 0; i < 9; i++) {
            brain.getInputNeurons().get(i).setFire(true);
        }
        step(brain);
    }

    private static void step(Brain brain) {
        brain.printInput();
        brain.iterate();
        brain.printNeurons();
        System.out.println();
        System.out.println(" x:" + brain.getDirectionX() + " y:" + brain.getDirectionY() + " z:" + brain.getSpeed());
    }    

    public static Brain getBrain() {
        Brain brain = new BrainImpl5();
    
        final List<List<Integer>> genom = new ArrayList<List<Integer>>();
    
        final List<Integer> inputParameter = new ArrayList<Integer>();
        inputParameter.add(3);
        inputParameter.add(3);
        genom.add(inputParameter);
        
        final List<Integer> outputParameter = new ArrayList<Integer>();
        outputParameter.add(5);
        genom.add(outputParameter);
        
        brain.grow(genom);
        return brain;
    }
}
