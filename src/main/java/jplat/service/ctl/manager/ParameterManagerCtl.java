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

import jplat.error.exception.JSystemException;
import jplat.tools.config.JAppConfig;
import jplat.tools.config.JConfigManager;

@Controller
public class ParameterManagerCtl
{
	private Logger logger = LoggerFactory.getLogger(ParameterManagerCtl.class);
	
	@RequestMapping("/mgr/config/reload.do")
	@ResponseBody
	public Map<String,Object> reloadConfigs( HttpServletRequest request, HttpServletResponse response )
	{
		Map<String,Object> retMap = new HashMap<String,Object>();
		retMap.put("appName", request.getContextPath() );
		
		String token = request.getParameter("authkey");
		JConfigManager configMr = JConfigManager.getInstance();
		
		if ( !token.equals(JAppConfig.getConfigCache().MGR_AUTHKEY) )
		{
			return null;
		}

		try {
			configMr.load();
		} catch (JSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			retMap.put("retcode", "9999");
			retMap.put("retmsg", e.getMessage());
			return retMap;
		}

		retMap.put("version",JAppConfig.getConfigCache().MGR_CONFIG_VERSION);
		retMap.put("create_time", configMr.getSystemConfigLoader().create_time );
		retMap.put("parameters", JAppConfig.getConfigCache().convert2Map());
		
		return retMap;
	}
}
