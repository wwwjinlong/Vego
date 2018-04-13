package jplat.core.cache.redis;

import java.util.Map;

import jplat.tools.coder.JsonCoder;
import jplat.tools.config.JAppConfig;
import jplat.tools.string.JStringUtil;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import z.log.tracelog.JLog;
import z.log.tracelog.JTraceLogUtils;
import z.log.tracelog.KTraceLog;

public abstract class JIRedisConnector
{
	public static final long FAIL = -1;		//失败。
	public static final long EX_NEW = 1;		//新值.
	public static final long EX_OLD = 0;		//老值.
	public static final String REDIS_RET_OK = "OK";
	
	protected boolean logOn;
	protected String password;
	protected JedisPoolConfig poolConfig;
	
	public JIRedisConnector()
	{
		init();
	}
	
	private boolean init()
	{
		
		//print the monitor log or not.
		logOn = JAppConfig.getConfigCache().REDIS_LOGON;

		int maxConn=0,minIdle=0, maxWaitMills=0;
//		timeout = JAppConfig.getConfigCache().REDIS_CONNECT_TIME_OUT;

		maxWaitMills = JAppConfig.getConfigCache().REDIS_MAX_WAIT_MILLIS;
		maxConn = JAppConfig.getConfigCache().REDIS_MAX_CONNECT;
		minIdle = JAppConfig.getConfigCache().REDIS_MIN_IDLE;
		password = JAppConfig.getConfigCache().REDIS_PASSWORD;
		if ( JStringUtil.isEmpty(password) )
		{
			password = null;
		}

		poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(maxConn);
		poolConfig.setMinIdle(minIdle);
		poolConfig.setMaxWaitMillis(maxWaitMills);
//		config.setBlockWhenExhausted(true);
		
		//5000ms超时是可以，默认是2000
		JLog.log(JTraceLogUtils.getTraceLog(KTraceLog.ACTION_JINIT, KTraceLog.EVENT_SUCCESS, "none",
				JTraceLogUtils.buildUserData("JIRedisConnector",password,JsonCoder.toJsonString(poolConfig))));
				
		return true;
	
	}
	
	/**
	 * 设置值
	 * @param key
	 * @param value
	 * @return
	 */
	public abstract boolean set( String key, String value );

	/**
	 * 获取值.
	 * @param key
	 * @param value
	 * @return
	 */
	public abstract String get( String key );

	/**
	 * 删除.
	 * @author zhangcq
	 * @date Jan 6, 2017
	 * @comment 
	 * @param key
	 * @return
	 */
	public abstract long del( String key );

	/**
	 * 设置值并超时时间.
	 * @param key
	 * @param value
	 * @param timeOutSec
	 * @return
	 */
	public abstract boolean set( String key, String value, int timeOutSec );

	/**
	 * 返回值并更新超时时间.
	 * @param key
	 * @param value
	 * @param timeOutSec
	 * @return
	 */
	public abstract String get( String key, int timeOutSec );

	/******
	 ********************  Redis的HSET/HGET. ************************
	 */

	/***
	 * hset方法.
	 * @param key
	 * @param field
	 * @param value
	 * @return 0-覆盖旧域名.1-产生新域.
	 */
	public abstract long hset( String key, String field, String value );

	/***
	 * 获取值.
	 */
	public abstract String hget( String key, String field );

	/***
	 * hset方法.
	 * @param key
	 * @param field
	 * @param value
	 * @param timeout
	 * @return 0-覆盖旧域名.1-产生新域.
	 */
	public abstract long hset( String key, String field, String value, int timeout );

	/***
	 * hset方法.
	 * @param key
	 * @param field
	 * @param value
	 * @param timeout
	 * @return 0-覆盖旧域名.1-产生新域.
	 */
	public abstract void hset( String key, Map<String,String> sessMap, int timeout );

	/***
	 * 获取值.
	 */
	public abstract String hget( String key, String field, int timeout );
	
	/***
	 * 获取值.
	 */
	public abstract Map<String,String> hmget( String key, int timeout, String ...fields );

	/**
	 * 按照域删除.
	 * @author zhangcq
	 * @date Jan 6, 2017
	 * @comment 
	 * @param key
	 * @param field
	 * @return
	 */
	public abstract long hdel( String key , String field );

	/**
	 * 集合增加数据.
	 * @author zhangcq
	 * @date Feb 13, 2017
	 * @comment 
	 * @param key
	 * @param field
	 * @return
	 */
	public abstract long sadd( String key , String ...members );

	/**
	 * 集合增加
	 * @author zhangcq
	 * @date Feb 14, 2017
	 * @comment 
	 * @param key
	 * @param timeout
	 * @param members
	 * @return
	 */
	public abstract long sadd( String key, int timeout, String ...members );
	
	public abstract boolean sismember( String key, String member );
	
	/**
	 * -2表示已过期.
	 * -1表示永远不过期.
	 * @param key
	 * @return
	 */
	public abstract long ttl( String key );
	
	public abstract boolean exists( String key );
	
	/**
	 * Oct 25, 201710:17:15 AM
	 * pexpireat
	 * @param key
	 * @param millseconds
	 * @return 1-成功.
	 */
	public abstract long pexpireAt( String key, long millseconds );
	
	public abstract long incr( String key );
}