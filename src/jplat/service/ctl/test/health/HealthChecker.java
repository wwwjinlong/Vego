package jplat.service.ctl.test.health;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jplat.tools.string.DateUtil;
import jplat.tools.string.StringUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HealthChecker
{
	private Logger logger = LogManager.getLogger(HealthChecker.class);

	@RequestMapping("/test/ping.do")
	@ResponseBody
	public Map<String,String> healthCheck( HttpServletRequest request, HttpServletResponse response )
	{
		Map<String,String> retMap = new HashMap<String,String>();
		retMap.put("msg", "pong");
		retMap.put("ipAddr", StringUtil.getLocalIP());
		retMap.put("appName", request.getContextPath() );

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
}
