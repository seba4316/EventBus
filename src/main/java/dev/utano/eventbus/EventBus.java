package dev.utano.eventbus;

import dev.utano.eventbus.annotation.EventHandler;
import dev.utano.eventbus.event.Cancellable;
import dev.utano.eventbus.event.Event;
import dev.utano.eventbus.event.priority.EventPriority;
import dev.utano.eventbus.listener.EventListener;
import dev.utano.eventbus.listener.ListenerClass;
import dev.utano.eventbus.listener.ListenerInstance;
import top.wavelength.betterreflection.BetterReflectionClass;
import top.wavelength.betterreflection.dumper.implementation.MethodDumper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code EventBus} class is responsible for registering event listeners,
 * unregistering event listeners, and firing events.
 * It maintains a list of {@link ListenerInstance} objects, which represent
 * an event listener along with its corresponding listener class.
 * The {@code EventBus} class provides methods to register and unregister
 * event listeners, and to fire events.
 *
 * <p>
 * Event listeners can be registered using the {@link #register(EventListener)} method,
 * which associates the event listener with its listener class.
 * Event listeners can be unregistered using the {@link #unregister(EventListener)} method,
 * which removes the event listener from the list of registered listeners.
 * The {@link #unregister(Class)} method can also be used to unregister all event listeners
 * associated with a specific listener class.
 * </p>
 *
 * <p>
 * Events can be fired using the {@link #fireEvent(Event)} method.
 * This method takes an event as a parameter and dispatches it to all registered listeners.
 * The event is dispatched to the listeners in the order of their specified {@link EventPriority}.
 * If the event is cancellable, the method also checks if the event is cancelled and
 * if the handler method should be invoked based on the event's priority.
 * </p>
 *
 * @see ListenerInstance
 * @see EventListener
 * @see BetterReflectionClass
 */
public class EventBus {

	private final List<ListenerInstance> listenerInstanceList = new ArrayList<>();

	protected final MethodDumper methodDumper = new MethodDumper();

	/**
	 * Represents the default instance of EventBus
	 */
	private static final EventBus DEFAULT_INSTANCE = new EventBus();

	/**
	 * @return the default instance of the EventBus class
	 */
	public static EventBus getDefault() {
		return DEFAULT_INSTANCE;
	}

	/**
	 * Registers the specified event listener.
	 *
	 * @param eventListener The event listeners to register.
	 * @return The EventBus.
	 */
	public EventBus register(EventListener eventListener) {
		listenerInstanceList.add(new ListenerInstance(eventListener, new ListenerClass<>(eventListener)));
		return this;
	}

	/**
	 * Unregisters the specified listener.
	 *
	 * @param eventListener The listener to unregister.
	 */
	public void unregister(EventListener eventListener) {
		listenerInstanceList.removeIf(listenerInstance -> listenerInstance.getEventListener().equals(eventListener));
	}

	/**
	 * Unregisters the specified listener class from the listener list.
	 *
	 * @param listenerClass The class of the listener to unregister.
	 */
	public void unregister(BetterReflectionClass<? extends EventListener> listenerClass) {
		listenerInstanceList.removeIf(listenerInstance -> listenerInstance.getListenerClass().equals(listenerClass));
	}

	/**
	 * Unregisters the specified listener class from the listener list.
	 *
	 * @param listenerClass The class of the listener to unregister.
	 */
	public void unregister(Class<? extends EventListener> listenerClass) {
		unregister(new BetterReflectionClass<>(listenerClass));
	}

	/**
	 * Fires the given event and processes it with the specified priority.
	 *
	 * @param event The event to be fired
	 * @param <T>   The type of the event
	 * @return The fired event
	 */
	public <T extends Event> T fireEvent(T event) {
		boolean isCancellable = event instanceof Cancellable;
		Cancellable cancellable = isCancellable ? (Cancellable) event : null;
		for (EventPriority priority : EventPriority.values())
			dispatchEvent(event, cancellable, priority);
		return event;
	}

	/**
	 * Dispatches the given event to all registered event listeners with the specified priority.
	 *
	 * @param event       The event to dispatch.
	 * @param cancellable If the event is {@link Cancellable}, the {@link Cancellable} instance of the method, null otherwise.
	 * @param priority    The priority of the event.
	 */
	private void dispatchEvent(Event event, Cancellable cancellable, EventPriority priority) {
		Method currentMethod = null;
		boolean isCancellable = cancellable != null;
		try {
			for (ListenerInstance listenerInstance : listenerInstanceList) {
				ListenerClass<?> listenerClass = listenerInstance.getListenerClass();
				for (HandlerMethod handlerMethod : listenerClass.getHandlers(isCancellable).handlerMethods) {
					currentMethod = handlerMethod.getMethod();
					if (skipHandlerInvocation(handlerMethod, event, cancellable, priority)) continue;
					currentMethod.invoke(listenerInstance.getEventListener(), event);
				}
			}
		} catch (Exception e) {
			StringBuilder description = new StringBuilder("An exception has occurred executing the method ").append(methodDumper.dump(currentMethod));
			description.append("\nEvent: ").append(event.getClass().getName());
			throw new RuntimeException(description.toString(), e);
		}
	}

	/**
	 * Checks whether to skip the invocation of a method annotated with {@link EventHandler}.
	 *
	 * @param handlerMethod The method to check.
	 * @param event         The event object.
	 * @param cancellable   The {@link Cancellable} instance of the event, null if non-cancellable.
	 * @param priority      The priority of the event handler.
	 * @return {@code true} if the invocation should be skipped, {@code false} otherwise.
	 */
	private boolean skipHandlerInvocation(HandlerMethod handlerMethod, Event event, Cancellable cancellable, EventPriority priority) {
		// Check if method is annotated and has correct priority
		if (handlerMethod.getPriority() != priority) return true;

		// If the event is cancellable
		if (cancellable != null) {
			// Resolve branching
			boolean isCancelledAndIgnoreCancelled = cancellable.isCancelled() && handlerMethod.isIgnoreCancelled();
			boolean isStoppedAndNotMonitorPriority = cancellable.isStopImmediatePropagation() && priority != EventPriority.MONITOR;

			if (isCancelledAndIgnoreCancelled || isStoppedAndNotMonitorPriority)
				return true;
		}

		// If the annotation allows derivatives but the parameter is not an instance of the event or a subclass
		if (handlerMethod.isHandleDerivatives() && !handlerMethod.getEventClass().isAssignableFrom(event.getClass()))
			return true;

		// If the annotation does not allow derivatives and the parameter is not an instance of the event
		return !handlerMethod.isHandleDerivatives() && !handlerMethod.getEventClass().getClasz().equals(event.getClass());
	}

}