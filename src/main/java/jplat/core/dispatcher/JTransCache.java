package jplat.core.dispatcher;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import jplat.error.exception.JSystemException;

/**
 * 缓存URL映射信息.
 * @author zhangcq
 *
 */
public class JTransCache
{
	private Logger logger = LoggerFactory.getLogger(JTransCache.class);
	
	//对Map中的对象建立合适的淘汰机制可以减少内存使用.
	public Map<String, JTransInfo> hashMap = new ConcurrentHashMap<String,JTransInfo>();
	
	public JTransInfo getTransInfo( ApplicationContext springCtx, JTransURLInfo urlInfo ) throws JSystemException
	{
		JTransInfo actionInfo = hashMap.get(urlInfo.getMapKey());
		if ( actionInfo == null )
		{
			actionInfo = urlInfo.getActionInfo(springCtx);
			hashMap.put(urlInfo.getMapKey(), actionInfo);
			
			logger.info(String.format("JTransInfo CREATED:%s", urlInfo.getMapKey() ));
		}
		
		return actionInfo;
	}
	
	private static final class Holder
	{
		private static final JTransCache  connector = new JTransCache();
	}

	public static JTransCache getInstance()
	{
		return Holder.connector;
	}
}
