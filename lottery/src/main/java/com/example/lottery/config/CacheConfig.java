package com.example.lottery.config;

import java.util.List;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableCaching
public class CacheConfig {

	@Bean
	@Profile({"dev", "test"})
	public CacheManager inMemoryCacheManager(){
		var cacheManager = new SimpleCacheManager();
		cacheManager.setCaches(List.of( new ConcurrentMapCache("numbers") ));
		return cacheManager;
	}

	@Bean
	@Profile({"prod"})
	public CacheManager redisCacheManager(){
		var cacheManager = new SimpleCacheManager();
		cacheManager.setCaches(List.of( new ConcurrentMapCache("numbers") ));
		return cacheManager;
	}
	
}
