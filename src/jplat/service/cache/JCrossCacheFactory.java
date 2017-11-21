package jplat.service.cache;

import jplat.error.exception.JSystemException;
import jplat.service.cache.redis.JRedisCache;
import z.log.tracelog.XLog;

public class JCrossCacheFactory
{
	/**
	 * 新建缓存.
	 * 现在默认的是redis缓存.
	 * @return
	 * @throws JSystemException 
	 */
	public static JICrossCache buildCrossCache()
	{
		try {
			return new JRedisCache(true);
		} catch (JSystemException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		
		return null;
	}
	
	public static JICrossCache buildCrossCache( String cacheId ) 
	{
		try {
			return new JRedisCache(cacheId,true);
		} catch (JSystemException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		
		return null;
	}
	
	public static JICrossCache buildCrossCache( String cacheId, int timeout )
	{
		try {
			return new JRedisCache(cacheId,timeout,true );
		} catch (JSystemException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		
		return null;
	}
	
	public static JICrossCache getCrossCache( String cacheId )
	{
		try {
			return new JRedisCache(cacheId,false );
		} catch (JSystemException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void test()
	{
		JICrossCache ic = buildCrossCache();
		ic.set("k", "123456");

		String key1 = ic.get("k");
		XLog.log("1keyid=%s,k1=%s",ic.getID(), key1);
		XLog.log("2is_new=%s", ic.isNew()+"");
		XLog.log("30--------------------");

		ic = buildCrossCache(ic.getID(),999);
		XLog.log("4is_new=%s,value=%s", ic.isNew()+"",ic.get("k"));

		ic = getCrossCache(ic.getID());
		XLog.log("5-------------"+ic.get("k"));
	}
	
	public static void main(String args[])
	{
		test();
	}
}
