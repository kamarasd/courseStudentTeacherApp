package hu.webuni.cst.kamarasd.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.webuni.cst.kamarasd.service.ConnectCentralDbService;

@Aspect
@Component
public class RecallerAspect {
	
	private final ConnectCentralDbService connectCentralDbService = new ConnectCentralDbService();
	
	@Pointcut("@annotation(hu.webuni.cst.kamarasd.aspect.RecallNeptunIfFailed) || @within(hu.webuni.cst.kamarasd.aspect.RecallNeptunIfFailed)")
	public void annotationRecallNeptun() {
		
	}
	
//	@After("hu.webuni.cst.kamarasd.aspect.RecallerAspect.annotationRecallNeptun()")
	@AfterThrowing(pointcut = "hu.webuni.cst.kamarasd.aspect.RecallerAspect.annotationRecallNeptun()",
			throwing = "e")
	public void recallNeptun(JoinPoint joinPoint) {
		try {
			
		}catch (Exception e) {
			System.out.println(e);
		}
		
		//connectCentralDbService.getFreeSemesters(null);
		
		
	}

}
