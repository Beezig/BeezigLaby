package eu.the5zig.mod.plugin;

import eu.the5zig.mod.event.EventHandler;

import java.lang.reflect.Method;

public class RegisteredEventHandler implements Comparable<RegisteredEventHandler>
{
    private final Object instance;
    private final Method method;
    private final EventHandler eventHandler;

    public RegisteredEventHandler(final Object instance, final Method method, final EventHandler eventHandler) {
        this.instance = instance;
        this.method = method;
        this.eventHandler = eventHandler;
    }

    public Object getInstance() {
        return this.instance;
    }

    public Method getMethod() {
        return this.method;
    }

    public EventHandler getEventHandler() {
        return this.eventHandler;
    }

    @Override
    public int compareTo(final RegisteredEventHandler o) {
        return o.eventHandler.priority().compareTo(this.eventHandler.priority());
    }
}
