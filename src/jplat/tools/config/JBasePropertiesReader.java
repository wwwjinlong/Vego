package jplat.tools.config;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import jplat.base.constant.KPlatResponseCode;
import jplat.error.exception.JSystemException;
import jplat.tools.string.JStringUtil;
import jplat.tools.string.StringUtil;

import org.apache.commons.lang3.StringUtils;

import z.log.tracelog.XLog;

/**
 * 该类的子类或者工具类应该实现为一个单例或者常量类.
 * 不要反复加载配置.
 * 最好用单例实现,并附加一个reload的操作.
 * @author zhangcq
 * @date Feb 7, 2017
 * @comment
 */
public class JBasePropertiesReader
{
	private Map<String,String> cMap = new ConcurrentHashMap<String,String>();
	
	//初始化.
	protected void loadConfigs( String envType,boolean print, String ...proName  ) throws JSystemException
	{
		if ( StringUtil.isEmpty(envType) )
		{
			XLog.loginit("ERROR:the envType of resource-files cannot be empty.");
			throw new RuntimeException("the envType cannot be empty.");
		}

		for ( String confpath : proName )
		{
			confpath = confpath.trim();
			

			Properties props = JConfigUtils.loadPropertis(confpath);
			XLog.loginit("__LOAD PROPERTY SUCCESS["+confpath+"]");

			Iterator<Entry<Object, Object>> it = props.entrySet().iterator();  
			while (it.hasNext())
			{  
				Entry<Object, Object> entry = it.next();  
				String key = (String)entry.getKey();  
				String value = (String)entry.getValue();

				if( print )
				{
					XLog.loginit("property-value:[ %s = %s ]", key,value );
				}

				if ( cMap.get(key) != null )
				{
					XLog.loginit("ERROR[%s]", "duplicate config key:"+key);
					throw new JSystemException(KPlatResponseCode.CD_CONF_ERROR,KPlatResponseCode.MSG_CONF_ERROR);
				}
				
				cMap.put(key, value );
			}
		}
	}

	public String getString(String key, boolean must )
	{
//		XLog.log("find property-value for key:"+key);
		String value = cMap.get(key);
		if ( must && StringUtils.isEmpty(value) )
		{
			throw new RuntimeException( "WARN,key-property [" + key + "] not found" );
		}

		return value;
	}

	public String getString(String key )
	{
		return getString( key, false );
	}

	public String getString(String key, String defval )
	{
		String retVal = getString(key,false);
		if ( StringUtil.isEmpty(retVal) )
		{
			return defval;
		}

		return retVal;
	}

	public int getInt(String key)
	{
		String cf = getString(key,false);
		if ( StringUtil.isEmpty(cf) )
		{
			return 0;
		}

		return Integer.parseInt(cf);
	}

	public int getInt(String key, boolean must )
	{
		return Integer.parseInt(getString(key,must));
	}

	public int getInt(String key,int defval )
	{
		int retval = getInt(key);
		if (retval==0)
		{
			return defval;
		}

		return retval;
	}

	public boolean getBoolean(String key)
	{
		String value = getString(key,false);
		if ( value != null )
		{
			value = value.trim();
		}

		return "true".equalsIgnoreCase(value);
	}
	
	/**
	 * 获取环境类型 用于加载不同文件.
	 * @author zhangcq
	 * @date Feb 7, 2017
	 * @comment 
	 * @return
	 * @throws JSystemException 
	 */
	protected static String getEnvValue( String envFile,String key)
	{
		try
		{
			Properties props = JConfigUtils.loadPropertis(envFile);
			String envValue = props.getProperty(key);
					
			if ( JStringUtil.isEmpty(envValue) )
			{
				return "";
			}
			
			return envValue;
		}
		catch (MissingResourceException e)
		{
			XLog.loginit("ERROR:fail to tell the environment type");
			throw e;
		}
	}
}