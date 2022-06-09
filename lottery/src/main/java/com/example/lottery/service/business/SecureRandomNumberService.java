package com.example.lottery.service.business;

import java.security.SecureRandom;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.example.lottery.service.RandomNumberService;

@SuppressWarnings("unused")
@Service
//@ServiceQuality(QualityLevel.SECURE)
//@ConditionalOnProperty(name = "qualityLevel", havingValue = "secure")
@Profile({"preprod","prod"})
public class SecureRandomNumberService implements RandomNumberService {

	private SecureRandom random = new SecureRandom();

	@Override
	public int generate(int min, int max) {
		System.out.println("SecureRandomNumberService::generate");
		return random .nextInt(min,max);
	}

}
