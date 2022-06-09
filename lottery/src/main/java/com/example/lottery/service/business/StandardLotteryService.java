package com.example.lottery.service.business;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.lottery.service.LotteryService;
import com.example.lottery.service.QualityLevel;
import com.example.lottery.service.RandomNumberService;
import com.example.lottery.service.ServiceQuality;

@SuppressWarnings("unused")
@Service
public class StandardLotteryService implements LotteryService {
	private final RandomNumberService randomNumberService;
	private final int lotteryMax;
	private final int lotterySize;
	private final List<RandomNumberService> randomNumberServices;
	
	public StandardLotteryService(
	    // @ServiceQuality(QualityLevel.SECURE)
		RandomNumberService randomNumberService,
		final List<RandomNumberService> randomNumberServices,
		@Value("${lottery.max}") int lotteryMax, 
		@Value("${lottery.size}") int lotterySize) {
		this.randomNumberService = randomNumberService;
		this.lotteryMax = lotteryMax;
		this.lotterySize = lotterySize;
		this.randomNumberServices = randomNumberServices;
		System.err.println(randomNumberService.getClass());
	}

	
	@Override
	public List<Integer> draw() {
		//var randomNumberService = randomNumberServices.get(ThreadLocalRandom.current().nextInt(0, 2));
		return IntStream.generate( () -> randomNumberService.generate(1, lotteryMax))
				        .distinct()
				        .limit(lotterySize)
				        .sorted()
				        .boxed()
				        .toList();
	}

	@Override
	public List<List<Integer>> draw(int column) {
		return IntStream.range(0, column)
				        .mapToObj( i -> this.draw())
				        .toList();
	}

}
