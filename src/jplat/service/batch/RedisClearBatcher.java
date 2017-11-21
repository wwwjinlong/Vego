package jplat.service.batch;

import jplat.tools.config.JConfigManager;
import jplat.tools.string.DateUtil;
import z.log.tracelog.XLog;

//@PropertySource("classpath:conf/system/batch.properties")
//@Component
public class RedisClearBatcher
{
	//cache.clear.runtime
//	@Scheduled(cron="5 * * * * ?")
	public void resetDefendCache()
	{
		XLog.log("RedisClearBatcher.resetDefendCache:"+DateUtil.todayStr(DateUtil.FMT_ALL));
		
		int timeout = JConfigManager.getInstance().getSystemConfig().getInt("cache.defend.timeout",4200);
	}
}
