package com.example.lottery.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(Integer.MIN_VALUE)
public class AuditingAspect {

	
}
