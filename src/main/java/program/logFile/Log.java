package program.logFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
    private static final String fileName = "logFileCarFactory.txt";
    private static Log objLog;
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-E-MMM-yyyy HH:mm:ss.SSS");

    private Log() {
        try {
            File logFile = new File(fileName);
            logFile.createNewFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    synchronized public void writeLog(String logText) {
        try {
            Files.write(Paths.get(fileName),
                    (dateFormat.format(LocalDateTime.now()) + " --> " + logText +  "\n").getBytes(),
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    synchronized public static Log getObjLog() {
        if (objLog == null) {
            objLog = new Log();
        }
        return objLog;
    }
}
