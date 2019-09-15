package com.kopcheski.blockingqueue;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class MessageQueue {

	private Map<String, BlockingQueue> messageMap;

	public MessageQueue() {
		this.messageMap = new ConcurrentHashMap<>();
	}

	public boolean add(String machineId, List<Long> numbers) {
		BlockingQueue blockingQueue = this.messageMap.get(machineId);
		if (blockingQueue == null) {
			blockingQueue = new ArrayBlockingQueue(10);
		}
		messageMap.put(machineId, blockingQueue);
		return blockingQueue.offer(numbers);
	}

	public List<Long> poll(String machineId) {
		BlockingQueue blockingQueue = messageMap.get(machineId);
		if (blockingQueue == null)
			return Collections.emptyList();

		return (List<Long>) blockingQueue.poll();
	}

	public String sizes() {
		String message = "";
		for (String s : messageMap.keySet()) {
			message += s + ">" + messageMap.get(s).size() + "\n";
		}
		return message;
	}
}
