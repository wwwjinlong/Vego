package jplat.tools.config;

public class JLogConfig
{
	//是否打印redis的log.
	public static boolean REDIS_LOG_ON = JConfigManager.getInstance().getSystemConfig().getBoolean("log.redis.on");
			
	//打印网络请求数据有关的日志.
	public static boolean canPrintNetInfo()
	{
		return JConfigManager.getInstance().getSystemConfig().getBoolean("log.net.info");
	}

	//打印网络读取相关日志.
	public static boolean canPrintNetRead()
	{
		return JConfigManager.getInstance().getSystemConfig().getBoolean("log.net.read");
	}
	
	//打印Session相关日志.
	public static boolean canPrintSession()
	{
		return JConfigManager.getInstance().getSystemConfig().getBoolean("log.session");
	}
	
	//打印Session相关日志.
	public static boolean canPrintXMLLog()
	{
		return JConfigManager.getInstance().getSystemConfig().getBoolean("log.xml");
	}
}
