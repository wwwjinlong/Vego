package jplat.service.trans.test;


import jplat.core.trans.JAppContext;
import jplat.service.ctl.test.model.HealthReqModel;
import jplat.service.ctl.test.model.HealthRspModel;
import jplat.tools.config.JAppConfig;
import jplat.tools.string.JStringUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

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
}
