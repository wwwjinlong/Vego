package jplat.tools.config;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

import z.log.tracelog.XLog;
import jplat.error.exception.JSystemException;
import jplat.tools.string.JRandomUtil;
import jplat.tools.string.StringUtil;

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
		} catch (JSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return false;
		}
		
		if ( cnf != null )
		{
			configEntity = cnf;
			return true;
		}
		
		return false;
	}
	
	public JSystemConfig getSystemConfig()
	{
		return configEntity;
	}
	
	public static void main(String args[])
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
