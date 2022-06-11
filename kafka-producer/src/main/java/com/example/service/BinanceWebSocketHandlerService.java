package com.example.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;

import com.example.event.TradeEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BinanceWebSocketHandlerService implements WebSocketHandler{
	private final WebSocketClient webSocketClient;
	private final String binanceWebSocketUrl;
	private final ApplicationEventPublisher eventPublisher;
	private final ObjectMapper objectMapper;
	
	public BinanceWebSocketHandlerService(
			WebSocketClient webSocketClient,
			ApplicationEventPublisher eventPublisher,
			ObjectMapper objectMapper,
			@Value("${binance.websocket.url}") String binanceWebSocketUrl) {
		this.webSocketClient = webSocketClient;
		this.binanceWebSocketUrl = binanceWebSocketUrl;
		this.eventPublisher = eventPublisher;
		this.objectMapper = objectMapper;
	}


	@PostConstruct
	public void connectToBinance() {
		webSocketClient.doHandshake(this, binanceWebSocketUrl);
	}


	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.err.println("Connected to binance!");
	}


	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		String trade = message.getPayload().toString();
		var event = objectMapper.readValue(trade, TradeEvent.class);
		eventPublisher.publishEvent(event);
	}


	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		System.err.println(exception.getMessage());
	}


	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		System.err.println("Disconnected from binance!");
	}


	@Override
	public boolean supportsPartialMessages() {
		return false;
	}
}
