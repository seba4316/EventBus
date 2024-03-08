package dev.utano.eventbus.listener;

import dev.utano.eventbus.annotation.EventHandler;

/**
 * The {@code EventListener} interface represents an event listener.
 * Event listeners are used to handle events triggered by the application.
 *
 * <p>
 * This interface does not define any methods and serves as a marker interface
 * for classes that are intended to be used as event listeners.
 * Event listeners should implement this interface in order to be recognized
 * as event listeners by the application.
 * </p>
 *
 * <p>
 * Event listeners can be registered and unregistered using the {@link dev.utano.eventbus.EventBus} class.
 * </p>
 *
 * <p>
 * To create a custom event listener, you can implement the {@code EventListener} interface
 * and define event handling methods with the {@link EventHandler} annotation.
 * Event handling methods must be public and have a single parameter of the event type they handle.
 * The {@link EventHandler} annotation can be used to specify the priority of the event handler,
 * as well as whether the event handler should also handle events of subclasses of the specified event type.
 * </p>
 *
 * @see EventHandler
 * @see dev.utano.eventbus.EventBus
 */
public interface EventListener {
}