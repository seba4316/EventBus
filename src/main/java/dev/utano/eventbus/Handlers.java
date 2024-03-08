package dev.utano.eventbus;


import dev.utano.eventbus.annotation.EventHandler;
import dev.utano.eventbus.event.Event;
import dev.utano.eventbus.event.priority.MethodPriorityComparator;
import dev.utano.eventbus.listener.ListenerClass;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * The Handlers class is responsible for scanning a listener class and identifying
 * event handler methods marked with the @EventHandler annotation.
 */
@RequiredArgsConstructor
public class Handlers {

	public static final Class<EventHandler> ANNOTATION = EventHandler.class;

	@Getter
	private final ListenerClass<?> listenerClass;
	private final boolean cancellable;

	protected HandlerMethod[] handlerMethods;

	/**
	 * Scans the listener class and identifies event handler methods marked with the {@link EventHandler} annotation.
	 *
	 * @throws IllegalStateException if this class has been scanned already.
	 */
	public void scanClass() throws IllegalStateException {
		if (wasScanned())
			throw new IllegalStateException("This class has been scanned already.");

		List<HandlerMethod> methodList = new ArrayList<>();
		for (Method method : listenerClass.getMethods()) {

			// Check for EventHandler annotation
			if (!method.isAnnotationPresent(ANNOTATION)) continue;

			// Check for event parameter
			if (method.getParameterCount() != 1 || !Event.CLASS.isAssignableFrom(method.getParameterTypes()[0]))
				continue;

			// If this list is for Cancellable events and the event is not cancellable or vice-versa continue
			if (cancellable != Event.CANCELLABLE_CLASS.isAssignableFrom(method.getParameterTypes()[0])) continue;

			methodList.add(new HandlerMethod(listenerClass, method, method.getAnnotation(ANNOTATION)));
		}
		methodList.sort(new MethodPriorityComparator()); // Sorting by priority
		handlerMethods = methodList.toArray(new HandlerMethod[0]); // Converting to array for significant performance improvement
	}

	/**
 	 * @return whether this particular listener class had been scanned already.
	 */
	public boolean wasScanned() {
		return handlerMethods != null;
	}

}