package com.dandinglong.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class DealTimeAspect {
    private Logger logger= LoggerFactory.getLogger(DealTimeAspect.class);
    @Pointcut("@annotation(com.dandinglong.annotation.FunctionUseTime)")
    public void pointcut(){

    }
    @Around("pointcut()")
    public Object assertAround(ProceedingJoinPoint pjp) throws Throwable {
        Class<?> aClass = pjp.getTarget().getClass(); //先获取被织入增强处理的目标对象，再获取目标类的clazz
        String methodName = pjp.getSignature().getName(); //先获取目标方法的签名，再获取目标方法的名
        try {
            long starttime = System.currentTimeMillis();
            Object proceed = pjp.proceed();  //执行目标方法
            long exctime = System.currentTimeMillis() - starttime;
            logger.info(aClass.getName()+"."+methodName+"执行时间："+exctime + "毫秒");
            return proceed;
        } catch (Throwable throwable) {
            logger.error("切面执行出错",throwable);
            throw throwable;
        }
    }
}
