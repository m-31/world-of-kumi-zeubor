/* This file is part of the project "zeubor" - http://www.meyling.com/zeubor
 *
 * Copyright (C) 2014-2015  Michael Meyling
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.meyling.zeubor.core.main;

import com.meyling.zeubor.core.io.IoUtility;
import com.meyling.zeubor.core.player.evolution.EvolveBrainPlayer;
import joptsimple.OptionException;
import joptsimple.OptionSet;

import java.io.File;
import java.util.List;

import static java.util.Arrays.asList;

public class BreedArgumentParser extends AbstractArgumentParser {

	
    public void parseArguments(String[] args) {
        getOptionParser().accepts("iterations").withRequiredArg().ofType(Long.class)
                .describedAs("number of iterations for testing a new player")
                .defaultsTo(10000l);
        getOptionParser().accepts("neighbours").withRequiredArg().ofType(Integer.class)
	                .describedAs("number of neighbours for each breeding")
	                .defaultsTo(20);
        getOptionParser().accepts("probability").withRequiredArg().ofType(Double.class)
	                .describedAs("initial change probability for each factor")
	                .defaultsTo(0.1);
        getOptionParser().accepts("change").withRequiredArg().ofType(Double.class)
	                .describedAs("initial maximum change range")
	                .defaultsTo(100d);
        getOptionParser().accepts("probability").withRequiredArg().ofType(Double.class)
	                .describedAs("initial change probability for each factor")
	                .defaultsTo(0.1);
        getOptionParser().accepts("outdir").withRequiredArg().ofType(File.class)
	                .describedAs("directory for results")
	                .defaultsTo(new File(IoUtility.getUTCTimestamp()));
        getOptionParser().acceptsAll( asList( "help", "?" ), "show help" ).forHelp();
        getOptionParser().posixlyCorrect(true);
        try {
            final OptionSet options = getOptionParser().parse(args);
            if (options.has("help")) {
                printHelp();
                System.exit(0);
            }
            final long iterations = (Long) options.valueOf("iterations");
            System.out.println("iterations is:  " + iterations);

            final int neighbours = (Integer) options.valueOf("neighbours");
            System.out.println("neighbours is:  " + neighbours);

            final double probability = (Double) options.valueOf("probability");
            System.out.println("probability is: " + probability);

            final double change = (Double) options.valueOf("change");
            System.out.println("change is:      " + change);

            final File outdir = (File) options.valueOf("outdir");
            System.out.println("outdir is:      " + outdir);
            
            
            List<?> ar = options.nonOptionArguments();
	    	if (ar.size() == 0) {
	    		errorExit("no players to evolve given");
	    	}
            final EvolveBrainPlayer breeder = new EvolveBrainPlayer(iterations, neighbours, probability, change, outdir);
            ar.toArray(new String[0]);
            breeder.breed(ar.toArray(new String[0]));

        } catch (final OptionException e) {
            errorExitWithHelp(e.getMessage());
        }
    }


    public void printHelp() {
		System.out.println("usage: breed [-?][-i <iterations>][-n <neigbours>][-p <probability>][-c <change>][-o <outdir>] <player 1> .. <player n>");
		System.out.println("Breed new players out of given ones.");
		System.out.println();
		try {
			getOptionParser().printHelpOn(System.out);
		} catch (Exception ex) {
		}
		System.out.println();
		System.out.println("Examples");
		System.out.println();
		System.out.println("--level ERROR breed --iterations 100000 --change 10 --probability 0.08 inga");	
		System.out.println("--level ERROR breed --iterations  50000 --change 10 --probability 0.5  inga");
	}

}
