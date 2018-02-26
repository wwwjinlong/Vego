package jplat.service.ctl.dispatcher;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jplat.base.constant.KPlatResponseCode;
import jplat.core.dispatcher.JTransCache;
import jplat.core.dispatcher.JTransInfo;
import jplat.core.dispatcher.JTransURLInfo;
import jplat.core.framework.config.AppServletContextHolder;
import jplat.core.trans.JAppBaseService;
import jplat.core.trans.impl.JServletAppContext;
import jplat.error.exception.JSystemException;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import z.log.tracelog.JLog;

/**
 * 该类用于将url按照规则映射到类名，用于解决对不同渠道的相同接口复用的问题.
 * 例如App请求查询客户的信息的时候需要json数据，
 * 而网页版本则需要返回一个网页，但是服务端实现实际是一个查询客户信息的接口.
 * @author zhangcq
 *
 */
@Controller
//@RequestMapping("/test157")
public class JTransDispatcherCtrl extends JAppBaseService
{
	private JTransCache transCache = JTransCache.getInstance();
	
	/**
	 * Oct 30, 201710:58:57 AM
	 * doDispatchJson
	 * @param moduleCode 用于映射包名.
	 * @param clazzName	 类名.
	 * @param methodName 方法名.
	 * @throws JSystemException 
	 */
	@RequestMapping("/json2/{moduleCode}/{clazzName}/{methodName}.do")
	public void doDispatchJson( HttpServletRequest request,HttpServletResponse response,
			@PathVariable String moduleCode, @PathVariable String clazzName, @PathVariable String methodName ) throws JSystemException
	{
		callTransImpl0(request,response,moduleCode,clazzName,methodName);
	}
	
	/**
	 * JSON格式报文处理类.
	 * Nov 20, 201711:33:37 AM
	 * callActionImpl0
	 * @param request
	 * @param response
	 * @param moduleCode
	 * @param clazzName
	 * @param methodName
	 * @throws JSystemException
	 */
	private void callTransImpl0( HttpServletRequest request,HttpServletResponse response,
			String moduleCode, String clazzName, String methodName ) throws JSystemException
	{
		//获取springMVC的上下文.
//		ApplicationContext springMVCCtx = AppServletContextHolder.getContext();
		
		//获取spring的上下文.
		ApplicationContext springCtx = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		
//		XLog.log("servlet[%d],spring[%d]", springMVCCtx.hashCode(),springCtx.hashCode());
		
		//获取url基本信息.
		JTransURLInfo urlInfo = new JTransURLInfo(moduleCode,clazzName,methodName);

		//映射到具体类.
		JTransInfo actionInfo = transCache.getTransInfo(springCtx,urlInfo);

		//创建应用上下文.
		JServletAppContext appCtx = (JServletAppContext)serviceFactory.buildAppContext(request, response, true);

		//初始化参数列表.
		List<Object> paraList = new ArrayList<Object>();
		paraList.add(appCtx);

		//转换请求数据类型.
		if ( actionInfo.getReqParaClass() != null )
		{
			Object reqModel = appCtx.obtainReqModel(actionInfo.getReqParaClass(), true);
			appCtx.setReqBody(reqModel);
			paraList.add(reqModel);
		}

		//设置返回报文.
		if ( actionInfo.getRspParaClass() != null )
		{
			try
			{
				Object rspBody = actionInfo.getRspParaClass().newInstance();
				paraList.add(rspBody);
				appCtx.setRspBody(rspBody);
			}
			catch (InstantiationException | IllegalAccessException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new JSystemException(KPlatResponseCode.CD_FRAMEWORK_ERR,KPlatResponseCode.MSG_FRAMEWORK_ERR);
			}
		}

		//调用业务模块.
		actionInfo.invokeTrans(paraList.toArray());

		//组包返回报文.
		appParser.doAppPack(appCtx);
		
		//返回视图路径.
//		return urlInfo.getViewKey(appCtx.getRetCode(), "");
	}
}
