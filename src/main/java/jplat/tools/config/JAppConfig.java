package jplat.tools.config;

import java.io.File;

import jplat.tools.string.StringUtil;
import z.log.tracelog.JLog;

/**
 * 不会变或者高频访问的建议改成常量.
 * 那些可能会动态改变的 还是用方法调用.不然需要重启才会生效.
 * @author zhangcq
 * @date Jan 5, 2017
 * @comment
 */
public class JAppConfig
{
	//管理类单例，可以重载参数.
	//static静态元素按照声明顺序执行.
	public static JConfigManager confManager;
	
	static
	{
		confManager = JConfigManager.getInstance();
		try {
			confManager.load();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//临时目录.
	private static String TEMP_HOME = getTempHome();
	
	//获取临时文件夹.
	public static String getTempDir( String seq )
	{
		return TEMP_HOME+seq+File.separator;
	}
	
	/***
	 * 获取字符串
	 * 2018年2月24日下午5:06:39
	 * getString
	 * @param key
	 * @return
	 */
	public static String getString( String key )
	{
		return confManager.getSystemConfigLoader().getString(key);
	}
	
	public static String getString( String key, String def )
	{
		return confManager.getSystemConfigLoader().getString(key,def);
	}
	
	public static String getString( String key, boolean must )
	{
		return confManager.getSystemConfigLoader().getString(key,must);
	}
	
	/**
	 * 获取整数.
	 * 2018年2月24日下午5:06:47
	 * getInt
	 * @param key
	 * @return
	 */
	public static int getInt( String key )
	{
		return confManager.getSystemConfigLoader().getInt(key);
	}
	
	public static int getInt( String key, int def )
	{
		return confManager.getSystemConfigLoader().getInt(key,def);
	}
	
	public static int getInt( String key, boolean must )
	{
		return confManager.getSystemConfigLoader().getInt(key,must);
	}
	
	/**
	 * 获取布尔值.
	 * 2018年2月24日下午5:07:00
	 * getBoolean
	 * @param key
	 * @return
	 */
	public static boolean getBoolean( String key )
	{
		return confManager.getSystemConfigLoader().getBoolean(key);
	}
	
	public static JSystemConfigCache getConfigCache()
	{
		return JConfigManager.getInstance().getSystemConfigCache();
	}
	
	//初始化临时目录.
	private static String getTempHome()
	{
		String home = System.getProperty("user.home");
		
		String appName = "__"+StringUtil.getDefString( getConfigCache().APP_NAME,"noname");

		return String.format("%s%s%s%s%s", home,File.separator,appName,File.separator,"temp_");
	}

	public static void main(String args[])
	{
		JLog.log(getTempDir("1"));
	}
}
