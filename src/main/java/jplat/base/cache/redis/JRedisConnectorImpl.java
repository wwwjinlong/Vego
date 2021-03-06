package jplat.base.cache.redis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jplat.tools.coder.JsonCoder;
import jplat.tools.config.JAppConfig;
import jplat.tools.string.JRandomUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import z.log.tracelog.JLog;
import z.log.tracelog.JTraceLogUtils;
import z.log.tracelog.KTraceLog;

public class JRedisConnectorImpl extends JIRedisConnector
{
	private static Logger logger = LoggerFactory.getLogger(JRedisConnectorImpl.class);
	
	private JedisPool redisPool;
	
	private JRedisConnectorImpl()
	{
		init();
	}
	
	private boolean init()
	{
		redisPool = new JedisPool(poolConfig,JAppConfig.getConfigCache().REDIS_SERVER,
					JAppConfig.getConfigCache().REDIS_PORT,JAppConfig.getConfigCache().REDIS_CONNECT_TIME_OUT);
		
		JLog.log(JTraceLogUtils.getTraceLog(KTraceLog.ACTION_JINIT, KTraceLog.EVENT_SUCCESS, "none",
				JTraceLogUtils.buildUserData("JRedisConnectorImpl",JAppConfig.getConfigCache().REDIS_SERVER,
						""+JAppConfig.getConfigCache().REDIS_PORT,""+JAppConfig.getConfigCache().REDIS_CONNECT_TIME_OUT)));
				
		return true;
	}

	private static final class Holder
	{
		private static final JRedisConnectorImpl  connector = new JRedisConnectorImpl();
	}

	public static JRedisConnectorImpl getInstance()
	{
		return Holder.connector;
	}

	//---------------getter-settter-operations--------------

	/**
	 * 设置值
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set( String key, String value )
	{
		Jedis jedis = null;
		try
		{
			jedis = redisPool.getResource();
			if ( password != null )
			{
				jedis.auth(password);
			}

			String retMsg = jedis.set(key, value);
			if ( !REDIS_RET_OK.equals(retMsg))
			{
				return false;
			}
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}
		finally
		{
			if ( jedis != null )
			{
				jedis.close();
			}
		}

		return true;
	}

	/**
	 * 获取值.
	 * @param key
	 * @param value
	 * @return
	 */
	public String get( String key )
	{
		Jedis jedis = null;
		try
		{
			jedis = redisPool.getResource();
			if ( password != null )
			{
				jedis.auth(password);
			}

			return jedis.get(key);
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}
		finally
		{
			if ( jedis != null )
			{
				jedis.close();
			}
		}

		return null;
	}

	/**
	 * 删除.
	 * @author zhangcq
	 * @date Jan 6, 2017
	 * @comment 
	 * @param key
	 * @return
	 */
	public long del( String key )
	{
		Jedis jedis = null;
		try
		{
			jedis = redisPool.getResource();
			if ( password != null )
			{
				jedis.auth(password);
			}

			long ret = jedis.del(key);

			return ret;
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}
		finally
		{
			if ( jedis != null )
			{
				jedis.close();
			}
		}

		return FAIL;
	}

	/**
	 * 设置值并超时时间.
	 * @param key
	 * @param value
	 * @param timeOutSec
	 * @return
	 */
	public boolean set( String key, String value, int timeOutSec )
	{
		boolean success = true;
		String mark = "";
		if ( logOn )
		{
			mark = JRandomUtil.getRandomSequence(KTraceLog.MARK_LENGTH);
			logger.info(JTraceLogUtils.getStartTraceLog(KTraceLog.ACTION_REDIS, mark, JTraceLogUtils.buildUserData("set",key) ));
		}

		Jedis jedis = null;
//		Pipeline pl = null;
		try
		{
			jedis = redisPool.getResource();
			if ( password != null )
			{
				jedis.auth(password);
			}
			
			String retMsg = jedis.setex(key, timeOutSec, value);
			
//			pl = jedis.pipelined();
//			Response<String> response = pl.set(key, value);
//			pl.expire(key, timeOutSec );
//			pl.sync();

			//			pl.close();
			//			jedis.close();
			if ( !REDIS_RET_OK.equals(retMsg))
			{
				success = false;
				return false;
			}
		}
		catch ( Exception e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			success = false;
		}
		finally
		{
			if ( logOn )
			{
				logger.info(JTraceLogUtils.getEndTraceLog(KTraceLog.ACTION_REDIS, mark, JTraceLogUtils.buildUserData("set",key,""+success) ));
			}

/*			if ( pl != null )
			{
				try {
					pl.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}*/

			if ( jedis != null )
			{
				jedis.close();
			}
		}

		return true;
	}

