package jplat.service.ctl.test.health;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jplat.error.exception.JSystemException;
import jplat.tools.coder.JsonCoder;
import jplat.tools.stream.JServletStreamUtils;
import jplat.tools.string.DateUtil;
import jplat.tools.string.StringUtil;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HealthChecker
{
	private Logger logger = LoggerFactory.getLogger(HealthChecker.class);

	@RequestMapping("/test/ping.do")
	@ResponseBody
	public Map<String,String> healthCheck( HttpServletRequest request, HttpServletResponse response )
	{
		Map<String,String> retMap = new HashMap<String,String>();
		retMap.put("msg", "pong");
		retMap.put("ipAddr", StringUtil.getLocalIP());
		retMap.put("appName", request.getContextPath() );
		
		Enumeration<String> enumKey = request.getHeaderNames();
		while ( enumKey.hasMoreElements() )
		{
			String headKey = enumKey.nextElement();
			logger.info(String.format("HTTP_HEAD key=[%s],value=[%s]",headKey,request.getHeader(headKey)));
		}
		
		Cookie[] cooks = request.getCookies();
		if ( cooks != null )
		{
			for ( int i = 0; i < cooks.length; ++i )
			{
				Cookie cook = cooks[i];
				logger.info(String.format("HTTP_COOKIE name=[%s],value=[%s],domain=[%s],path=[%s],secure=[%s],maxage=[%s]",
							cook.getName(),cook.getValue(),cook.getDomain(),cook.getPath(),cook.getSecure()+"",""+cook.getMaxAge()));
			}
		}


//		RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
		return retMap;
	}

	@RequestMapping("/test/jsp.do")
	public String healthCheckJSP( HttpServletRequest request, HttpServletResponse response )
	{
		return "__OK";
	}

	@RequestMapping("/test/__info.do")
	@ResponseBody
	public Map jvmInfo( HttpServletRequest request )
	{
		Map<String,Object> retMap = new HashMap<String,Object>();

		//增加线程数目.
		ThreadMXBean tb = ManagementFactory.getThreadMXBean();
		int peakCount = tb.getPeakThreadCount(); //峰值
		int allCount = tb.getThreadCount(); //所有存活线程
		int dmCount = tb.getDaemonThreadCount(); //所有后台线程

		long tids[] = tb.findDeadlockedThreads();
		if ( tids != null )
		{
			retMap.put("deadlockTd", tids.length );
			retMap.put("deadTdid", tids[0]);
		}

		retMap.put("peakTd", ""+peakCount);
		retMap.put("daemonTd", ""+dmCount);
		retMap.put("livesTd", ""+allCount);
		retMap.put("ipAddr", StringUtil.getLocalIP());
		retMap.put("appName", request.getContextPath() );
		
//		request.get

		//获取系统
		RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
		
		// Get start time
		long startTime = bean.getStartTime();
		
		// Get start Date
		retMap.put("startTime", DateUtil.formatDateToStr(new Date(startTime), "yyyy-MM-dd HH:mm:ss" ));
		
		retMap.put("liveMinutes", ""+(new Date().getTime() - startTime)/1000/60 );
		
		logger.info("__MONITOR:THREAD_COUNT=["+retMap+"]");

		return retMap;
	}
	
	@RequestMapping("/test/head.do")
//	@ResponseBody
//	public Map<String,Object> headPrint( HttpServletRequest request, HttpServletResponse response )
	public void headPrint( HttpServletRequest request, HttpServletResponse response )
	{
		Map<String,Object> retMap = new HashMap<String,Object>();
		retMap.put("msg", "pong");
		retMap.put("ipAddr", StringUtil.getLocalIP());
		retMap.put("appName", request.getContextPath() );
		retMap.put("getCharacterEncoding", request.getCharacterEncoding());
		retMap.put("getContentType", request.getContentType());
		retMap.put("getContentLength", request.getContentLength());
		retMap.put("getPathInfo", request.getPathInfo());
		retMap.put("getRequestURI", request.getRequestURI());
		retMap.put("getServletPath", request.getServletPath());
		retMap.put("getServerName", request.getServerName());
		retMap.put("method", request.getMethod());
		
		Map<String,String> headMap = new HashMap<String,String>();
		Enumeration<String> enumKey = request.getHeaderNames();
		while ( enumKey.hasMoreElements() )
		{
			String headKey = enumKey.nextElement();
			headMap.put(headKey,request.getHeader(headKey));
			
			logger.info(String.format("HTTP_HEAD key=[%s],value=[%s]",headKey,request.getHeader(headKey)));
		}
		
		Map<String,String> cookieMap = new HashMap<String,String>();
		Cookie[] cooks = request.getCookies();
		if ( cooks != null )
		{
			for ( int i = 0; i < cooks.length; ++i )
			{
				Cookie cook = cooks[i];
				cookieMap.put(cook.getName(), cook.getValue());
				
				logger.info(String.format("HTTP_COOKIE name=[%s],value=[%s],domain=[%s],path=[%s],secure=[%s],maxage=[%s]",
							cook.getName(),cook.getValue(),cook.getDomain(),cook.getPath(),cook.getSecure()+"",""+cook.getMaxAge()));
			}
		}
		
		//读取数据.
		if ( "POST".equalsIgnoreCase(request.getMethod()) )
		{
			try {
				byte[] inDatas = JServletStreamUtils.readInputStream(request);
				logger.info(String.format("FROM_REQ:length[%d],content=[%s]",inDatas.length,new String(inDatas,"utf-8")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		retMap.put("http_headers", headMap);
		retMap.put("http_cookies", cookieMap);

		String jsonStr = JsonCoder.toJsonString(retMap);
		byte[] outData = jsonStr.getBytes();
		try {
			response.setHeader("return-length", ""+outData.length);
			response.setContentType("application/json;charse=utf-8");
			OutputStream ous = response.getOutputStream();
			ous.write(outData);
/*			ous.write(outData,0,10);
			ous.flush();
			ous.write(outData,10,outData.length-10);
			ous.flush();*/
			ous.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
//		return retMap;
	}
}
