package jplat.tools.config;


import a.autocode.utils.QLogUtils;
import jplat.base.constant.KPlatResponseCode;
import jplat.error.exception.JSystemException;
import z.log.tracelog.JLog;

/**
 * 配置管理类.可以动态重载参数.
 * 动态载入参数和程序运行效率是需要权衡的，一旦可以动态则需要每次都从参数文件读取.
 * @author zhangcq
 * @date Jan 5, 2017
 * @comment
 */
public class JConfigManager
{
	private JSystemConfigLoader configEntity;
	
	private JSystemConfigCache configCache;
			
	private JConfigManager()
	{
	}
	
	private static final class Holer
	{
		private static final JConfigManager config = new JConfigManager();
	}
	
	public static JConfigManager getInstance()
	{
		return Holer.config;
	}

	public JConfigManager load() throws JSystemException
	{
		try
		{
			configEntity = new JSystemConfigLoader();
			configCache = new JSystemConfigCache();
			return this;
		} catch ( JSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}
	
	//获取配置加载类
	public JSystemConfigLoader getSystemConfigLoader()
	{
		return configEntity;
	}
	
	//获取配置常用类.
	public JSystemConfigCache getSystemConfigCache()
	{
		return configCache;
	}
	
	public static void main(String args[])
	{
		loadTest();
	}
	
	/********** test code *************/
	private static void loadTest()
	{
		JSystemConfigLoader config = JConfigManager.getInstance().getSystemConfigLoader();
		
		JLog.log("default config[%s]", config.getString("mdp.health"));
		JLog.log("sys_env config[%s]", config.getString("app.test"));
		JLog.log("classpath config[%s]", config.getString("keyPassword"));
		JLog.log("system absolute config[%s]", config.getString("cache.clear.runtime"));
		
	}
	private static void reloadTest()
	{
		for ( int i = 0; i < 1000; ++i )
		{
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				JConfigManager.getInstance().load();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JLog.log("--------------------"+JConfigManager.getInstance().getSystemConfigLoader().getString("app.name"));
		}
	}
}
