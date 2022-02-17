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

import com.meyling.zeubor.core.gui.*;
import com.meyling.zeubor.core.gui_demo.ToInfinityAndBeyond;
import joptsimple.OptionException;
import joptsimple.OptionSet;
import org.slf4j.event.Level;

import java.util.List;

import static java.util.Arrays.asList;

public class GuiArgumentParser extends AbstractArgumentParser {

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

	    	List<?> commandWithArguments = options.nonOptionArguments();
	    	if (commandWithArguments.size() == 0) {
	    		errorExit("no window type given");
	    	}
	    	final String command = commandWithArguments.get(0).toString();
	    	switch (command) {
	    	case "1":
	    		ToInfinityAndBeyond.main(new String[0]);
	    		break;
	    	case "2":	
	    		MainWindow2.main(new String[0]);
	    		break;
	    	case "3":	
	    		MainWindow3.main(new String[0]);
	    		break;
	    	case "4":	
	    		MainWindow4.main(new String[0]);
	    		break;
	    	case "5":	
	    		MainWindow5.main(new String[0]);
	    		break;
	    	case "6":	
	    		MainWindow6.main(new String[0]);
	    		break;
    		default:
    			errorExitWithHelp("unknown window type '" + command + "'");
	    	}
	    	
    	} catch (final OptionException e) {
    		errorExitWithHelp(e.getMessage());
    	}

    }
    
    public void printHelp() {
		System.out.println("Kumi Zeubors World");
		System.out.println();
		System.out.println("usage: gui [-l <level>] [-?]  <window>");
		System.out.println("Simulate virtual algae world with neuronal nets.");
		System.out.println();
		System.out.println("commands are:");
		System.out.println("   1   You can swim through our algae world.");
		System.out.println("       Pressing the left mouse button and moving around");
		System.out.println("       with the mouse changes the viewing direction.");
		System.out.println("       Scrolling the wheel changes forward and backward stepping.");
		System.out.println("       A left mouse click stoppes the movement.");
		System.out.println("       A right mouse click changes the viewing direction to the zero point.");
		System.out.println("       Exit with <ESC>.");
		System.out.println("   2   Show two simple programs that eat all the algae.");
		System.out.println("       These programs just look at the center of the screen and look if its a green");
		System.out.println("       pixel. If yes, they move in this direction. If not they spiral around this");
		System.out.println("       pixel until they find a green pixel. If the found one they change their");
		System.out.println("       direction. Also two camera windows are displayed.");
		System.out.println("   3   Show four older brain players eating algae");
		System.out.println("   4   Start gui window showing some algae eating players");
		System.out.println("   5   Latest model (Jasmine) alone in the algae world");
		System.out.println("   6   Start gui window with Ginny eating algae");
		System.out.println();
		try {
			getOptionParser().printHelpOn(System.out);
		} catch (Exception ex) {
		}
		System.out.println();
		System.out.println("Examples");
		System.out.println();
		System.out.println("call 'help <command>' or '<command> --help' for help on a specific command.");		
		System.out.println();
		System.out.println("Report bugs by sending email to <kumi.zeubor@gmx.com>");
	}

}
