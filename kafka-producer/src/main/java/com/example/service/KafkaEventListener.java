package com.example.service;

import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.event.TradeEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KafkaEventListener {
	private final KafkaTemplate<String, String> kafkaTemplate;
	private final ObjectMapper objectMapper;

	public KafkaEventListener(KafkaTemplate<String, String> kafkaTemplate,ObjectMapper objectMapper) {
		this.kafkaTemplate = kafkaTemplate;
		this.objectMapper = objectMapper;
	}

	@EventListener
	public void listenEvent(TradeEvent event) throws Exception {
		kafkaTemplate.send("trade-events", event.getSymbol(), objectMapper.writeValueAsString(event));
	}
}
