package jplat.service.asyntask;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jplat.tools.config.JAppConfig;
import z.log.tracelog.XLog;

public class JTaskExecutor
{
	private Logger logger = LoggerFactory.getLogger(JTaskExecutor.class);
	
	private ThreadPoolExecutor fixedThreadPool;
	
	//异步阻塞队列.
	private ArrayBlockingQueue blockQueue;
	
	public JTaskExecutor()
	{
		init();
	}
	
	/**
	 * 初始化.
	 * @author zhangcq
	 * @date Jun 6, 2017
	 * @comment
	 */
	private void init()
	{
		int corePoolSize = JAppConfig.getConfigCache().ASYN_CORE_POOL_SIZE;
		int maxPoolSize =JAppConfig.getConfigCache().ASYN_MAX_POOL_SIZE;
		int maxQueue = JAppConfig.getConfigCache().ASYN_MAX_QUEUE_SIZE;
		
		blockQueue = new ArrayBlockingQueue(maxQueue);
		
		XLog.loginit(String.format("__JTaskExecutor:asyntask.coretd=%d,asyntask.maxtd=%d,asyntask.maxque=%d",corePoolSize,maxPoolSize,maxQueue));
		fixedThreadPool = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 20, TimeUnit.SECONDS, blockQueue );
		
		fixedThreadPool.allowCoreThreadTimeOut(true);
	}
	/**
	 * 单例.
	 * @author zhangcq
	 * @date Jun 6, 2017
	 * @comment
	 */
	private static final class Holder
	{
		private static JTaskExecutor instance = new JTaskExecutor();
	}
	
	public static final JTaskExecutor getInstance()
	{
		return Holder.instance;
	}
	
	/**
	 * 获取当前提任务数量.
	 * @author zhangcq
	 * @date Jun 6, 2017
	 * @comment 
	 * @return
	 */
	public int getTaskCnt()
	{
		return blockQueue.size();
	}
	
	/**
	 * 提交任务.
	 * @author zhangcq
	 * @date Jun 6, 2017
	 * @comment 
	 * @param task
	 */
	public void executeTask( Runnable task )
	{
		logger.info("startup exec task,hashCode="+task.hashCode());
		fixedThreadPool.execute(task);
		logger.info("end exec task.hashCode="+task.hashCode());
	}
	
	public void submitTask( Runnable task )
	{
		logger.info("startup submit task,hashCode="+task.hashCode());
		fixedThreadPool.submit(task);
		logger.info("end submit task.hashCode="+task.hashCode());
	}
}
