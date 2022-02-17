package com.meyling.zeubor.core.world;

import com.meyling.zeubor.core.io.IoUtility;
import com.meyling.zeubor.core.log.Log;
import com.meyling.zeubor.core.player.basis.AbstractPlayer;
import com.meyling.zeubor.core.player.basis.AbstractPlayerCreator;
import com.meyling.zeubor.core.player.brain.AdvancedBrainPlayerCreator;

import java.io.File;


/**
 * ## Just prototyping main processes.
 */
public class EvolveBrainPlayer {

    /**
     * Call the main loop.
     *
     * @param args    Not used yet.
     */
    public static void main(String[] args) {
        EvolveBrainPlayer tester = new EvolveBrainPlayer();
        if (args == null || args.length == 0) {
            tester.breed(new String[] {"inga"});
        } else {
            tester.breed(args);
        }
    }

    private PlayerCreator initialCreator;
    private BrainPlayerBreeder creator;
    private String overAllBestName;
    private int overAllBestResult;
	private final long iterations;
	private final int neighbours;
	private final double probabilty;
	private final double change;
	private final File outdir;

    public EvolveBrainPlayer() {
    	this(50000, 30, 0.3, 10d, new File(IoUtility.getUTCTimestamp()));
    }

    public EvolveBrainPlayer(final long iterations, final int neighbours, final double probabilty, final double change, final File outdir) {
    	this.iterations = iterations;
    	this.neighbours = neighbours;
    	this.probabilty = probabilty;
    	this.change = change;
    	this.outdir = outdir;
        this.initialCreator = new PlayerCreator();
        this.creator = new BrainPlayerBreeder();
        this.overAllBestName = "";
        this.overAllBestResult = 0;
        System.out.println(limit(1, 1));
        System.out.println(limit(2, 1));
        System.out.println(limit(3, 1));
        System.out.println(limit(5, 1));
        System.out.println(limit(10, 1));
        System.out.println(limit(15, 1));
        System.out.println(limit(70, 1));
        System.out.println();
        System.out.println(limit(1, 2));
        System.out.println(limit(2, 2));
        System.out.println(limit(3, 2));
        System.out.println(limit(5, 2));
        System.out.println(limit(10, 2));
        System.out.println(limit(15, 2));
        System.out.println(limit(70, 2));
        System.out.println();
        System.out.println(limit(1, 3));
        System.out.println(limit(2, 3));
        System.out.println(limit(3, 3));
        System.out.println(limit(5, 3));
        System.out.println(limit(10, 3));
        System.out.println(limit(15, 3));
        System.out.println(limit(70, 3));
        System.out.println();
        System.out.println(limit(1, 4));
        System.out.println(limit(2, 4));
        System.out.println(limit(3, 4));
        System.out.println(limit(5, 4));
        System.out.println(limit(10, 4));
        System.out.println(limit(15, 4));
        System.out.println(limit(70, 4));
        System.out.println();
        System.out.println(limit(1, 10));
        System.out.println(limit(2, 10));
        System.out.println(limit(3, 10));
        System.out.println(limit(5, 10));
        System.out.println(limit(10, 10));
        System.out.println(limit(15, 10));
        System.out.println(limit(70, 10));
        System.out.println();
        System.out.println(limit(1, 100));
        System.out.println(limit(2, 100));
        System.out.println(limit(3, 100));
        System.out.println(limit(5, 100));
        System.out.println(limit(10, 100));
        System.out.println(limit(15, 100));
        System.out.println(limit(70, 100));
    }

    public void breed(final String[] names) {
    	outdir.mkdirs();
//        for (final String name : names) {
//	        final AdvancedBrainPlayerCreator t = initialCreator.createCreatorFromJson(name);
//            IoUtility.save(new File(outdir, t.getName() + ".json"), t.getJson());
//        }    
        while (true) {
            for (final String name : names) {
	            final AdvancedBrainPlayerCreator c = initialCreator.createCreatorFromJson(name);
	            int m = measure(c);
                IoUtility.save(new File(outdir, c.getName() + "_0_" + IoUtility.getUTCTimestamp() + "_" + m + ".json"), c.getJson());
	            iter(m, c.getJson(), probabilty, 1, name);
            }    
        }    
    }
    
    private int iter(final int previous, final String json, final double changeProbabilty, final long step, final String name) {
        double l = limit(1, step);
        Log.error( step + ": we try to beat " + previous + " with changeProbability " + changeProbabilty + " and step limit " + l);
        int last = previous;
        for (int i = 0; i < neighbours; i++) {
            AdvancedBrainPlayerCreator c = creator.createCreator(json, changeProbabilty, this.change, name + "_" + step + "_" + IoUtility.getUTCTimestamp().replace('T', '_'));
            int m = measure(c);
            Log.error(step + ": " + i + ": " + m + " " + c.getName());
            checkMaximum(c.getName(), m);
            if (m >= last) {
                IoUtility.save(new File(outdir, c.getFileName().replaceAll(".json$", "_" + m + ".json")), c.getJson());
                Log.error("we tried to beat " + previous);
                Log.error("best: " + c.getName() + " with result " + m);
                Log.error("                   overallBest: " + overAllBestResult + ": " + overAllBestName);
            	return last = iter(m, c.getJson(), changeProbabilty, step + 1, name);
            }
            double limit = limit(last - m, step);
            Log.debug("limit=" + limit);
            if (Math.random() < limit) {
            	last = iter(m, c.getJson(), changeProbabilty, step + 1, name);
            	if (last > previous) {
            	    return last;
            	}
            } 	
        }
        return iter(last, json, changeProbabilty, step + 1, name);
    }

    protected double limit(final double delta, final long step) {
        double r = 1 - 1 / (1 + (1000000d / (2 * Math.pow(Math.abs(delta), 2) + 10) / iterations  / (2 + step * step / 10000)));
        System.out.println("limit(" + delta + ", " + step + ") = " + r);
        return r;
//        return Math.exp(-Math.abs(delta) /iterations * 10000 / (1-  / 1 + step * step));
    }

    private void checkMaximum(final String name, final int result) {
        if (result > overAllBestResult) {
            overAllBestName = name;
            overAllBestResult = result;
        }
    }
    
    private int measure(final AbstractPlayerCreator playerCreator) {
		return test_scenario(playerCreator, 250, 750, 10, this.iterations);
    }
    
    private int test_scenario(final AbstractPlayerCreator playerCreator, 
        final int balls, final int squares, final double factor, final long iterations) {
        final World world = new World(0, balls, squares, factor);
        final AbstractPlayer player = playerCreator.create();
        world.add(player);
        for (long i = 1; i <= iterations; i++) {
            world.iterate();
        }
        return player.getAlgae();
    }

}
