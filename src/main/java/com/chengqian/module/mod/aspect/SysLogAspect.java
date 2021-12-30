//
//package com.chengqian.module.mod.aspect;
//
//import born.common.annotation.SysLog;
//import born.common.utils.HttpContextUtils;
//import born.common.utils.IPUtils;
//import born.modules.sys.entity.SysLogEntity;
//import born.modules.sys.entity.SysUserEntity;
//import born.modules.sys.service.SysLogService;
//import com.google.gson.Gson;
//import org.apache.commons.lang.StringUtils;
//import org.apache.shiro.SecurityUtils;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import java.lang.reflect.Method;
//import java.util.Date;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//
///**
// * 系统日志，切面处理类
// *
// */
//@Aspect
//@Component
//public class SysLogAspect {
//	@Autowired
//	private SysLogService sysLogService;
//
//	@Pointcut("@annotation(born.common.annotation.SysLog)")
//	public void logPointCut() {
//
//	}
//
//	@Around("logPointCut()")
//	public Object around(ProceedingJoinPoint point) throws Throwable {
//		long beginTime = System.currentTimeMillis();
//		//执行方法
//		Object result = point.proceed();
//		//执行时长(毫秒)
//		long time = System.currentTimeMillis() - beginTime;
//
//		//保存日志
//		saveSysLog(point, time);
//
//		return result;
//	}
//
//	private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
//		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//		Method method = signature.getMethod();
//
//		SysLogEntity sysLog = new SysLogEntity();
//		SysLog syslog = method.getAnnotation(SysLog.class);
//		if(syslog != null){
//			//注解上的描述
//			sysLog.setOperation(syslog.value());
//		}
//
//		//请求的方法名
//		String className = joinPoint.getTarget().getClass().getName();
//		String methodName = signature.getName();
//		sysLog.setMethod(className + "." + methodName + "()");
//
//		//请求的参数
//		Object[] args = joinPoint.getArgs();
//		try{
//			String params = new Gson().toJson(args);
//			if(params.length()>1000){
//				params=params.substring(0,1000);
//			}
//			sysLog.setParams(params);
//		}catch (Exception e){
//
//		}
//
//		//获取request
//		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
//		//设置IP地址
//		sysLog.setIp(IPUtils.getIpAddr(request));
//
//		//用户名
//		SysUserEntity principal = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
//		if(principal==null||StringUtils.isEmpty(principal.getUsername())){
//			Pattern pattern=Pattern.compile("\"username\":\".+\",");
//			if(StringUtils.isNotEmpty(sysLog.getParams())){
//				Matcher matcher = pattern.matcher(sysLog.getParams());
//				while(matcher.find()){
//					String s2=matcher.group();
//					String username = s2.substring(12, s2.indexOf("\"", 12));
//					sysLog.setUsername(username);
//				}
//			}else{
//				sysLog.setUsername("");
//			}
//		}else{
//			String username =principal.getUsername();
//			sysLog.setUsername(username);
//		}
//
//
//		sysLog.setTime(time);
//		sysLog.setCreateDate(new Date());
//		//保存系统日志
//		sysLogService.save(sysLog);
//	}
//}
