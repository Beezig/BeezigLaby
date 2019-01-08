/*
 *    Copyright 2016 5zig
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package eu.the5zig.mod.plugin;

import eu.the5zig.mod.event.Cancelable;
import eu.the5zig.mod.event.Event;
import eu.the5zig.mod.event.EventHandler;

import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.util.*;

public class PluginManager {

	private Map<Class<? extends Event>, List<RegisteredEventHandler>> registeredEvents = new HashMap<>();

	/**
	 * Registers a class as a listener. This method scans through all methods of the specified class and registers all methods
	 * that are annotated by the {@link eu.the5zig.mod.event.EventHandler} annotation.
	 *
	 * @param plugin   the plugin instance.
	 * @param listener an instance of the listener class that should be registered.
	 */
	public void registerListener(Object plugin, Object listener) {
		registerListener(listener);
	}

	/**
	 * Unregisters a listener class instance.
	 *
	 * @param plugin   the plugin instance.
	 * @param listener the listener class instance that should be unregistered.
	 */
	void unregisterListener(Object plugin, Object listener) {}

	/**
	 * Unregisters all listeners of a plugin.
	 *
	 * @param plugin the plugin instance.
	 */
	void unregisterListener(Object plugin) {}

	/**
	 * Fires an event and calls all methods that are listening on it.
	 *
	 * @param event the event that should be fired.
	 * @return the fired event (for convenience).
	 */
	public <T extends Event> T fireEvent(T event) {

		try {
			final List<RegisteredEventHandler> eventHandlers = this.registeredEvents.get(event.getClass());
			if (eventHandlers == null) {
				return event;
			}
			for (final RegisteredEventHandler eventHandler : eventHandlers) {
				if (event instanceof Cancelable && ((Cancelable)event).isCancelled() && eventHandler.getEventHandler().ignoreCancelled()) {
					continue;
				}
				try {
					eventHandler.getMethod().invoke(eventHandler.getInstance(), event);
				}
				catch (Throwable t) {
					t.printStackTrace();
				}

			}
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
		return event;
	}

	private void registerListener(final Object listener) {
		try {
			final Class<?> clazz = listener.getClass();
			for (final Method method : clazz.getMethods()) {
				if (method.isAnnotationPresent(EventHandler.class) && method.getParameterTypes().length == 1 && Event.class.isAssignableFrom(method.getParameterTypes()[0])) {
					final Class<? extends Event> eventClass = (Class<? extends Event>)method.getParameterTypes()[0];
					final EventHandler eventHandler = method.getAnnotation(EventHandler.class);
					if (!this.registeredEvents.containsKey(eventClass)) {
						this.registeredEvents.put(eventClass, new ArrayList<>());
					}
					final List<RegisteredEventHandler> eventHandlers = this.registeredEvents.get(eventClass);
					eventHandlers.add(new RegisteredEventHandler(listener, method, eventHandler));
					Collections.sort(eventHandlers);
				}
			}
		}
		catch (Throwable throwable) {
			throw new RuntimeException("Could not register listener!", throwable);
		}
	}

	public Class getClassByName(String name, URLClassLoader ignored) {
		try {
			return Class.forName(name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
