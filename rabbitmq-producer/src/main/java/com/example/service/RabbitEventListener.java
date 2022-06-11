package com.example.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.example.event.TradeEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RabbitEventListener {
	private final RabbitTemplate rabbitTemplate;
	private final ObjectMapper objectMapper;

	public RabbitEventListener(RabbitTemplate rabbitTemplate,ObjectMapper objectMapper) {
		this.rabbitTemplate = rabbitTemplate;
		this.objectMapper = objectMapper;
	}

	@EventListener
	public void listenEvent(TradeEvent event) throws Exception {
		rabbitTemplate.convertAndSend("trade-exchange", null,objectMapper.writeValueAsString(event));
	}
}
