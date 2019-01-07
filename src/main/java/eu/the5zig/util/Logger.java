package eu.the5zig.util;

/**
 * Created by Rocco on 05/01/2019.
 */
public class Logger {

    public void info(Object message) {
        System.out.println("[Beezig] " + message);
    }

    public void error(Object message) { info(message); }
    public void fatal(Object message) { info(message); }

}
