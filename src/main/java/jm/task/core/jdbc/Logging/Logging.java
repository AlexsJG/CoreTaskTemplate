package jm.task.core.jdbc.Logging;

import java.io.IOException;
import java.util.logging.*;

public class Logging {
    public static Logger logger = Logger.getLogger("MyLogger");
    public static FileHandler handler;

    static {
        try {
            handler = new FileHandler("logfile.%u.%g.txt", 5024, 10, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.setUseParentHandlers(false);

        handler.setFormatter(new myFormatter());
        logger.addHandler(handler);
    }

    public static class myFormatter extends Formatter {
        @Override
        public String format(LogRecord record) {
            if (record.getLevel() == Level.INFO) {
                return record.getLevel() + " : " + record.getMessage() + "\n";
            } else {
                return record.getLevel() + " : " + record.getMessage() + "\n" + record.getThrown() + "\n";
            }
        }
    }
}
