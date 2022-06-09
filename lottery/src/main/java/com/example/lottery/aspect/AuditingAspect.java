package com.example.lottery.aspect;

import java.util.Arrays;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(Integer.MIN_VALUE)
public class AuditingAspect {

//	@Before
//	@AfterReturning
//	@AfterThrowing
//	@After
	@Around("execution(* com.example.lottery..*(..))")
	public Object audit(ProceedingJoinPoint joinPoint) throws Throwable {
		String methodName = joinPoint.getSignature().getName();
		System.err.println("%s runs at %s".formatted(methodName,new Date()));
		System.err.println("%s runs with the parameters: %s".formatted(methodName,Arrays.toString(joinPoint.getArgs())));
		var result = joinPoint.proceed();
		System.err.println("%s return %s".formatted(methodName,result));
		return result;
	}
}
