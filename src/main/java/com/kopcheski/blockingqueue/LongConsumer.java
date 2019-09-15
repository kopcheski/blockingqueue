package com.kopcheski.blockingqueue;

import java.util.List;

import static com.kopcheski.blockingqueue.Main.print;


public class LongConsumer {

	private MessageQueue queue;

	private String machineId;

	public LongConsumer(String machineId, MessageQueue queue) {
		this.machineId = machineId;
		this.queue = queue;
	}

	Runnable run = () -> {
		List<Long> longs = queue.poll(machineId);
		if (longs.isEmpty()) {
			print("Continuing " + machineId);
			return;
		}
		for (Long aLong : longs) {
			print("Consuming: " + machineId + ">" + aLong);
		}
	};
}
