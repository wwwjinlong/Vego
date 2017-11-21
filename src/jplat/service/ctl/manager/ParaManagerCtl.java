package jplat.service.ctl.manager;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jplat.tools.config.JConfigManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ParaManagerCtl
{
	private Logger logger = LogManager.getLogger(ParaManagerCtl.class);
	
	@RequestMapping("/mgr/config/reload.do")
	@ResponseBody
	public Map<String,String> reloadConfigs( HttpServletRequest request, HttpServletResponse response )
	{
		Map<String,String> retMap = new HashMap<String,String>();
		retMap.put("appName", request.getContextPath() );
		
		String token = request.getParameter("authkey");
		JConfigManager configMr = JConfigManager.getInstance();
		
		if ( !token.equals(configMr.getSystemConfig().getString("auth.key")) )
		{
			return null;
		}

		configMr.reload();

		retMap.put("version",configMr.getSystemConfig().getString("cnf.version"));
		retMap.put("create_time", configMr.getSystemConfig().create_time );
		
		return retMap;
	}
}
