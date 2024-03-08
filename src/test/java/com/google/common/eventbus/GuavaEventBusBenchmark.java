package com.google.common.eventbus;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode({Mode.AverageTime, Mode.Throughput})
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class GuavaEventBusBenchmark {

	private EventBus eventBus;
	private GuavaEvent event;

	@Setup(Level.Trial)
	public void setup() {
		eventBus = new EventBus();
		eventBus.register(this);
		event = new GuavaEvent();
	}

	@Subscribe
	public void testEvent(GuavaEvent ignored) {
	}

	@Benchmark
	public void benchmarkEvent() {
		eventBus.post(event);
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(GuavaEventBusBenchmark.class.getSimpleName())
				.forks(1)
				.build();

		new Runner(opt).run();
	}

}