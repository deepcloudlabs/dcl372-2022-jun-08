package com.example.lottery.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(Integer.MIN_VALUE)
public class ProfilingAspect {

	@Around("""
		lotteryPackage() &&
		(	
		   classIsProfiledAnnotated() || methodIsProfiledAnnotated()
		)	
	""")
	public Object profile(ProceedingJoinPoint joinPoint) throws Throwable {
		String methodName = joinPoint.getSignature().getName();
		var start = System.nanoTime();
		var result = joinPoint.proceed();
		var stop = System.nanoTime();
		System.err.println("%s runs %d ns.".formatted(methodName,(stop-start)));
		return result;
	}
	
	@Pointcut("within(@com.example.lottery.aspect.Profiled *))")
	public void classIsProfiledAnnotated() {}

	@Pointcut("@annotation(com.example.lottery.aspect.Profiled))")
	public void methodIsProfiledAnnotated() {}
	
	@Pointcut("execution(* com.example.lottery..*(..))")
	public void lotteryPackage() {}
	
	
}
