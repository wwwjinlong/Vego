package jplat.service.asyntask;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import jplat.tools.config.JConfigManager;
import jplat.tools.config.JSystemConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import z.log.tracelog.XLog;

public class JTaskExecutor
{
	private Logger logger = LogManager.getLogger(JTaskExecutor.class);
	
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
		JSystemConfig sysConfig = JConfigManager.getInstance().getSystemConfig();
		
		int corePoolSize = sysConfig.getInt("asyntask.coretd",100);
		int maxPoolSize = sysConfig.getInt("asyntask.maxtd",1000);
		int maxQueue = sysConfig.getInt("asyntask.maxque",8000);
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
