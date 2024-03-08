package dev.utano.eventbus;

import dev.utano.eventbus.annotation.EventHandler;
import dev.utano.eventbus.event.CancellableTestEvent;
import dev.utano.eventbus.event.Event;
import dev.utano.eventbus.listener.EventListener;
import dev.utano.eventbus.event.TestEvent;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode({Mode.AverageTime, Mode.Throughput})
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class SimplyEventBusBenchmark implements EventListener {

	private EventBus eventBus;
	private Event event1;
	private Event event2;

	@Setup(Level.Trial)
	public void setup() {
		eventBus = new EventBus();
		eventBus.register(this);
		event1 = new TestEvent();
		event2 = new CancellableTestEvent();
	}

	@Benchmark
	public void benchmarkEvent1() {
		eventBus.fireEvent(event1);
	}

	@Benchmark
	public void benchmarkEvent2() {
		eventBus.fireEvent(event2);
	}

	@EventHandler(handleDerivatives = false)
	public void onTestEvent(TestEvent ignored) {
	}

	@EventHandler(handleDerivatives = false)
	public void onCancellableTestEvent(CancellableTestEvent ignored) {
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(SimplyEventBusBenchmark.class.getSimpleName())
				.forks(1)
				.build();

		new Runner(opt).run();
	}
}