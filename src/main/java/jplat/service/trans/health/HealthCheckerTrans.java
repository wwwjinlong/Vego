package jplat.service.trans.health;

import jplat.core.trans.JAppContext;
import jplat.error.exception.JTransException;
import jplat.service.ctl.test.model.HealthReqModel;
import jplat.service.ctl.test.model.HealthRspModel;
import jplat.tools.config.JAppConfig;
import jplat.tools.string.JStringUtil;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
//@Controller
public class HealthCheckerTrans
{
	private Logger logger = LoggerFactory.getLogger(HealthCheckerTrans.class);
	
	public void ping( JAppContext appCtx, HealthReqModel reqModel, HealthRspModel rspModel )
	{
		logger.info(String.format("TEST:recv trans message.[%s]",reqModel.getMsg()));
		
		rspModel.setAppName(JAppConfig.getConfigCache().APP_NAME);
		rspModel.setIpAddr(JStringUtil.getLocalIP());
		rspModel.setRetMsg("pong");
		rspModel.setYourMsg(reqModel.getMsg());
	}
	
	public void testext( JAppContext appCtx ) throws JTransException
	{
		throw new JTransException("0001","some JTransException error happened!");
	}
	
	public void testexr( JAppContext appCtx )
	{
		throw new RuntimeException("0002 Runtime some error happened!");
	}
}
