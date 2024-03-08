package dev.utano.eventbus;

import dev.utano.eventbus.annotation.EventHandler;
import dev.utano.eventbus.event.Event;
import dev.utano.eventbus.event.priority.EventPriority;
import dev.utano.eventbus.listener.ListenerClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import top.wavelength.betterreflection.BetterReflectionClass;

import java.lang.reflect.Method;

/**
 * Represents a handler method for an event listener.
 * It encapsulates the listener class, method, event class and annotation preferences.
 */
@Getter
@AllArgsConstructor
@Builder
public class HandlerMethod {

	private final ListenerClass<?> listenerClass;
	private final Method method;
	private final BetterReflectionClass<? extends Event> eventClass;

	// Caching the annotation for significant performance boost
	private final EventPriority priority;
	private final boolean handleDerivatives;
	private final boolean ignoreCancelled;

	@SuppressWarnings("unchecked")
	public HandlerMethod(ListenerClass<?> listenerClass, Method method, EventHandler annotation) {
		this.listenerClass = listenerClass;
		this.method = method;

		this.eventClass = (BetterReflectionClass<? extends Event>) new BetterReflectionClass<>(method.getParameterTypes()[0]);
		this.priority = annotation.priority();
		this.handleDerivatives = annotation.handleDerivatives();
		this.ignoreCancelled = annotation.ignoreCancelled();
	}


}