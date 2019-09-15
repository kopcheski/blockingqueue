package com.kopcheski.blockingqueue;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LongProducer {

	private MessageQueue queue;

	private String machineId;

	private long counter = 0;

	public LongProducer(String machineId, MessageQueue queue) {
		this.queue = queue;
		this.machineId = machineId;
	}

	Runnable run = () -> {
		List<Long> longList = Stream
				.of(counter++, counter++, counter++)
				.collect(Collectors.toList());
		if (queue.add(machineId, longList)) {
			print(String.format("Produced %s-%s...", machineId, longList));
		} else {
			print(String.format("Produced and REFUSED %s-%s...", machineId, longList));
			counter -= 3;
		}
	};

	static void print(String message) {
		System.out.println(Thread.currentThread().getName() + " " + message);
	}
}
