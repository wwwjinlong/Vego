package jplat.tools.config;

import z.log.tracelog.JTraceLogUtils;
import z.log.tracelog.KTraceLog;
import z.log.tracelog.XLog;
import jplat.core.session.redis.JRedisConnector;

public class JConfigLoader
{
	public JConfigLoader()
	{
		init();
	}
	
	private void init()
	{
		//加载JAppConfig
		JAppConfig.getTempDir("1");
		XLog.log(JTraceLogUtils.getTraceLog(KTraceLog.ACTION_JINIT, KTraceLog.EVENT_POINT, "none",
							JTraceLogUtils.buildUserData("JAppConfig",""+JAppConfig.SESSION_TYPE,JAppConfig.getTempDir("0"))));
		
		JRedisConnector.getInstance();
	}
	
	public static void main(String args[])
	{
		JConfigLoader loader = new JConfigLoader();
	}
}
