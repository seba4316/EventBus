package dev.utano.eventbus.event.priority;

/**
 * The EventPriority enum represents the priority levels for event handling.
 * <p>
 * The priority levels are as follows:
 * - LOWEST: The lowest priority level.
 * - LOW: Lower than the normal priority level.
 * - NORMAL: The default priority level.
 * - HIGH: Higher than the normal priority level.
 * - HIGHEST: The highest priority level.
 * - MONITOR: A priority level used for monitoring events without modifying them.
 * <p>
 * The priority level indicates their say on the Event's result and determines the order in which event handlers are executed.
 * Handlers with lower priority levels are executed before handlers with higher priority levels.
 */
public enum EventPriority {

	LOWEST,
	LOW,
	NORMAL,
	HIGH,
	HIGHEST,
	/**
	 * The MONITOR priority level is used for event handlers that monitor events without making any modifications.
	 * Event handlers with MONITOR priority are executed after all other event handlers have finished.
	 * They provide a way to observe events without interfering with the normal event flow.
	 * <p>
	 * EventHandlers with the MONITOR priority will ALWAYS be triggered
	 * regardless of their cancelled and/or stop immediate propagation status.
	 *
	 * @see EventPriority
	 */
	MONITOR;

}