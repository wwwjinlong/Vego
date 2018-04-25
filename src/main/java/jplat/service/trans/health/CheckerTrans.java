package jplat.service.trans.health;

import org.springframework.stereotype.Component;

import jplat.core.trans.JAppContext;
import vegos.app.auto.transmodel.chk.HealthCheckerRspModel;

@Component
public class CheckerTrans
{
	public void ping( JAppContext appCtx, HealthCheckerRspModel rspModel )
	{
		rspModel.setRspmsg("pong");
	}
}