	/**
	 * 返回值并更新超时时间.
	 * @param key
	 * @param value
	 * @param timeOutSec
	 * @return
	 */
	public String get( String key, int timeOutSec )
	{
		boolean success = true;
		String mark = "";
		if ( logOn )
		{
			mark = JRandomUtil.getRandomSequence(KTraceLog.MARK_LENGTH);
			logger.info(JTraceLogUtils.getStartTraceLog(KTraceLog.ACTION_REDIS, mark, JTraceLogUtils.buildUserData("get",key) ));
		}

		Jedis jedis = null;
		Pipeline pl = null;
		try
		{
			jedis = redisPool.getResource();
			if ( password != null )
			{
				jedis.auth(password);
			}

			pl = jedis.pipelined();
			Response<String> response = pl.get(key);
			pl.expire(key, timeOutSec );
			pl.sync();
			//		pl.close();
			//		jedis.close();

			return response.get();
		}
		catch ( Exception e)
		{
			success=false;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if ( logOn )
			{
				logger.info(JTraceLogUtils.getEndTraceLog(KTraceLog.ACTION_REDIS, mark, JTraceLogUtils.buildUserData("get",key,""+success) ));
			}

			if ( pl != null )
			{
				try {
					pl.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if ( jedis != null )
			{
				jedis.close();
			}
		}

		return null;
	}

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
	public long hset( String key, String field, String value )
	{
		Jedis jedis = null;
		try
		{
			jedis = redisPool.getResource();
			if ( password != null )
			{
				jedis.auth(password);
			}

			long retl = jedis.hset(key,field,value);
			//		jedis.close();
			return retl;
		}
		catch ( Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if ( jedis != null )
			{
				jedis.close();
			}
		}

		return FAIL;
	}

	/***
	 * 获取值.
	 */
	public String hget( String key, String field )
	{
		Jedis jedis = null;
		try
		{
			jedis = redisPool.getResource();
			if ( password != null )
			{
				jedis.auth(password);
			}

			String retstr = jedis.hget(key,field);
			//			jedis.close();

			return retstr;
		}
		catch ( Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if ( jedis != null )
			{
				jedis.close();
			}
		}

		return null;
	}

	/***
	 * hset方法.
	 * @param key
	 * @param field
	 * @param value
	 * @param timeout
	 * @return 0-覆盖旧域名.1-产生新域.
	 */
	public long hset( String key, String field, String value, int timeout )
	{
		boolean success = true;
		String mark = "";
		if ( logOn )
		{
			mark = JRandomUtil.getRandomSequence(KTraceLog.MARK_LENGTH);
			logger.info(JTraceLogUtils.getStartTraceLog(KTraceLog.ACTION_REDIS, mark, JTraceLogUtils.buildUserData("hset",key,field,""+timeout) ));
		}

		Jedis jedis = null;
		Pipeline pl = null;
		try
		{
			jedis = redisPool.getResource();
			if ( password != null )
			{
				jedis.auth(password);
			}

			pl = jedis.pipelined();
			Response<Long> response = pl.hset(key,field,value);
			pl.expire(key, timeout );
			pl.sync();

			//			pl.close();
			//			jedis.close();

			return response.get();
		}
		catch ( Exception e)
		{
			success=false;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if ( logOn )
			{
				logger.info(JTraceLogUtils.getEndTraceLog(KTraceLog.ACTION_REDIS, mark, JTraceLogUtils.buildUserData("hset",key,field,""+success) ));
			}

			if ( pl != null )
			{
				try {
					pl.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if ( jedis != null )
			{
				jedis.close();
			}
		}

		return FAIL;
	}

	/***
	 * hset方法.
	 * @param key
	 * @param field
	 * @param value
	 * @param timeout
	 * @return 0-覆盖旧域名.1-产生新域.
	 */
	public void hset( String key, Map<String,String> sessMap, int timeout )
	{
		boolean success = true;
		String mark = "";
		if ( logOn )
		{
			mark = JRandomUtil.getRandomSequence(KTraceLog.MARK_LENGTH);
			logger.info(JTraceLogUtils.getStartTraceLog(KTraceLog.ACTION_REDIS, mark, JTraceLogUtils.buildUserData("hset.maps",key) ));
		}

		Jedis jedis = null;
		Pipeline pl = null;
		try
		{
			jedis = redisPool.getResource();
			if ( password != null )
			{
				jedis.auth(password);
			}

			pl = jedis.pipelined();
			for ( String tmpKey : sessMap.keySet() )
			{
				Response<Long> response = pl.hset(key,tmpKey,sessMap.get(tmpKey));
			}

			pl.expire(key, timeout );
			pl.sync();
			//			pl.close();
			//			jedis.close();

		}
		catch ( Exception e)
		{
			success=false;
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			if ( logOn )
			{
				logger.info(JTraceLogUtils.getEndTraceLog(KTraceLog.ACTION_REDIS, mark, JTraceLogUtils.buildUserData("hset.maps",key,""+success) ));
			}

			if ( pl != null )
			{
				try {
					pl.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if ( jedis != null )
			{
				jedis.close();
			}
		}
	}

	/***
	 * 获取值.
	 */
	public String hget( String key, String field, int timeout )
	{
		boolean success = true;
		String mark = "";
		if ( logOn )
		{
			mark = JRandomUtil.getRandomSequence(KTraceLog.MARK_LENGTH);
			logger.info(JTraceLogUtils.getStartTraceLog(KTraceLog.ACTION_REDIS, mark, JTraceLogUtils.buildUserData("hget",key,field,""+timeout) ));
		}

		Jedis jedis = null;
		Pipeline pl = null;
		try
		{
			jedis = redisPool.getResource();
			if ( password != null )
			{
				jedis.auth(password);
			}

			pl = jedis.pipelined();
			Response<String> response = pl.hget(key,field);		//response 是否会为空.
			pl.expire(key, timeout );
			pl.sync();

			//			pl.close();
			//			jedis.close();

			return response.get();
		}
		catch ( Exception e)
		{
			success=false;
			logger.error(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if ( logOn )
			{
				logger.info(JTraceLogUtils.getEndTraceLog(KTraceLog.ACTION_REDIS, mark, JTraceLogUtils.buildUserData("hget",key,field,""+success) ));
			}

			if ( pl != null )
			{
				try {
					pl.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if ( jedis != null )
			{
				jedis.close();
			}
		}

		return null;
	}
	
	/***
	 * 获取值.
	 */
	public Map<String,String> hmget( String key, int timeout, String ...fields )
	{
		boolean success = true;
		String mark = "";
		if ( logOn )
		{
			mark = JRandomUtil.getRandomSequence(KTraceLog.MARK_LENGTH);
			logger.info(JTraceLogUtils.getStartTraceLog(KTraceLog.ACTION_REDIS,
					mark, JTraceLogUtils.buildUserData("hmget", JTraceLogUtils.buildUserData(fields),""+timeout)) );
		}

		Jedis jedis = null;
		Pipeline pl = null;
		try
		{
			jedis = redisPool.getResource();
			if ( password != null )
			{
				jedis.auth(password);
			}

			List<Response<String>> rspList = new ArrayList<Response<String>>();
			
			pl = jedis.pipelined();
			for ( int i = 0; i < fields.length; ++i )
			{
				Response<String> response = pl.hget(key,fields[i]);		//response 是否会为空.
				rspList.add(response);
			}
			
			pl.expire(key, timeout );
			pl.sync();

			Map<String,String> retMap = new HashMap<String,String>();
			
			int size = rspList.size();
			for ( int i = 0; i < size; ++i )
			{
				retMap.put(fields[i], rspList.get(i).get());
			}
			
			return retMap;
		}
		catch ( Exception e)
		{
			success=false;
			logger.error(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if ( logOn )
			{
				logger.info(JTraceLogUtils.getEndTraceLog(KTraceLog.ACTION_REDIS,
							mark, JTraceLogUtils.buildUserData("hmget",key,JTraceLogUtils.buildUserData(fields),""+success) ));
			}

			if ( pl != null )
			{
				try {
					pl.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if ( jedis != null )
			{
				jedis.close();
			}
		}

		return null;
	}

	/**
	 * 按照域删除.
	 * @author zhangcq
	 * @date Jan 6, 2017
	 * @comment 
	 * @param key
	 * @param field
	 * @return
	 */
	public long hdel( String key , String field )
	{
		boolean success = true;
		String mark = "";
		if ( logOn )
		{
			mark = JRandomUtil.getRandomSequence(KTraceLog.MARK_LENGTH);
			logger.info(JTraceLogUtils.getStartTraceLog(KTraceLog.ACTION_REDIS, mark, JTraceLogUtils.buildUserData("hdel",key,field) ));
		}

		Jedis jedis = null;
		try
		{
			jedis = redisPool.getResource();
			if ( password != null )
			{
				jedis.auth(password);
			}

			long ret = jedis.del(key,field);
			return ret;
		}
		catch ( Exception e)
		{
			// TODO Auto-generated catch block
			success=false;
			e.printStackTrace();
		}
		finally
		{
			if ( logOn )
			{
				logger.info(JTraceLogUtils.getEndTraceLog(KTraceLog.ACTION_REDIS, mark, JTraceLogUtils.buildUserData("hdel",key,field,""+success) ));
			}

			if ( jedis != null )
			{
				jedis.close();
			}
		}

		return FAIL;
	}

	/**
	 * 集合增加数据.
	 * @author zhangcq
	 * @date Feb 13, 2017
	 * @comment 
	 * @param key
	 * @param field
	 * @return
	 */
	public long sadd( String key , String ...members )
	{
		boolean success = true;
		String mark = "";
		if ( logOn )
		{
			mark = JRandomUtil.getRandomSequence(KTraceLog.MARK_LENGTH);
			logger.info(JTraceLogUtils.getStartTraceLog(KTraceLog.ACTION_REDIS, mark, JTraceLogUtils.buildUserData("sadd",key) ));
		}

		Jedis jedis = null;
		try
		{
			jedis = redisPool.getResource();
			if ( password != null )
			{
				jedis.auth(password);
			}

			long ret = jedis.sadd(key, members);
			return ret;
		}
		catch ( Exception e)
		{
			// TODO Auto-generated catch block
			success=false;
			e.printStackTrace();
		}
		finally
		{
			if ( logOn )
			{
				logger.info(JTraceLogUtils.getEndTraceLog(KTraceLog.ACTION_REDIS, mark, JTraceLogUtils.buildUserData("sadd",key,""+success) ));
			}

			if ( jedis != null )
			{
				jedis.close();
			}
		}

		return FAIL;
	}

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
	public long sadd( String key, int timeout, String ...members )
	{
		boolean success = true;
		String mark = "";
		if ( logOn )
		{
			mark = JRandomUtil.getRandomSequence(KTraceLog.MARK_LENGTH);
			logger.info(JTraceLogUtils.getStartTraceLog(KTraceLog.ACTION_REDIS, mark, JTraceLogUtils.buildUserData("sadd_t",key,timeout+"") ));
		}

		Jedis jedis = null;
		Pipeline pl = null;
		try
		{
			jedis = redisPool.getResource();
			if ( password != null )
			{
				jedis.auth(password);
			}

			pl = jedis.pipelined();
			Response<Long> response = pl.sadd(key,members);
			pl.expire(key, timeout );
			pl.sync();

			//			pl.close();
			//			jedis.close();

			return response.get();
		}
		catch ( Exception e)
		{
			success=false;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if ( logOn )
			{
				logger.info(JTraceLogUtils.getEndTraceLog(KTraceLog.ACTION_REDIS, mark, JTraceLogUtils.buildUserData("sadd_t",key,""+success) ));
			}

			if ( pl != null )
			{
				try {
					pl.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if ( jedis != null )
			{
				jedis.close();
			}
		}

		return FAIL;
	}
	
	public boolean sismember( String key, String member )
	{

		boolean success = true;
		String mark = "";
		if ( logOn )
		{
			mark = JRandomUtil.getRandomSequence(KTraceLog.MARK_LENGTH);
			logger.info(JTraceLogUtils.getStartTraceLog(KTraceLog.ACTION_REDIS, mark, JTraceLogUtils.buildUserData("sismember",key,member) ));
		}

		Jedis jedis = null;
		try
		{
			jedis = redisPool.getResource();
			if ( password != null )
			{
				jedis.auth(password);
			}

			return jedis.sismember(key, member);
		}
		catch ( Exception e)
		{
			// password != null
			success=false;
			e.printStackTrace();
		}
		finally
		{
			if ( logOn )
			{
				logger.info(JTraceLogUtils.getEndTraceLog(KTraceLog.ACTION_REDIS, mark, JTraceLogUtils.buildUserData("sismember",key,member,""+success) ));
			}

			if ( jedis != null )
			{
				jedis.close();
			}
		}

		return false;
	}
	
	/**
	 * -2表示已过期.
	 * -1表示永远不过期.
	 * @param key
	 * @return
	 */
	public long ttl( String key )
	{
		boolean success = true;
		String mark = "";
		if ( logOn )
		{
			mark = JRandomUtil.getRandomSequence(KTraceLog.MARK_LENGTH);
			logger.info(JTraceLogUtils.getStartTraceLog(KTraceLog.ACTION_REDIS, mark, JTraceLogUtils.buildUserData("ttl",key) ));
		}

		Jedis jedis = null;
		try
		{
			jedis = redisPool.getResource();
			if ( password != null )
			{
				jedis.auth(password);
			}

			return jedis.ttl(key);
		}
		catch ( Exception e)
		{
			// TODO Auto-generated catch block
			success=false;
			e.printStackTrace();
		}
		finally
		{
			if ( logOn )
			{
				logger.info(JTraceLogUtils.getEndTraceLog(KTraceLog.ACTION_REDIS, mark, JTraceLogUtils.buildUserData("ttl",key,""+success) ));
			}

			if ( jedis != null )
			{
				jedis.close();
			}
		}
		
		return -2;
	}
	
	public boolean exists( String key )
	{
		boolean success = true;
		String mark = "";
		if ( logOn )
		{
			mark = JRandomUtil.getRandomSequence(KTraceLog.MARK_LENGTH);
			logger.info(JTraceLogUtils.getStartTraceLog(KTraceLog.ACTION_REDIS, mark, JTraceLogUtils.buildUserData("exists",key) ));
		}

		Jedis jedis = null;
		try
		{
			jedis = redisPool.getResource();
			if ( password != null )
			{
				jedis.auth(password);
			}
			
			return jedis.exists(key);
		}
		catch ( Exception e)
		{
			success=false;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if ( logOn )
			{
				logger.info(JTraceLogUtils.getEndTraceLog(KTraceLog.ACTION_REDIS, mark, JTraceLogUtils.buildUserData("exists",key,""+success) ));
			}

			if ( jedis != null )
			{
				jedis.close();
			}
		}

		return false;
	}
	
	/**
	 * Oct 25, 201710:17:15 AM
	 * pexpireat
	 * @param key
	 * @param millseconds
	 * @return 1-成功.
	 */
	public long pexpireAt( String key, long millseconds )
	{
		boolean success = true;
		String mark = "";
		if ( logOn )
		{
			mark = JRandomUtil.getRandomSequence(KTraceLog.MARK_LENGTH);
			logger.info(JTraceLogUtils.getStartTraceLog(KTraceLog.ACTION_REDIS, mark, JTraceLogUtils.buildUserData("exists",key) ));
		}

		Jedis jedis = null;
		try
		{
			jedis = redisPool.getResource();
			if ( password != null )
			{
				jedis.auth(password);
			}
			
			return jedis.pexpireAt(key, millseconds );
		}
		catch ( Exception e)
		{
			success=false;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if ( logOn )
			{
				logger.info(JTraceLogUtils.getEndTraceLog(KTraceLog.ACTION_REDIS, mark, JTraceLogUtils.buildUserData("exists",key,""+success) ));
			}

			if ( jedis != null )
			{
				jedis.close();
			}
		}

		return FAIL;
	}
	
	public long incr( String key )
	{
		boolean success = true;
		String mark = "";
		if ( logOn )
		{
			mark = JRandomUtil.getRandomSequence(KTraceLog.MARK_LENGTH);
			logger.info(JTraceLogUtils.getStartTraceLog(KTraceLog.ACTION_REDIS, mark, JTraceLogUtils.buildUserData("incr",key) ));
		}

		Jedis jedis = null;
		try
		{
			jedis = redisPool.getResource();
			if ( password != null )
			{
				jedis.auth(password);
			}
			
			return jedis.incr(key);
		}
		catch ( Exception e)
		{
			success=false;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if ( logOn )
			{
				logger.info(JTraceLogUtils.getEndTraceLog(KTraceLog.ACTION_REDIS, mark, JTraceLogUtils.buildUserData("incr",key,""+success) ));
			}

			if ( jedis != null )
			{
				jedis.close();
			}
		}

		return FAIL;
	}
}