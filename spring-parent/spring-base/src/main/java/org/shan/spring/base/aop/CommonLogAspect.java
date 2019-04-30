package org.shan.spring.base.aop;


import com.mlnx.common.annotations.SysLog;
import com.mlnx.common.entity.Log;
import com.mlnx.common.utils.MyLog;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.shan.spring.base.utils.ServletUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

/**
 * Web层日志切面.
 */

public class CommonLogAspect {

    private MyLog logger = MyLog.getLog(this.getClass());

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * 开始调用的时候进入
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Before("cutController()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());
    }

    /**
     * 调用结束
     * @param ret
     * @throws Throwable
     */
//    @AfterReturning(returning = "ret", pointcut = "cutController()")
//    public void doAfterReturning(Object ret) throws Throwable {
//        // 处理完请求，返回内容
//        logger.info("RESPONSE : " + ret);
//
//        logger.info("消耗时间: " + (System.currentTimeMillis() - startTime.get()));
//    }

    /**
     * 从处理到结束
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("cutController()")
    public Object recordSysLog(ProceedingJoinPoint joinPoint) throws Throwable {

        Object result = null;

        // 判断是否需要日记记录
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLog syslog = method.getAnnotation(SysLog.class);
        if (syslog != null && syslog.isLog()) {

            // 请求的方法名
            String strMethodName = joinPoint.getSignature().getName();
            // 请求的类名
            String strClassName = joinPoint.getTarget().getClass().getName();
            // 请求的参数
            Object[] params = joinPoint.getArgs();
            StringBuffer bfParams = new StringBuffer();
            Enumeration<String> paraNames = null;
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                    .getRequest();
            if (params != null && params.length > 0) {
                paraNames = request.getParameterNames();
                String key;
                String value;
                while (paraNames.hasMoreElements()) {
                    // 遍历请求参数
                    key = paraNames.nextElement();
                    value = request.getParameter(key);
                    bfParams.append(key).append("=").append(value).append("&");
                }
                if (StringUtils.isBlank(bfParams.toString())) {
                    // 如果请求参数为空,返回url路径后面的查询字符串
                    bfParams.append(request.getQueryString());
                }
            }

            String strMessage = String.format("[类名]:%s,[方法]:%s,[参数]:%s", strClassName, strMethodName, bfParams
                    .toString());
            logger.info(strMessage);

            // 环绕通知 ProceedingJoinPoint执行proceed方法的作用是让目标方法执行，这也是环绕通知和前置、后置通知方法的一个最大区别。
            result = joinPoint.proceed();

            long endTime = System.currentTimeMillis();
            logger.info("doAround>>>result={},耗时：{}", result, endTime - startTime.get());

            Log sysLog = new Log();
            sysLog.setCreateTime(new Date());
            sysLog.setOptContent(strMessage);
            sysLog.setUserIp(ServletUtils.getIpAddr());
            sysLog.setUrl(request.getRequestURI());
            sysLog.setMethod(request.getMethod());
            sysLog.setUserAgent(request.getHeader("User-Agent"));
            sysLog.setSpendTime((int) (endTime - startTime.get()));
            // 可以保存调用信息到数据库
        }
        return result;
    }
}
