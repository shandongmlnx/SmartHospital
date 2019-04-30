package org.shan.spring.base.aop;

import com.alibaba.fastjson.JSON;
import com.mlnx.common.annotations.SysLog;
import com.mlnx.common.utils.MyLog;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.shan.spring.base.annotations.MlnxJsonFormat;
import org.shan.spring.base.entity.LogInfo;

import java.lang.reflect.Method;

/**
 * Created by amanda.shan on 2018/10/31.
 */
public abstract class BaseAspect {

    protected MyLog log = MyLog.getLog(BaseAspect.class);

    ThreadLocal<LogInfo> recordLogLocal = new ThreadLocal<>();

    public abstract void pointCut();


    /**
     * JoinPoint 方法
     * java.lang.Object[] getArgs()：获取连接点方法运行时的入参列表；
     * Signature getSignature() ：获取连接点的方法签名对象；
     * java.lang.Object getTarget() ：获取连接点所在的目标对象；
     * java.lang.Object getThis() ：获取代理对象本身；
     * Signature getSignature() : 获取注解
     *
     * @param joinPoint
     */

    /**
     * 开始调用方法拦截
     *
     * @param joinPoint
     */
    @Before("pointCut()")
    public void baseStart(JoinPoint joinPoint) {

        recordLogLocal.set(new LogInfo());

        if (isLog(joinPoint)) {

            // 请求的方法名
            String strMethodName = joinPoint.getSignature().getName();
            // 请求的类名
            String strClassName = joinPoint.getTarget().getClass().getName();
            // 请求的参数
            Object[] params = joinPoint.getArgs();

            if (params != null && params.length > 0) {
                StringBuffer bfParams = new StringBuffer();
                for (int i = 0; i < params.length; i++) {
                    bfParams.append(params[i].toString());
                    bfParams.append(",");
                }
                String strMessage = String.format("\n[类名]:%s,[方法]:%s,[参数]:%s", strClassName, strMethodName, bfParams
                        .toString());
                recordLogLocal.get().getBfParams().append(strMessage);
            }
        }
    }

    /**
     * 方法调用结束拦截
     *
     * @param joinPoint
     */
    @After("pointCut()")
    public void baseEnd(JoinPoint joinPoint) {

    }

    //JoinPoint一定要出现在参数表的第一位
    @AfterReturning(value = "pointCut()", returning = "result")
    public void baseReturn(JoinPoint joinPoint, Object result) {
        if (isLog(joinPoint)) {
            long endTime = System.currentTimeMillis();

            recordLogLocal.get().getBfParams().append(String.format("\n返回值：%s 耗时：%d", result,
                    endTime - recordLogLocal.get().getStartTime()));
            log.info(recordLogLocal.get().getBfParams().toString());
        }
    }

    @AfterThrowing(value = "pointCut()", throwing = "exception")
    public void baseException(JoinPoint joinPoint, Exception exception) {
        if (isLog(joinPoint)) {
            recordLogLocal.get().getBfParams().append("\n异常：" + exception.getMessage());
            log.info(recordLogLocal.get().getBfParams().toString());
        }
    }

    @Around(value = "pointCut()")
    public Object baseAround(ProceedingJoinPoint joinPoint) throws Throwable {

        // 返回值
        Object result = joinPoint.proceed();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        MlnxJsonFormat jsonFormat = method.getAnnotation(MlnxJsonFormat.class);
        if (jsonFormat != null) {

            String obj = JSON.toJSONStringWithDateFormat(result, jsonFormat.datePattern());
            log.debug("aop拦截时间格式化后返回：" + obj);

            return obj;
        }

        return result;
    }

    private boolean isLog(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLog syslog = method.getAnnotation(SysLog.class);

        return (syslog != null && syslog.isLog());
    }
}
