package jplat.tools.coder.xml;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

public class JAxbCache
{
	public static Map<String,JAXBContext> cacheMap = new ConcurrentHashMap<String,JAXBContext>();
	
	public static JAXBContext findContext( String name, Class<?> clazz ) throws JAXBException
	{
		JAXBContext retCtx = cacheMap.get(name);
		if( retCtx != null )
		{
			return retCtx;
		}
		
		JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
		
		cacheMap.put(name, jaxbContext);
		
		return jaxbContext;
	}
}
