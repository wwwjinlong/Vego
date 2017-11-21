package z.log.tracelog;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jplat.core.session.JSessionUtils;
import jplat.tools.string.DateUtil;
import jplat.tools.string.JRandomUtil;
import jplat.tools.string.StringUtil;

public class JTraceLogUtils
{
	private static Logger logger = LogManager.getLogger(JSessionUtils.class);
	
	/**
	 * 日志起始标记.
	 * @author zhangcq
	 * @date Dec 28, 2016
	 * @comment 
	 * @param logAction
	 * @param traceMark
	 * @param userData
	 * @return
	 */
	public static String getStartTraceLog( String logAction, String traceMark, String userData )
	{
		return getTraceLog(logAction,KTraceLog.EVENT_START,traceMark,userData);
	}
	
	/**
	 * 获取标记日志.
	 * @author zhangcq
	 * @date Dec 28, 2016
	 * @comment 
	 * @param logAction
	 * @param traceMark
	 * @param userData
	 * @return
	 */
	public static String getEndTraceLog( String logAction, String traceMark, String userData )
	{
		return getTraceLog(logAction,KTraceLog.EVENT_END,traceMark,userData);
	}

	/**
	 * 日志结束标记.
	 * @author zhangcq
	 * @date Dec 28, 2016
	 * @comment 
	 * @param logAction
	 * @param traceMark
	 * @param userData		用户自定义数据.
	 * @return
	 */
	public static String getTraceLog( String logAction, String event, String traceMark, String userData )
	{
		StringBuffer sbuffer = new StringBuffer();
		
		sbuffer.append(KTraceLog.GRABY_FLAG)
		.append(KTraceLog.ACTION).append(logAction);
		
		if ( isNotEmpty(event) )
		{
			sbuffer.append(KTraceLog.CONNMARK).append(KTraceLog.EVENT).append(event);
		}
		
		sbuffer.append(KTraceLog.CONNMARK).append(KTraceLog.MARK).append(traceMark)
		.append(KTraceLog.CONNMARK).append(KTraceLog.TIME).append(DateUtil.todayStr(DateUtil.FMT_READ_ALL));
		
		if ( isNotEmpty(userData) )
		{
			sbuffer.append(KTraceLog.CONNMARK);
			sbuffer.append(KTraceLog.USER_DATA+userData).append(KTraceLog.USER_DATA_END);
		}
		
		return sbuffer.toString();
	}
	
	/**
	 * zhangcq
	 * 2016-03-15 
	 * @param ex
	 * @return 获取代码级别的错误记录,对于反射这类调用无效.
	 */
	public static String getExceptionLog( Throwable ex, int length, boolean needLine )
	{
		if ( ex == null )
		{
			return "系统异常.";
		}
		
		StackTraceElement[] stackTraceElements = ex.getStackTrace();
		StringBuilder exLogStr = new StringBuilder();
		if( stackTraceElements != null )
		{
			for(int i =0;i<stackTraceElements.length-length;i++)
			{
				exLogStr.append( stackTraceElements[i].getMethodName()).append(" ");
				exLogStr.append( stackTraceElements[i].getFileName());
				exLogStr.append(":"+stackTraceElements[i].getLineNumber());
				if (needLine)
				{
					exLogStr.append("\n");
				}
				else
				{
					exLogStr.append("|");
				}
			}
		}
		
		return  ex.toString()+ex.getMessage()+" REVOKE_STACK:"+exLogStr.toString();
	}
	
	public static String getExceptionFullLog( Throwable ex, int length, boolean needLine )
	{
		if ( ex == null )
		{
			return "系统异常.";
		}
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(bos);
		ex.printStackTrace(ps);
		
		StringBuilder sbuilder = new StringBuilder();
//		sbuilder.append(ex.toString()).append("\nREVOKE_STACK:").append(new String(bos.toByteArray()));
		sbuilder.append("CALL_STACK:").append(new String(bos.toByteArray()));
		ps.close();
		
		return sbuilder.toString();
	}

	public static String buildUserData( String ...args )
	{
		StringBuilder sb = new StringBuilder();
		String connMark = "|";
		for ( int i = 0; i < args.length; ++i )
		{
			
			if ( i == args.length -1 )
			{
				connMark = "";
			}
			
			sb.append(args[i]).append(connMark);
		}
		
		return sb.toString();
	}
	
	public static String getLogMark()
	{
		return JRandomUtil.getStrongRandomSequence(KTraceLog.MARK_LENGTH);
	}
	
	/********************
	 * 					自包含工具类
	 								*********************/
	private static boolean isEmpty(String value)
	{
		return null == value || "".equals(value.trim()) || "null".equalsIgnoreCase(value.trim());
	}
	
	private static boolean isNotEmpty(String value)
	{
		return !isEmpty(value);
	}
	
	public static void main( String args[])
	{
		String mark = JRandomUtil.getRandomSequence(KTraceLog.MARK_LENGTH);
		XLog.log(getTraceLog(KTraceLog.ACTION_CALL_ICOP,KTraceLog.EVENT_SUCCESS,mark,"1234567|1234567890123456|交易成功......"));
		
		XLog.log(buildUserData("A","B","C333"));
		
/*		try
		{
			new JTraceLogUtils().test();
		}
		catch ( Exception e )
		{
			XLog.log("%s",getExceptionLog(e,0,true));
			XLog.log("-------------");
			XLog.log("%s",getExceptionFullLog(e,0,true));
		}*/
		
		try {
			Class<JTraceLogUtils> clazz = JTraceLogUtils.class;
			JTraceLogUtils obj = clazz.newInstance();
			Method mtd = clazz.getMethod("test", null);
			mtd.invoke(obj, null);
			
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			XLog.log("%s",getExceptionLog(e,0,true));
			XLog.log("-------------");
			XLog.log("%s",getExceptionFullLog(e,0,true));
		}
	}
	
	public  void test() throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException
	{
		Class<JTraceLogUtils> clazz = JTraceLogUtils.class;
		JTraceLogUtils obj = clazz.newInstance();
		Method mtd = clazz.getMethod("makeEx", null);
		mtd.invoke(obj, null);
	}
	
	public void makeEx()
	{
		String ss = null;
		if ( ss.endsWith(""))
		{
			
		}
	}
}
