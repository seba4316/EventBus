package dev.utano.eventbus.listener;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The ListenerInstance class represents an instance of an event listener along with its corresponding listener class.
 * It is used to store and manage the association between an event listener and its listener class.
 *
 * @see EventListener
 * @see ListenerClass
 */
@Getter
@AllArgsConstructor
public class ListenerInstance {
	private final EventListener eventListener;
	private final ListenerClass<?> listenerClass;
}