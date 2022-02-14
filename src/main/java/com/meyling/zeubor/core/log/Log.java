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
package com.meyling.zeubor.core.log;

import org.apache.logging.log4j.Logger;

/**
 * Generate log, journal and debug output.
 */
public final class Log {
    
    public static final Logger log = Loggers.getLoggers().getApplicationLog();

    public static final Logger journal = Loggers.getLoggers().getJournal();

    private static final Logger errorLog = Loggers.getLoggers().getErrorLog();

    public static void log(final String message) {
        log.info(message);
    }

    public static void log(final String format, final Object... arguments) {
        log.info(String.format(format, arguments));
    }

    public static void debug(final String message) {
        log.debug(message);
    }

    public static void debug(final String format, final Object... arguments) {
        log.debug(String.format(format, arguments));
    }

    public static void error(final String message, final Throwable error) {
        errorLog.error(message, error);
        log.error(message, error);
    }

    public static void error(final String message) {
        errorLog.error(message);
        log.error(message);
    }

    public static void journal(final String message) {
        journal.info(message);
    }

    public static void journal(final String format, final Object... arguments) {
        journal.info(String.format(format, arguments));
    }

}


