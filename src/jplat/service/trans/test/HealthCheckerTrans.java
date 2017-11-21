package jplat.service.trans.test;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jplat.core.trans.JAppContext;
import jplat.service.ctl.test.model.HealthReqModel;
import jplat.service.ctl.test.model.HealthRspModel;
import jplat.tools.config.JAppConfig;
import jplat.tools.string.JStringUtil;
import jplat.tools.string.StringUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
//@Controller
public class HealthCheckerTrans
{
	private Logger logger = LogManager.getLogger(HealthCheckerTrans.class);
	
	public void ping( JAppContext appCtx, HealthReqModel reqModel, HealthRspModel rspModel )
	{
		logger.info(String.format("TEST:recv trans message.[%s]",reqModel.getMsg()));
		
		rspModel.setAppName(JAppConfig.APP_NAME);
		rspModel.setIpAddr(JStringUtil.getLocalIP());
		rspModel.setRetMsg("pong");
		rspModel.setYourMsg(reqModel.getMsg());
	}
	
	@RequestMapping("/test/ping1.do")
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
	
}
