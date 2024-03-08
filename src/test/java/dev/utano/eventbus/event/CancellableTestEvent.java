package dev.utano.eventbus.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancellableTestEvent extends TestEvent implements Cancellable {

	private boolean monitored;

}