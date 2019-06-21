package com.elvis.demo.aspect;

import com.elvis.demo.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
@Aspect
@Slf4j
/*
参数校验切面
 */
public class RequestParamCheck {
	
	 @Pointcut("execution(* com.elvis.demo.controller..*(..,org.springframework.validation.BindingResult))")
	    private void paramOps() {
	    }
	 
	    @Around("paramOps()")
	    public Object before(ProceedingJoinPoint pjp) throws Throwable {
	    	Object[] args = pjp.getArgs();
	    	for (Object o : args) {
				if(o instanceof BindingResult) {
					BindingResult restult = (BindingResult) o;
					if(restult.hasErrors()) {
						log.error("===AOP===="+restult.toString());
						return new ResponseEntity<R>(R.error("参数错误"), HttpStatus.BAD_REQUEST);
					}
				}
			}
	    	return pjp.proceed();
	    }
}
