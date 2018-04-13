package test.redis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import jplat.core.cache.redis.JRedisClusterConnectorImpl;
import jplat.error.exception.JSystemException;
import jplat.error.exception.JTransException;
import jplat.tools.string.JStringUtil;
import jplat.tools.trace.JTimeCounter;
import redis.clients.jedis.JedisCluster;
import z.log.tracelog.JLog;

public class RedisClusterTest {

	public static void testJedisClusterRunsWithMultithreaded() throws InterruptedException,
	ExecutionException, IOException, JSystemException, JTransException
	{
		final JedisCluster jc = JRedisClusterConnectorImpl.getInstance().getCluster();
		jc.set("foo", "bar");

		ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 100, 0, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(8000));
		List<Future<String>> futures = new ArrayList<Future<String>>();
		
		int max = 1000;
		for (int i = 0; i < max; i++)
		{
			jc.set("k"+i, "v"+i);
		}
		
		for (int i = 0; i < max; i++)
		{
			Future<String> future =  executor.submit(new MyRedisCallable("k"+i,jc));
			futures.add(future);
		}

		for ( int i = 0; i < futures.size(); ++i )
		{
			Future<String> future = futures.get(i);
			String value = future.get();
			JStringUtil.assertEqual("v"+i, value,"not equal!");
		}

		jc.close();
	}

	public static void testMemoryOverflow()
	{
		JRedisClusterConnectorImpl cluIns  = JRedisClusterConnectorImpl.getInstance();
		try {
			JedisCluster jc = cluIns.getCluster();
			jc.set("k11", "v11");

			JLog.logerr(jc.get("k11"));

		} catch (JSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//多线程测试
		for ( int i = 0;i < 3000; ++i )
		{
			JLog.logerr("---------------"+i+"-------------------");
			new Thread(
					new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							JRedisClusterConnectorImpl cluIns  = JRedisClusterConnectorImpl.getInstance();
							JedisCluster jc = null;
							try {
								jc = cluIns.getCluster();
							} catch (JSystemException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							for ( int i = 0;i < 300000; ++i )
							{
								JTimeCounter counter = new JTimeCounter();
								String key = "ktd"+Thread.currentThread().getName()+"_id"+i;
								jc.set(key, "1111lsdjflsflsjdfljjjjjjjsssssssssssssssssssssssssssssssv:"+i);
								//									jc.close();
								//									JLog.log(key+"|"+jc.get(key));
								if ( counter.endTick() > 100 )
								{
									JLog.log("MAX100:"+counter.endTick()+":"+key);
								}
							}

							JLog.log("---------END:"+Thread.currentThread().getName());
						}
					}
					).start();
		}

	}
	
	public static void main( String args[] )
	{
		testMemoryOverflow();
	}

}
