package com.example.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class AlgoTradingService {

	@RabbitListener(queues = "trade-queue")
	public void listen(String event) {
		System.err.println(event);
	}
}
