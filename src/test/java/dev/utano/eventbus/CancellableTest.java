package dev.utano.eventbus;

import dev.utano.eventbus.annotation.EventHandler;
import dev.utano.eventbus.event.CancellableTestEvent;
import dev.utano.eventbus.event.priority.EventPriority;
import dev.utano.eventbus.listener.EventListener;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CancellableTest implements EventListener {

	@Test
	public void stopImmediatePropagationTest() {
		CancellableTestEvent event = new CancellableTestEvent();
		assertFalse(event.isStopImmediatePropagation(), "Event should not be set to stop propagation by default");
		event.stopImmediatePropagation();
		assertTrue(event.isStopImmediatePropagation(), "Event should be set to stop propagation after calling stopImmediatePropagation");
		assertFalse(event.isCancelled(), "Event should not be cancelled for stopping immediate propagation.");
	}

	@Test
	public void setStopImmediatePropagationTest() {
		CancellableTestEvent event = new CancellableTestEvent();
		assertFalse(event.isStopImmediatePropagation(), "Event should not be set to stop propagation by default.");
		event.setStopImmediatePropagation(true);
		assertTrue(event.isStopImmediatePropagation(), "Event should be set to stop propagation after setting stopImmediatePropagation to true.");
		assertFalse(event.isCancelled(), "Event should not be cancelled for stopping immediate propagation.");
	}

	@Test
	public void propagationTest() {
		EventBus eventBus = EventBus.getDefault().register(this);

		CancellableTestEvent event = new CancellableTestEvent();
		assertFalse(event.isMonitored(), "The event's monitored status should not be initialized to true.");

		eventBus.fireEvent(event);

		assertTrue(event.isStopImmediatePropagation(), "The event's propagation status should be true.");
		assertFalse(event.isCancelled(), "The event should not be cancelled by stopping its propagation only.");
		assertTrue(event.isMonitored(), "The monitor event was never triggered.");
	}

	@EventHandler
	public void propagationStopper(CancellableTestEvent event) {
		assertFalse(event.isStopImmediatePropagation(), "Event should not be set to stop propagation.");
		event.setStopImmediatePropagation(true);
		assertTrue(event.isStopImmediatePropagation(), "Event immediate propagation flag should be true.");
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void secondPropagation(CancellableTestEvent event) {
		fail("The event's propagation should have been stopped by now. Flag: " + event.isStopImmediatePropagation());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void monitor(CancellableTestEvent event) {
		event.setMonitored(true);
	}

}