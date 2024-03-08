package dev.utano.eventbus.listener;

import dev.utano.eventbus.Handlers;
import lombok.Getter;
import top.wavelength.betterreflection.BetterReflectionClass;

/**
 * Responsible for caching {@link dev.utano.eventbus.annotation.EventHandler} methods within a class.
 * Separates them between regular and cancellable events for performance reasons.
 *
 * @param <T> the class type which extends {@link EventListener}
 */
@Getter
public class ListenerClass<T extends EventListener> extends BetterReflectionClass<T> {

	private final Handlers regularHandlers;
	private final Handlers cancellableHandlers;

	/**
	 * Initialize a new instance of ListenerClass with event listener.
	 *
	 * @param listener event listener
	 * @throws ClassCastException if listener type does not extend {@link EventListener}
	 */
	@SuppressWarnings("unchecked")
	public ListenerClass(T listener) {
		super((Class<T>) listener.getClass());
		regularHandlers = new Handlers(this, false);
		cancellableHandlers = new Handlers(this, true);

		regularHandlers.scanClass();
		cancellableHandlers.scanClass();
	}

	public Handlers getHandlers(boolean cancellable) {
		return cancellable ? cancellableHandlers : regularHandlers;
	}

}