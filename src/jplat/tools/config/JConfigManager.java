package jplat.tools.config;


import jplat.error.exception.JSystemException;
import z.log.tracelog.XLog;

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
		init();
	}
	
	private static final class Holer
	{
		private static final JConfigManager config = new JConfigManager();
	}
	
	public static JConfigManager getInstance()
	{
		return Holer.config;
	}
	
	private void init()
	{
		try {
			configEntity = new JSystemConfigLoader();
		} catch (JSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean reload()
	{
		JSystemConfigLoader cnf = null;
		try {
			cnf = new JSystemConfigLoader();
			if ( cnf != null )
			{
				configEntity = cnf;
				configCache = new JSystemConfigCache();
				return true;
			}
			
		} catch (JSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
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
		
		XLog.log("default config[%s]", config.getString("mdp.health"));
		XLog.log("sys_env config[%s]", config.getString("app.test"));
		XLog.log("classpath config[%s]", config.getString("keyPassword"));
		XLog.log("system absolute config[%s]", config.getString("cache.clear.runtime"));
		
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
			
			JConfigManager.getInstance().reload();
			XLog.log("--------------------"+JConfigManager.getInstance().getSystemConfigLoader().getString("app.name"));
		}
	}
}
