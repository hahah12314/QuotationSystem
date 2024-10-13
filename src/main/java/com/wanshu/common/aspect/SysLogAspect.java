package com.wanshu.common.aspect;

import com.alibaba.fastjson.JSON;
import com.wanshu.common.annotation.SystemLog;
import com.wanshu.sys.entity.SysLog;
import com.wanshu.sys.service.ISysLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Component
@Aspect
@EnableAspectJAutoProxy
public class SysLogAspect {
    @Autowired
    private ISysLogService sysLogService;
    @Pointcut("@annotation(com.wanshu.common.annotation.SystemLog)")
    public void logPointCut(){
        System.out.println("切面");
    }
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable{
        long start = System.currentTimeMillis();
        Object obj = point.proceed();
        long time = System.currentTimeMillis()-start;
        saveSysLog(point,time);
        return obj;
    }

    private void saveSysLog(ProceedingJoinPoint point, long time) {
        System.out.println("保存日志");
        System.out.println(point);
        SysLog sysLog = new SysLog();
        MethodSignature methodSignature =(MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        SystemLog systemLog = method.getAnnotation(SystemLog.class);
        if(systemLog!=null){
            String value = systemLog.value();
            sysLog.setOperation(value);
        }
        String className = point.getTarget().getClass().getName();
        String methodName = method.getName();
        sysLog.setMethod(className+"."+methodName+"()");
        sysLog.setTime(time);
        sysLog.setCreateDate(LocalDateTime.now());
        Object[] args = point.getArgs();
        String params = JSON.toJSON(args).toString();
        sysLog.setParams(params);
        sysLogService.save(sysLog);


    }
}
