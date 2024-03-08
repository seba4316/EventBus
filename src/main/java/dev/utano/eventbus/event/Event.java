package dev.utano.eventbus.event;

import top.wavelength.betterreflection.BetterReflectionClass;

/**
 * Represents an event.
 * Events can be used to signal the occurrence of a specific action or state change.
 */
public class Event {

	/**
	 * A flag indicating if the event was cancelled.
	 */
	private boolean cancelled;
	/**
	 * A flag indicating if the event should stop immediate propagation.
	 */
	private boolean stopImmediatePropagation;

	/**
	 * Reflection object for the Event class.
	 */
	public static final BetterReflectionClass<Event> CLASS = new BetterReflectionClass<>(Event.class);

	public static final BetterReflectionClass<Cancellable> CANCELLABLE_CLASS = new BetterReflectionClass<>(Cancellable.class);

	/*
	 * Static initializer block to change visibility of declared fields.
	 */
	static {
		CLASS.getDeclaredField("cancelled").setAccessible(true);
		CLASS.getDeclaredField("stopImmediatePropagation").setAccessible(true);
	}

}