package com.elvis.demo.aspect;

import com.elvis.demo.annotation.MyLog;
import com.elvis.demo.mapper.LogMapper;
import com.elvis.demo.model.Log;
import com.elvis.demo.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Elvis
 * @create 2019-06-20 16:06
 */
@Aspect
@Component
@Slf4j
public class MyLogAspect {
    @Autowired
    LogMapper logmapper;
    @Before("myLog()")
    public void dobefore(JoinPoint jp) throws Throwable {
        log.info("开始执行方法");
    }

    @Around("myLog()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        Object result = pjp.proceed();
        Log log = new Log();
        ResponseEntity<R> myres =  (ResponseEntity<R>)result;
       // R r = myres.getBody();
        log.setResult(myres.getStatusCode()==HttpStatus.OK?true:false);
        log.setTitle(getAspectLogtitle(pjp));
//        log.setUser(getUserName(pjp));
        log.setUser(getheader(pjp));
        logmapper.insertSelective(log);
        return result;
    }

    @Pointcut("@annotation(com.elvis.demo.annotation.MyLog)")
    private void myLog() {
    }

    /**
     * 获取切面注解的描述
     *
     * @param joinPoint 切点
     * @return 描述信息
     * @throws Exception
     */
    public String getAspectLogtitle(ProceedingJoinPoint joinPoint)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        StringBuilder title = new StringBuilder("");
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    title.append(method.getAnnotation(MyLog.class).title());
                    break;
                }
            }
        }
        return title.toString();
    }

    /**
     获取RequestHeader参数的值
     */
    public String getUserName(ProceedingJoinPoint pjp){
        MethodSignature signature= (MethodSignature) pjp.getSignature();
        Object[] args= pjp.getArgs();
        //获取所有参数上的注解
        Annotation[][] parameterAnnotations= signature.getMethod().getParameterAnnotations();
        for (Annotation[] parameterAnnotation: parameterAnnotations) {
            int paramIndex= ArrayUtils.indexOf(parameterAnnotations, parameterAnnotation);
            for (Annotation annotation: parameterAnnotation) {
                if (annotation instanceof RequestHeader){
                    return (String)args[paramIndex];
                }
            }}

        return "默认";
    }

    /**
        获取请求header数据
     */
    public String getheader(ProceedingJoinPoint pjp){
        MethodSignature signature= (MethodSignature) pjp.getSignature();
        Object[] args= pjp.getArgs();
        for(Object arg:args){
            if(arg instanceof HttpServletRequest){
                HttpServletRequest request =   (HttpServletRequest) arg;
                return request.getHeader("token");
            }
        }
        return "默认";
    }
}
