package hu.webuni.cst.kamarasd.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RecallerAspect {
	
	@Pointcut("@annotation(hu.webuni.cst.kamarasd.aspect.RecallNeptunIfFailed) || @within(hu.webuni.cst.kamarasd.aspect.RecallNeptunIfFailed)")
	public void annotationRecallNeptun() {
		
	}
	
	@Around("annotationRecallNeptun()")
	public Object recallNeptun(ProceedingJoinPoint joinPoint) throws Throwable {
		
		RecallNeptunIfFailed recallNeptunIfFailed = null;
		Signature sign = joinPoint.getSignature();
		
		if(sign instanceof MethodSignature) {
			MethodSignature methodSignature = (MethodSignature) sign;
			Method method = methodSignature.getMethod();
			recallNeptunIfFailed = method.getAnnotation(RecallNeptunIfFailed.class);
		} else {
			Class<?> declaringType = sign.getDeclaringType();
			recallNeptunIfFailed = declaringType.getAnnotation(RecallNeptunIfFailed.class); 
		}
		
		System.out.println(recallNeptunIfFailed);
		
		int times = recallNeptunIfFailed.retryTimes();
		long waitTime = recallNeptunIfFailed.waitTime();
		
		if( times <= 0) {
			times = 1;
		}
		
	    for (int numberOfTries = 1; numberOfTries <= times; numberOfTries++) {
		
		    System.out.format("time: %d %n", times);
		 	System.out.format("Tries: %d %n", numberOfTries);
		    try {
		    	return joinPoint.proceed();
		    } catch (Exception e) {
		      
		     	if (numberOfTries == times) {
		      		throw e;
		      	}
		            
		     	if (waitTime > 0) {
		            Thread.sleep(waitTime);
		        }
		    }
	    }    
	return null;	
	    
	}

}
