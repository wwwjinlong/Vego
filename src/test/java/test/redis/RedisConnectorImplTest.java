package test.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jplat.base.cache.redis.JRedisConnectorImpl;
import jplat.tools.string.JRandomUtil;

public class RedisConnectorImplTest
{
	private static Logger logger = LoggerFactory.getLogger(RedisConnectorImplTest.class);
	
	/*************
	 * 			FIHISH
	 * 				*************/

	public static void main(String[] args )
	{
		testConcurrent();
	}
	
	private static void testConcurrent()
	{
		for ( int i = 0; i < 2; ++i )
		{
			new Thread()
			{
				@Override
				public void run() {
					// TODO Auto-generated method stub
					int max = 2;
					while ( --max > 0 )
					{
						String key = "K_"+JRandomUtil.getUUID();
						String value = "V_"+JRandomUtil.getRandomSequence(512);
						
						JRedisConnectorImpl.getInstance().hset(key,"userInfo",value,300);
						logger.info("STR:"+JRedisConnectorImpl.getInstance().hget(key,"userInfo"));
					}
				}
			}.start();
		}
	}
}
