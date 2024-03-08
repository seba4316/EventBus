package dev.utano.eventbus.event.priority;

import java.util.Comparator;

public class PriorityComparator implements Comparator<EventPriority> {

	@Override
	public int compare(EventPriority p1, EventPriority p2) {
		return p1.compareTo(p2);
	}

}
