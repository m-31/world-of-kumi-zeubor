package com.meyling.zeubor.core.brain;

import com.meyling.zeubor.core.biology.brain.Brain;
import com.meyling.zeubor.core.biology.brain.BrainImpl5;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public final class TestBrainImpl5 {

    @Test
    public void iteratesCorrectly() {

        Brain brain = getBrain();
        
        // 0 1 2
        // 3 4 5
        // 6 7 8
        
        brain.getInputNeurons().get(1).setFire(true);
        step(brain);
        assertEquals(0, brain.getDirectionX());
        assertEquals(1, brain.getDirectionY());
        assertEquals(0, brain.getSpeed());

        brain.getInputNeurons().get(7).setFire(true);
        step(brain);
        assertEquals(0, brain.getDirectionX());
        assertEquals(-1, brain.getDirectionY());
        assertEquals(0, brain.getSpeed());

        brain.getInputNeurons().get(3).setFire(true);
        step(brain);
        assertEquals(-1, brain.getDirectionX());
        assertEquals(0, brain.getDirectionY());
        assertEquals(0, brain.getSpeed());

        brain.getInputNeurons().get(5).setFire(true);
        step(brain);
        assertEquals(1, brain.getDirectionX());
        assertEquals(0, brain.getDirectionY());
        assertEquals(0, brain.getSpeed());

        brain.getInputNeurons().get(4).setFire(true);
        step(brain);
        assertEquals(0, brain.getDirectionX());
        assertEquals(0, brain.getDirectionY());
        assertEquals(1, brain.getSpeed());

        brain.getInputNeurons().get(1).setFire(true);
        brain.getInputNeurons().get(7).setFire(true);
        step(brain);

        for (int i = 0; i < 9; i++) {
            brain.getInputNeurons().get(i).setFire(true);
        }
        step(brain);
        assertEquals(0, brain.getDirectionX());
        assertEquals(0, brain.getDirectionY());
        assertEquals(1, brain.getSpeed());
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
