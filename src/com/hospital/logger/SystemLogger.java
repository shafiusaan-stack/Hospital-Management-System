package com.hospital.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SystemLogger {
    private static SystemLogger instance;
    private PrintWriter writer;

    private SystemLogger() {
        try {
            File dir = new File("logs");
            if (!dir.exists()) dir.mkdirs();
            writer = new PrintWriter(new FileWriter(new File(dir, "system.log"), true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized SystemLogger getInstance() {
        if (instance == null) instance = new SystemLogger();
        return instance;
    }

    private String stamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public void logInfo(String msg) {
        writer.println("[INFO] " + stamp() + " - " + msg);
        writer.flush();
    }

    public void logError(String msg) {
        writer.println("[ERROR] " + stamp() + " - " + msg);
        writer.flush();
    }
}
