package dev.utano.eventbus.event.priority;

import dev.utano.eventbus.HandlerMethod;

import java.util.Comparator;

public class MethodPriorityComparator implements Comparator<HandlerMethod> {

	@Override
	public int compare(HandlerMethod m1, HandlerMethod m2) {
		EventPriority p1 = m1.getPriority();
		EventPriority p2 = m2.getPriority();

		return p1.compareTo(p2);
	}

}