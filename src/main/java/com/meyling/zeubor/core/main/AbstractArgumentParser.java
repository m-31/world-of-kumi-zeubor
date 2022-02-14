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

import joptsimple.OptionParser;

import java.io.IOException;

public abstract class AbstractArgumentParser {

    private OptionParser parser = new OptionParser();

    protected void setOptionParser(final OptionParser parser) {
    	this.parser = parser;
    }
    
    protected OptionParser getOptionParser() {
    	return parser;
    }
    
    public abstract void parseArguments(String[] args);
    	
    public void printHelp() {
		System.out.println("Kumi Zeubors World");
		System.out.println();
		try {
			parser.printHelpOn(System.out);
		} catch (IOException ex) {
		}
	}

	protected void errorExit(final String message) {
		System.out.println();
		System.out.println(message);
		System.exit(1);
	}
	
	protected void errorExitWithHelp(final String message) {
		System.out.println();
		printHelp();
		errorExit(message);
	}

}
