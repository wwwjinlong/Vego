package jplat.tools.config;


import jplat.error.exception.JSystemException;
import z.log.tracelog.XLog;

/**
 * 配置管理类.
 * @author zhangcq
 * @date Jan 5, 2017
 * @comment
 */
public class JConfigManager
{
	private JSystemConfig configEntity;
	
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
			configEntity = new JSystemConfig();
		} catch (JSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean reload()
	{
		JSystemConfig cnf = null;
		try {
			cnf = new JSystemConfig();
			if ( cnf != null )
			{
				configEntity = cnf;
				return true;
			}
			
		} catch (JSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public JSystemConfig getSystemConfig()
	{
		return configEntity;
	}
	
	public static void main(String args[])
	{
		loadTest();
	}
	
	/********** test code *************/
	private static void loadTest()
	{
		JSystemConfig config = JConfigManager.getInstance().getSystemConfig();
		
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
			XLog.log("--------------------"+JConfigManager.getInstance().getSystemConfig().getString("app.name"));
		}
	}
}
