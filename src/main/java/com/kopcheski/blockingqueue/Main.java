package com.kopcheski.blockingqueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class Main {

	static MessageQueue queue = new MessageQueue();

	public static void main(String[] args) {

		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);

		List<Future<?>> futures = new ArrayList<>();

		futures.add(executorService.scheduleWithFixedDelay(
				new LongProducer("ABC", queue).run, 0, 100, MILLISECONDS));
		futures.add(executorService.scheduleWithFixedDelay(
				new LongProducer("XYZ", queue).run, 0, 100, MILLISECONDS));
		futures.add(executorService.scheduleWithFixedDelay(
				new LongProducer("123", queue).run, 0, 100, MILLISECONDS));

		futures.add(executorService.scheduleWithFixedDelay(
				new LongConsumer("ABC", queue).run, 0, 250, MILLISECONDS));
		futures.add(executorService.scheduleWithFixedDelay(
				new LongConsumer("XYZ", queue).run, 0, 250, MILLISECONDS));
		futures.add(executorService.scheduleWithFixedDelay(
				new LongConsumer("123", queue).run, 0, 250, MILLISECONDS));

		futures.add(executorService.scheduleAtFixedRate(
				() -> System.out.println("Queue size: " + queue.sizes()), 500, 500, MILLISECONDS));

		Executors.newSingleThreadScheduledExecutor()
				.schedule(() -> {
					System.out.println("About to start stopping the futures.");
					futures.forEach(future -> future.cancel(false));
					System.out.println("All futures have been stopped.");
				}, 5, TimeUnit.SECONDS);
	}

	static void print(String message) {
		System.out.println(Thread.currentThread().getName() + " " + message);
	}
}
