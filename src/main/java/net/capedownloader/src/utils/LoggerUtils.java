package net.capedownloader.src.utils;

public class LoggerUtils {

    public static void info(String info) {
        System.out.println("[INFO] " + info);
    }

    public static void warning(String warning) {
        System.out.println("[WARNING] " + warning);
    }

    public static void error(String error) {
        System.err.println("[ERROR] " + error);
    }

}
