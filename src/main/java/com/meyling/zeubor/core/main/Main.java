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

import java.util.Arrays;

/**
 * ## Just prototyping main processes.
 */
public class Main {

    /**
     * Call the main loop.
     *
     * @param args    All necessary arguments.
     */
    public static void main(String[] args) {
        try {
        	Log.journal("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        	Log.journal("begin: %s", IoUtility.getUTCTimestamp());
        	Log.journal("Arguments: %s", Arrays.toString(args));
        	Runtime.getRuntime().addShutdownHook(new Thread() {
        		public void run() {
        	    	Log.journal("End 2: %s", IoUtility.getUTCTimestamp());
                	Log.journal("--------------------------------------------------------------");
        		}
        	});
        	final AbstractArgumentParser parser = new MainArgumentParser();
        	parser.parseArguments(args);
        } catch (final RuntimeException e) {
            Log.error("unexpected exception, leaving program", e);
        }
    	Log.journal("End 1: %s", IoUtility.getUTCTimestamp());
    }

}
