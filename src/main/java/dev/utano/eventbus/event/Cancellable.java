package dev.utano.eventbus.event;

import dev.utano.eventbus.annotation.EventHandler;
import dev.utano.eventbus.event.priority.EventPriority;

import java.lang.reflect.Field;

/**
 * The interface 'Cancellable' represents an event with optional cancel or immediate stop propagating actions.
 * <p>
 * Cancelling an event doesn't interfere with triggering of event handlers, however, it forbids the original intended action.
 * <p>
 * On the other hand, stopping the event's immediate propagation does not prevent the original action from occurring, but it restricts the triggering of any subsequent event handlers, excluding {@link EventPriority#MONITOR}.
 * <p>
 * When an event is cancelled and its immediate propagation is stopped, no further event handlers (except for {@link EventPriority#MONITOR}) will be invoked, additionally the original action will also be cancelled.
 */
public interface Cancellable {

	/**
	 * Changes the cancellation status of the event.
	 *
	 * @param cancelled If {@code true}, the event will be marked as cancelled. If {@code false}, the event will be marked as not cancelled.
	 */
	default void setCancelled(boolean cancelled) {
		Field field = getCancelledField();
		try {
			field.setBoolean(this, cancelled);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return {@code true} if the event is cancelled, {@code false} otherwise.
	 */
	default boolean isCancelled() {
		Field field = getCancelledField();
		try {
			return field.getBoolean(this);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Modifies the immediate propagation status of the event.
	 *
	 * @param stopImmediatePropagation If {@code true}, stops the immediate propagation of the event. If {@code false}, the event will continue to propagate.
	 */
	default void setStopImmediatePropagation(boolean stopImmediatePropagation) {
		Field field = getStopImmediatePropagationField();
		try {
			field.setBoolean(this, stopImmediatePropagation);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Stops the immediate propagation of the event. The event's cancelled flag remains unaffected.
	 * <p>
	 * The act of halting an event's propagation will immediately prevent other handlers from being triggered. The cancellation status should be managed separately.
	 */
	default void stopImmediatePropagation() {
		setStopImmediatePropagation(true);
	}

	/**
	 * Checks whether the immediate propagation of this event is set to stop.
	 * If it is, no other {@link EventHandler} will be triggered except those with {@link EventPriority#MONITOR} priority.
	 *
	 * @return {@code true} if the event is set to stop immediate propagation, {@code false} otherwise.
	 */
	default boolean isStopImmediatePropagation() {
		Field field = getStopImmediatePropagationField();
		try {
			return field.getBoolean(this);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Retrieves the Field object representing the 'cancelled' field in the Event class.
	 *
	 * @return The Field object representing the 'cancelled' field.
	 */
	default Field getCancelledField() {
		return Event.CLASS.getDeclaredField("cancelled");
	}

	/**
	 * Retrieves the Field object representing the 'stopImmediatePropagation' field in the Event class.
	 *
	 * @return The Field representing the 'stopIMmediatePropagation' field in the Event class.
	 */
	default Field getStopImmediatePropagationField() {
		return Event.CLASS.getDeclaredField("stopImmediatePropagation");
	}

}