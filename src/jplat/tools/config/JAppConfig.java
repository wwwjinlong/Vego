package jplat.tools.config;

import java.io.File;

import z.log.tracelog.XLog;
import jplat.core.session.JSession;
import jplat.tools.string.StringUtil;

/**
 * 不会变或者高频访问的建议改成常量.
 * 那些可能会动态改变的 还是用方法调用.不然需要重启才会生效.
 * @author zhangcq
 * @date Jan 5, 2017
 * @comment
 */
public class JAppConfig
{
	public static JConfigManager confManager = JConfigManager.getInstance();
	
	//报文字符集.
	public static String PACK_CHARSET = confManager.getSystemConfig().getString("packet.charset","utf-8");
	
	//是否开启防重检查.
	public static boolean CHK_REPEAT = confManager.getSystemConfig().getBoolean("safe.repeat");
	
	//session类型. 200-HTTP 300-REDIS.
	public static int SESSION_TYPE = confManager.getSystemConfig().getInt("session.type",JSession.SESS_HTTP);

	//是否是测试环境.
	public static boolean IS_TEST = confManager.getSystemConfig().getBoolean("app.test");

	//app名字.
	public static String APP_NAME = confManager.getSystemConfig().getString("app.name");
	
	//用于校验数据的key值.
	public static String DATA_SIGN_KEY = confManager.getSystemConfig().getString("sign.key");
	
	//异常日志栈打印行数
	public static int LOG_TRACE_CNT = confManager.getSystemConfig().getInt("log.trace.cnt",0);

	//异步任务初始并发数目.
	public static int ASYN_POOL_CORE_SIZE = confManager.getSystemConfig().getInt("asyntask.coretd",10);
	
	//异步最大队列.
	public static int ASYN_POOL_MAX_QUEUE = confManager.getSystemConfig().getInt("asyntask.maxque",8000);
	
	//临时目录.
	private static String TEMP_HOME = getTempHome();
	
	//获取临时文件夹.
	public static String getTempDir( String seq )
	{
		return TEMP_HOME+seq+File.separator;
	}
	
	/** 通用配置类 ***/
	public static String getString( String key )
	{
		return confManager.getSystemConfig().getString(key);
	}
	
	public static String getString( String key, String def )
	{
		return confManager.getSystemConfig().getString(key,def);
	}
	
	public static int getInt( String key, int def )
	{
		return confManager.getSystemConfig().getInt(key,def);
	}
	
	public static int getInt( String key )
	{
		return confManager.getSystemConfig().getInt(key);
	}
	
	//初始化临时目录.
	private static String getTempHome()
	{
		String home = System.getProperty("user.home");
		String appName = "__"+StringUtil.getDefString(APP_NAME,"noname");

		return String.format("%s%s%s%s%s", home,File.separator,appName,File.separator,"temp_");
	}

	public static void main(String args[])
	{
		XLog.log(getTempDir("1"));
	}
}
