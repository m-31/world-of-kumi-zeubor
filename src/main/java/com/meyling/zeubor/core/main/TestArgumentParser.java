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
import com.meyling.zeubor.core.log.Log;
import com.meyling.zeubor.core.player.test.TestBrainPlayer;
import joptsimple.OptionException;
import joptsimple.OptionSet;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static java.util.Arrays.asList;

public class TestArgumentParser extends AbstractArgumentParser {

    public void parseArguments(String[] args) {
        getOptionParser().accepts("runs").withRequiredArg().ofType(Integer.class)
            .describedAs("run scenarios this often")
            .defaultsTo(10);
        getOptionParser().accepts("level").withRequiredArg().ofType(Level.class)
                    .describedAs("log level")
                    .defaultsTo(Level.INFO);	// FIXME set default log level here???
        getOptionParser().accepts("iterations").withRequiredArg().ofType(Long.class)
                    .describedAs("run scenarios with this iteration number")
                    .defaultsTo(1000000l);
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
            final Logger logger = (Logger) Log.log;
            Configurator.setAllLevels(LogManager.getRootLogger().getName(), (Level) options.valueOf("level"));
            System.out.println("log level is: " + logger.getLevel());

            final long iterations = (Long) options.valueOf("iterations");
            System.out.println("iterations is:  " + iterations);

            File outdir = (File) options.valueOf("outdir");
            System.out.println("outdir is:    " + outdir);

            int runs = (Integer) options.valueOf("runs");
            System.out.println("runs is:      " + runs);
            
            List<?> ar = options.nonOptionArguments();

            final TestBrainPlayer tester = new TestBrainPlayer(runs, outdir, new MainContext(), iterations);
            for (Object name : options.nonOptionArguments()) {       
                tester.test(name.toString());
            }    
        } catch (final OptionException e) {
            errorExitWithHelp(e.getMessage());
        }
    }
    
    public void printHelp() {
		System.out.println("Kumi Zeubors World");
		System.out.println();
		System.out.println("usage: test [-l <level>] [-?]  [-runs <runs>] [-iterations <iterations>] [-outdir <outdir>");
		System.out.println();
		try {
			getOptionParser().printHelpOn(System.out);
		} catch (IOException ex) {
		}
		System.out.println();
		System.out.println("Examples");
		System.out.println();
		System.out.println("call 'help <command>' or '<command> --help' for help on a specific command.");		
		System.out.println();
		System.out.println("Report bugs by sending email to <kumi.zeubor@gmx.com>");
	}

}
