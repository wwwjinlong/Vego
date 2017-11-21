package jplat.core.dispatcher;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jplat.error.exception.JSystemException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;

/**
 * 缓存URL映射信息.
 * @author zhangcq
 *
 */
public class JTransCache
{
	private Logger logger = LogManager.getLogger(JTransCache.class);
	
	public Map<String, JTransInfo> hashMap = new ConcurrentHashMap<String,JTransInfo>();
	
	public JTransInfo getTransInfo( ApplicationContext springCtx, JTransURLInfo urlInfo ) throws JSystemException
	{
		JTransInfo actionInfo = hashMap.get(urlInfo.getMapKey());
		if ( actionInfo == null )
		{
			actionInfo = urlInfo.getActionInfo(springCtx);
			hashMap.put(urlInfo.getMapKey(), actionInfo);
			
			logger.info(String.format("CELL_CREATED:%s", urlInfo.getMapKey() ));
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
