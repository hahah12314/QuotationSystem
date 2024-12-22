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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 日志切面类，用于拦截标注了 {@link SystemLog} 注解的方法，并记录其执行信息到系统日志中。
 */
@Component
@Aspect
@EnableAspectJAutoProxy
public class SysLogAspect {

    @Autowired
    private ISysLogService sysLogService;

    /**
     * 定义切入点，匹配所有标注了 {@link SystemLog} 注解的方法。
     */
    @Pointcut("@annotation(com.wanshu.common.annotation.SystemLog)")
    public void logPointCut() {
        // 打印切面信息（调试用）
        System.out.println("切面");
    }

    /**
     * 环绕通知方法，用于在目标方法执行前后进行处理，记录方法的执行时间和参数等信息。
     *
     * @param point 切入点对象，包含被拦截方法的信息
     * @return 被拦截方法的返回结果
     * @throws Throwable 如果被拦截方法抛出异常，则此方法也会抛出相同的异常
     */
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long start = System.currentTimeMillis();  // 记录开始时间
        Object obj = point.proceed();  // 执行目标方法
        long time = System.currentTimeMillis() - start;  // 计算方法执行时间
        saveSysLog(point, time);  // 保存日志
        return obj;  // 返回目标方法的结果
    }

    /**
     * 保存系统日志信息。
     *
     * @param point 切入点对象，包含被拦截方法的信息
     * @param time  方法执行时间（毫秒）
     */
    private void saveSysLog(ProceedingJoinPoint point, long time) {
        // 打印保存日志信息（调试用）
        System.out.println("保存日志");
        System.out.println(point);

        // 获取当前用户信息
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();

        SysLog sysLog = new SysLog();  // 创建新的日志对象

        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();

        // 获取方法上的 {@link SystemLog} 注解信息
        SystemLog systemLog = method.getAnnotation(SystemLog.class);
        if (systemLog != null) {
            // 设置操作描述，从注解中获取
            String value = systemLog.value();
            sysLog.setOperation(value);
        }

        // 构建日志的类名和方法名
        String className = point.getTarget().getClass().getName();
        String methodName = method.getName();
        sysLog.setMethod(className + "." + methodName + "()");

        // 设置日志的执行时间
        sysLog.setTime(time);

        // 设置日志的创建日期
        sysLog.setCreateDate(LocalDateTime.now());

        // 获取方法参数并转换为 JSON 字符串
        Object[] args = point.getArgs();
        String params = JSON.toJSON(args).toString();
        sysLog.setParams(params);

        // 设置执行该方法的用户名
        sysLog.setUsername(username);

        // 保存日志到数据库
        sysLogService.save(sysLog);
    }
}
