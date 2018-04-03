package jplat.service.cache.redis;

import jplat.base.constant.KPlatResponseCode;
import jplat.core.cache.redis.JRedisConnector;
import jplat.core.session.redis.JRedisSession;
import jplat.error.exception.JSystemException;
import jplat.service.cache.JICrossCache;
import jplat.tools.string.DateUtil;
import jplat.tools.string.JRandomUtil;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import z.log.tracelog.JTraceLogUtils;
import z.log.tracelog.KTraceLog;

/**
 * 缓存只有在创建的时候才有效.
 * @author zhangcq
 *
 */
public class JRedisCache implements JICrossCache
{
	private static Logger logger = LoggerFactory.getLogger(JRedisSession.class);
	
	private static long REDIS_SETOK_0 = 0;
	private static long REDIS_SETOK_1 = 1;
	
	private String cacheKey;
	private boolean isNew = true;			//only create use this field.

	private JRedisConnector connector = JRedisConnector.getInstance();
	
	public JRedisCache( boolean createNew ) throws JSystemException
	{
		this(PREFIX+JRandomUtil.getUUID(),TIME_OUT_SECONDS,createNew);
	}
	
	public JRedisCache( String id, boolean createNew ) throws JSystemException
	{
		this(id,TIME_OUT_SECONDS,createNew);
	}

	public JRedisCache( String id, int time_out, boolean createNew ) throws JSystemException
	{
		cacheKey = id;
		
		if ( time_out < 0 || time_out > 43200 )		//12h max.
		{
			logger.error("缓存时间太长.");
			throw new JSystemException(KPlatResponseCode.CD_REDIS_SESSION_INIT_ERR,"缓存时间超长.");
		}
		
		if ( createNew )
		{
			if( connector.hset(cacheKey,K_CREATE_TIME,DateUtil.todayStr(DateUtil.FMT_ALL),time_out) == JRedisConnector.EX_OLD )
			{
				isNew = false;
				logger.info(JTraceLogUtils.getTraceLog(KTraceLog.ACTION_REDIS, KTraceLog.EVENT_POINT, "", JTraceLogUtils.buildUserData("cache exists,but create.",cacheKey)));
			}
		}
		else
		{
			if( connector.hget(cacheKey,K_CREATE_TIME) == null  )
			{
				logger.error(JTraceLogUtils.getTraceLog(KTraceLog.ACTION_REDIS, KTraceLog.EVENT_FAIL, "", JTraceLogUtils.buildUserData("no cache found",cacheKey)));
				throw new JSystemException(KPlatResponseCode.CD_REDIS_SESSION_INIT_ERR,KPlatResponseCode.MSG_FAIL);
			}
		}
	}

	@Override
	public String get(String key) {
		// TODO Auto-generated method stub
		return connector.hget(cacheKey,key);
	}

	@Override
	public boolean set(String key, String value) {
		// TODO Auto-generated method stub
		long retl = connector.hset(cacheKey,key,value);
		return retl==REDIS_SETOK_0||retl==REDIS_SETOK_1?true:false;
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return cacheKey;
	}

	@Override
	public boolean remove(String key){
		// TODO Auto-generated method stub
		long ret = connector.hdel(cacheKey,key);
		return (ret==1 || ret == 0)?true:false;
	}

	@Override
	public boolean destroy() {
		// TODO Auto-generated method stub
		long ret = connector.del(cacheKey);
		
		return (ret==1 || ret == 0)?true:false;
	}

	@Override
	public long timeleft() {
		// TODO Auto-generated method stub
		return connector.ttl(cacheKey);
	}

	@Override
	public boolean isNew() {
		// TODO Auto-generated method stub
		return isNew;
	}
	
/*	public static void main(String args[]) throws JSystemException
	{
		JRedisSession session = new JRedisSession();
		
		Map<String,String> data = new HashMap<String,String>();
		data.put("k1", "v1");
		data.put("k2", "v2");
		session.setObj("k1", data );
		XLog.log(""+session.getSessionID());
		
		data = (HashMap<String,String>)session.getObj("k1");
		XLog.log(JsonCoder.toJsonString(data));
	}*/
	
}