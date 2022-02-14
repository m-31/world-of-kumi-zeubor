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

import com.meyling.zeubor.core.log.Log;
import com.meyling.zeubor.core.video.ProduceVideo;
import com.meyling.zeubor.core.video.ProduceVideo2;
import joptsimple.OptionException;
import joptsimple.OptionSet;
import org.slf4j.event.Level;

import java.io.IOException;
import java.util.List;

import static java.util.Arrays.asList;

public class VideoArgumentParser extends AbstractArgumentParser {

    public void parseArguments(String[] args) {
        getOptionParser().accepts("level").withRequiredArg().ofType(Level.class)
                .describedAs("log level")
                .defaultsTo(Level.INFO);
        getOptionParser().acceptsAll( asList( "help", "?" ), "show help" ).forHelp();
        getOptionParser().posixlyCorrect(true);
        try {
            final OptionSet options = getOptionParser().parse(args);
            if (options.has("help")) {
                printHelp();
                System.exit(0);
            }

// FIXME
//	    	final Logger logger = (Logger) Log.log;
//	    	logger.setLevel((Level) options.valueOf("level"));
//
//	    	System.out.println("log level is: " + logger.getLevel());
            List<?> commandWithArguments = options.nonOptionArguments();
            if (commandWithArguments.size() == 0) {
                errorExit("no kind given");
            }
            final String command = commandWithArguments.get(0).toString();
            switch (command) {
                case "1":
                    ProduceVideo.main(new String[0]);
                    break;
                case "2":
                    ProduceVideo2.main(new String[0]);
                    break;
                default:
                    errorExitWithHelp("unknown kind '" + command + "'");
            }

        } catch (final OptionException e) {
            errorExitWithHelp(e.getMessage());
        }
    }


    public void printHelp() {
        System.out.println("usage: video [-l <level>] [-?]  <kind>");
        System.out.println("Make a video of player(s) eating algae.");
        System.out.println();
        System.out.println("values for <kind> are:");
        System.out.println("   1  Produce videos with Dido");
        System.out.println("   2  Produce video with eight different players.");
        System.out.println();
        try {
            getOptionParser().printHelpOn(System.out);
        } catch (IOException ex) {
        }
    }

}
