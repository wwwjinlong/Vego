package jplat.tools.config;

import z.log.tracelog.JTraceLogUtils;
import z.log.tracelog.KTraceLog;
import jplat.base.cache.redis.JRedisConnectorImpl;
import z.log.tracelog.JLog;

/**
 * 该类用于系统启动时候加载基本参数，
 * 这样参数加载问题会在平台启动过程中提前暴露出来，
 * 而不是在运行使用时暴露出来。
 * 
 * @author zhangcq
 *
 */
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
		
		//加载配置.
		JAppConfig.getConfigCache();
		
		JLog.log(JTraceLogUtils.getTraceLog(KTraceLog.ACTION_JINIT, KTraceLog.EVENT_POINT, "none",
							JTraceLogUtils.buildUserData("JAppConfig",""+JAppConfig.getConfigCache().SESSION_TYPE,JAppConfig.getTempDir("0"))));
		
		JRedisConnectorImpl.getInstance();
	}
	
	public static void main(String args[])
	{
		JConfigLoader loader = new JConfigLoader();
	}
}
