package com.example.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AlgoTradingService {

	@KafkaListener(topics = "trade-events")
	public void listen(String event) {
		System.err.println(event);
	}
}
