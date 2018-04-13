package test.redis;

import java.util.concurrent.Callable;

import jplat.error.exception.JTransException;
import jplat.tools.string.JStringUtil;
import redis.clients.jedis.JedisCluster;

public class MyRedisCallable implements Callable<String>
{
	private String key;
	private JedisCluster jc;
	
	public MyRedisCallable( String key, JedisCluster jc )
	{
		this.key = key;
		this.jc = jc;
	}
	
	@Override
	public String call() throws Exception {
		// TODO Auto-generated method stub
		return jc.get(key);
	}
	
	public boolean assetEqual( String value )
	{
		try {
			JStringUtil.assertEqual(key, jc.get(key), "not equal!");
		} catch (JTransException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
}
