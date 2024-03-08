package dev.utano.eventbus.annotation;

import dev.utano.eventbus.listener.EventListener;
import dev.utano.eventbus.event.priority.EventPriority;

import java.lang.annotation.*;

/**
 * The EventHandler annotation is used to mark a method as an event handler.
 * Event handlers are methods that will be called when a specific event occurs.
 *
 * @see EventPriority
 * @see EventListener
 * @see dev.utano.eventbus.EventBus
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface EventHandler {

	/**
	 * Returns the priority level of the event handler.
	 * <ol>
	 *     <li>{@link EventPriority#LOWEST}</li>
	 *     <li>{@link EventPriority#LOW}</li>
	 *     <li>{@link EventPriority#NORMAL}</li>
	 *     <li>{@link EventPriority#HIGH}</li>
	 *     <li>{@link EventPriority#HIGHEST}</li>
	 *     <li>{@link EventPriority#MONITOR}</li>
	 * </ol>
	 *
	 * @see EventPriority
	 * @return The priority level of the event handler.
	 */
	EventPriority priority() default EventPriority.NORMAL;

	/**
	 * Returns whether the event handler should also handle events of subclasses of the specified event type.
	 *
	 * @return {@code true} if the event handler should handle events of subclasses, {@code false} otherwise.
	 */
	boolean handleDerivatives() default true;

	/**
	 * Returns whether the event handler should ignore cancelled events.
	 *
	 * @return {@code true} if the event handler should ignore cancelled events, {@code false} otherwise.
	 * @see EventHandler
	 */
	boolean ignoreCancelled() default false;

}