package jplat.service.ctl.manager;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jplat.tools.config.JAppConfig;
import jplat.tools.config.JConfigManager;

@Controller
public class ParaManagerCtl
{
	private Logger logger = LoggerFactory.getLogger(ParaManagerCtl.class);
	
	@RequestMapping("/mgr/config/reload.do")
	@ResponseBody
	public Map<String,String> reloadConfigs( HttpServletRequest request, HttpServletResponse response )
	{
		Map<String,String> retMap = new HashMap<String,String>();
		retMap.put("appName", request.getContextPath() );
		
		String token = request.getParameter("authkey");
		JConfigManager configMr = JConfigManager.getInstance();
		
		if ( !token.equals(JAppConfig.getConfigCache().MGR_AUTHKEY) )
		{
			return null;
		}

		configMr.reload();

		retMap.put("version",JAppConfig.getConfigCache().MGR_CONFIG_VERSION);
		retMap.put("create_time", configMr.getSystemConfigLoader().create_time );
		
		return retMap;
	}
}
