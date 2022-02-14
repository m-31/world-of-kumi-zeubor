package com.meyling.zeubor.core.nerve;

import com.meyling.zeubor.core.nerve.basis.Dendrite;
import com.meyling.zeubor.core.nerve.basis.Glia;
import com.meyling.zeubor.core.nerve.basis.Neuron;
import com.meyling.zeubor.core.nerve.basis.PotentialCalculator;

import java.util.List;

public final class SimplePotentialCalculator implements PotentialCalculator  {

    public SimplePotentialCalculator() {
    }
 
    public void accumulate(Neuron neuron) {
        for (Dendrite dendrite : neuron.getDendrites()) {
            if (dendrite.getFire()) {
                final Glia g = dendrite.getAxon().getNeuron().getGlia();
                // FIXME
               
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
//                    c = dendrite.getWeight();   // FIXME
                neuron.setPotential(neuron.getPotential() + c);
            }
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
