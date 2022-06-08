package com.example.lottery.service.business;

import java.security.SecureRandom;

import org.springframework.boot.autoconfigure.condition.ConditionalOnCloudPlatform;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.cloud.CloudPlatform;
import org.springframework.stereotype.Service;

import com.example.lottery.service.QualityLevel;
import com.example.lottery.service.RandomNumberService;
import com.example.lottery.service.ServiceQuality;

@SuppressWarnings("unused")
@Service
//@ServiceQuality(QualityLevel.SECURE)
@ConditionalOnProperty(name = "qualityLevel", havingValue = "secure")
public class SecureRandomNumberService implements RandomNumberService {

	private SecureRandom random = new SecureRandom();

	@Override
	public int generate(int min, int max) {
		System.out.println("SecureRandomNumberService::generate");
		return random .nextInt(min,max);
	}

}
