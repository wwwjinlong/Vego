package jplat.core.cache.redis;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jplat.base.constant.KPlatResponseCode;
import jplat.error.exception.JSystemException;
import jplat.tools.coder.JsonCoder;
import jplat.tools.config.JAppConfig;
import jplat.tools.string.JStringUtil;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import z.log.tracelog.JLog;
import z.log.tracelog.JTraceLogUtils;
import z.log.tracelog.KTraceLog;

public class JRedisClusterConnector
{
	private static Logger logger = LoggerFactory.getLogger(JRedisClusterConnector.class);
	
	public boolean logOn = false;
	
	private int maxConn=0,minIdle=0, maxWaitMills=0,connTimeout=0,redirect=0;
	
	private JedisPoolConfig config;
	
	private JRedisClusterConnector()
	{
		try {
			init();
		} catch (JSystemException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			JTraceLogUtils.getExceptionFullLog(e, JAppConfig.getConfigCache().LOG_TRACE_CNT, true);
		}
	}
	
	private static final class Holder
	{
		private static final JRedisClusterConnector  connector = new JRedisClusterConnector();
	}

	public static JRedisClusterConnector getInstance()
	{
		return Holder.connector;
	}
	
	private boolean init() throws JSystemException
	{
		// 这里还是依赖了Spring哦.
		// QConfig qconfig = AppContextHolder.getContext().getBean(QConfig.class);
		JLog.loginit("REDIS_POOL_INIT start");
		
//		ResourceBundle resBundle = ResourceBundle.getBundle("conf/redis_conf");
		connTimeout = JAppConfig.getConfigCache().REDIS_CONNECT_TIME_OUT;
		maxWaitMills = JAppConfig.getConfigCache().REDIS_MAX_WAIT_MILLIS;
		
		maxConn = JAppConfig.getConfigCache().REDIS_MAX_CONNECT;
		minIdle = JAppConfig.getConfigCache().REDIS_MIN_IDLE;
		redirect = JAppConfig.getConfigCache().REDIS_REDIRECT;
		
		//print the monitor log or not.
		logOn = JAppConfig.getConfigCache().REDIS_LOGON;

		config = new JedisPoolConfig();
		//		config.setBlockWhenExhausted(true);
		config.setMaxTotal(maxConn);
		config.setMinIdle(minIdle);
		config.setMaxWaitMillis(maxWaitMills);
		
		/********* 获取集群服务器  **********/
		JLog.log(JTraceLogUtils.getTraceLog(KTraceLog.ACTION_JINIT, KTraceLog.EVENT_SUCCESS, "none",
				JTraceLogUtils.buildUserData("REDIS_INIT","clusters="+JAppConfig.getConfigCache().REDIS_CLUSTER,JsonCoder.toJsonString(config))));
				
		return true;
	}
	
	/**
	 * 获取集群实例.
	 * 2018年4月2日下午4:15:02
	 * getCluster
	 * @return
	 * @throws JSystemException
	 */
	public JedisCluster getCluster() throws JSystemException
	{
	    return new JedisCluster( getHosts(), connTimeout, maxWaitMills, redirect, config );
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
			
			logger.info(String.format("ip=%s,port=%s",addrInfo[0],addrInfo[1]));
			jedisClusterNode.add(new HostAndPort(addrInfo[0], Integer.parseInt(addrInfo[1])));
		}
		
		logger.info("jedisClusterNode|"+JsonCoder.toJsonString(jedisClusterNode));
		return jedisClusterNode;
	}
	
	public static void main( String args[] )
	{
		JRedisClusterConnector cluIns  = JRedisClusterConnector.getInstance();
		try {
			JedisCluster jc = cluIns.getCluster();
			jc.set("k11", "v11");
			JLog.logerr(jc.get("k11"));
		} catch (JSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}