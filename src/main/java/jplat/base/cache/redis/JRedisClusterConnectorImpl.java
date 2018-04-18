package jplat.base.cache.redis;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jplat.base.constant.KPlatResponseCode;
import jplat.error.exception.JSystemException;
import jplat.tools.config.JAppConfig;
import jplat.tools.string.JStringUtil;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import z.log.tracelog.JLog;
import z.log.tracelog.JTraceLogUtils;
import z.log.tracelog.KTraceLog;

public class JRedisClusterConnectorImpl extends JIRedisConnector
{
	private static Logger logger = LoggerFactory.getLogger(JRedisClusterConnectorImpl.class);
	
	private int connTimeout = 5000;
	private int redirect = 5;
	
	//not elastic
	private JedisCluster jedisCluster;
	
	private Map<Integer,JedisCluster> cacheMap = new ConcurrentHashMap<Integer,JedisCluster>();

	private JRedisClusterConnectorImpl()
	{
		try {
			init();
		} catch (JSystemException e) {
			// TODO Auto-generated catch block
			//			e.printStackTrace();
			JTraceLogUtils.getExceptionFullLog(e, JAppConfig.getConfigCache().LOG_TRACE_CNT, true);
			throw new RuntimeException("JRedisClusterConnectorImpl init failed.");
		}
	}

	private static final class Holder
	{
		private static final JRedisClusterConnectorImpl  connector = new JRedisClusterConnectorImpl();
	}

	public static JRedisClusterConnectorImpl getInstance()
	{
		return Holder.connector;
	}

	private boolean init() throws JSystemException
	{
		connTimeout = JAppConfig.getConfigCache().REDIS_CONNECT_TIME_OUT;
		redirect = JAppConfig.getConfigCache().REDIS_REDIRECT;
		
		jedisCluster = new JedisCluster( getHosts(), connTimeout, connTimeout, redirect, poolConfig );
		
		/********* 获取集群服务器  **********/
		JLog.log(JTraceLogUtils.getTraceLog(KTraceLog.ACTION_JINIT, KTraceLog.EVENT_SUCCESS, "none",
				JTraceLogUtils.buildUserData("REDIS_INIT","clusters="+JAppConfig.getConfigCache().REDIS_CLUSTER)));
		return true;
	}

	/**
	 * 获取集群实例.
	 * 2018年4月2日下午4:15:02
	 * getCluster
	 * @return
	 * @throws JSystemException
	 */
	public JedisCluster getCluster()
	{
		return jedisCluster;
	}

	/**
	 * 从配置获取集群地址.
	 * 2018年4月2日下午4:14:50
	 * getHosts
	 * @return
	 * @throws JSystemException
	 */
	private Set<HostAndPort> getHosts() throws JSystemException
	{
		String hosts = JAppConfig.getConfigCache().REDIS_CLUSTER;
		if ( JStringUtil.isEmpty(hosts) )
		{
			throw new JSystemException(KPlatResponseCode.CD_PARA_ERROR,KPlatResponseCode.MSG_PARA_ERROR);
		}

		Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();
		String hostArr[] = hosts.split(",");
		for ( int i = 0; i < hostArr.length; ++i )
		{
			String[] addrInfo = hostArr[i].split(":");

			//			logger.info(String.format("ip=%s,port=%s",addrInfo[0],addrInfo[1]));
			jedisClusterNode.add(new HostAndPort(addrInfo[0], Integer.parseInt(addrInfo[1])));
		}

		//		logger.info("jedisClusterNode|"+JsonCoder.toJsonString(jedisClusterNode));
		return jedisClusterNode;
	}

	@Override
	public boolean set(String key, String value) {
		// TODO Auto-generated method stub
		
		JedisCluster jc = getCluster();
		
		String retMsg = jc.set(key, value);
		if ( !REDIS_RET_OK.equals(retMsg) )
		{
			return false;
		}
		
		return true;
	}

	@Override
	public String get(String key) {
		// TODO Auto-generated method stub
		
		JedisCluster jc = getCluster();
		
		return jc.get(key);
	}

	@Override
	public long del(String key) {
		// TODO Auto-generated method stub
		
		JedisCluster jc = getCluster();
		
		return jc.del(key);
	}

	@Override
	public boolean set(String key, String value, int timeOutSec)
	{
		boolean success = true;
		JedisCluster jc = getCluster();
		
		String retMsg = jc.setex(key, timeOutSec, value);
		if ( !REDIS_RET_OK.equals(retMsg))
		{
			success = false;
			return false;
		}
		
		return true;
	}

	@Override
	public String get(String key, int timeOutSec) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long hset(String key, String field, String value) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String hget(String key, String field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long hset(String key, String field, String value, int timeout) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void hset(String key, Map<String, String> sessMap, int timeout) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String hget(String key, String field, int timeout) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> hmget(String key, int timeout, String... fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long hdel(String key, String field) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long sadd(String key, String... members) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long sadd(String key, int timeout, String... members) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean sismember(String key, String member) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long ttl(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean exists(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long pexpireAt(String key, long millseconds) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long incr(String key) {
		// TODO Auto-generated method stub
		return 0;
	}
}