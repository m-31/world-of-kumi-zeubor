package com.meyling.zeubor.core.log;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.spi.ExtendedLogger;
import org.apache.logging.log4j.spi.ExtendedLoggerWrapper;
import org.apache.logging.log4j.util.StackLocatorUtil;

public class Loggers {

    private static final Marker JOURNAL = MarkerManager.getMarker("JOURNAL");
    private static final Marker APPLICATION = MarkerManager.getMarker("APPLICATION");
    private static final Marker ERROR = MarkerManager.getMarker("ERROR");

    private static class MarkerLogger extends ExtendedLoggerWrapper {

        private final Marker marker;

        public MarkerLogger(ExtendedLogger logger, Marker marker) {
            super(logger, logger.getName(), logger.getMessageFactory());
            this.marker = marker;
        }

        @Override
        public void logMessage(final String fqcn, final Level level, final Marker marker, final Message message,
                               final Throwable t) {
            super.logMessage(fqcn, level, marker != null ? marker : this.marker, message, t);
        }

    }

    public static Loggers getLoggers() {
        return new Loggers((ExtendedLogger) LogManager.getLogger(StackLocatorUtil.getCallerClass(2)));
    }

    private final Logger errorLog;
    private final Logger applicationLog;
    private final Logger journalLog;

    private Loggers(ExtendedLogger logger) {
        this.errorLog = new MarkerLogger(logger, ERROR);
        this.applicationLog = new MarkerLogger(logger, APPLICATION);
        this.journalLog = new MarkerLogger(logger, JOURNAL);
    }

    public Logger getErrorLog() {
        return errorLog;
    }

    public Logger getApplicationLog() {
        return applicationLog;
    }

    public Logger getJournal() {
        return journalLog;
    }

}
