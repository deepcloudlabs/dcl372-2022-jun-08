package com.example.lottery.controller;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.example.lottery.aspect.Profiled;
import com.example.lottery.service.LotteryService;

@RestController
@RequestScope
@RequestMapping("/numbers")
@CrossOrigin
@Validated
@Profiled
public class LotteryRestController {
	private final LotteryService lotteryService;
	
	public LotteryRestController(LotteryService lotteryService) {
		this.lotteryService = lotteryService;
		System.err.println(lotteryService.getClass());
	}

	// http://localhost:8100/numbers?column=10
	@GetMapping(params="column")
	@Cacheable(value = "numbers", key = "#column")	
	public List<List<Integer>> getLotteryNumbers(@RequestParam @Min(5) @Max(25) int column){
		return lotteryService.draw(column);	
	}
}
