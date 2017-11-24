package jplat.tools.config;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import jplat.error.exception.JSystemException;
import jplat.tools.string.DateUtil;
import jplat.tools.string.JRandomUtil;
import jplat.tools.string.JStringUtil;
import jplat.tools.string.StringUtil;
import z.log.tracelog.XLog;

/**
 * 父类基础类.该类不应该直接使用和修改.
 * 系统基础信息在JAppconfig中.
 * @author zhangcq
 * @date Jan 5, 2017
 * @comment
 */
public class JSystemConfig extends JBasePropertiesReader
{
	//环境切换配置文件.
	private static String envFile = "classpath:conf/system/AENV";
	private static String envHolder = ":ENV:";
	
	//本机IP地址.
	private String localIpAddr = StringUtil.getLocalIP();
//	private static String localIpAddr = "127.0.0.1";
	
	public String create_time = DateUtil.todayfulldata();
	
	//锁.
	private ReentrantLock randLock = new ReentrantLock();
	private String fixSample = localIpAddr.replace(".", "");
	private long seqno = 0;
	
	public JSystemConfig() throws JSystemException
	{
		init();
	}
	
	private void init() throws JSystemException
	{
		//环境类型
		String envType = getEnvValue(envFile,"environment");
		XLog.loginit("USE ENV_TYPE:"+envType);
		
		//parser config paths.
		String[] confPaths = getProFileArray(envFile,envType);
		
		//load config files.
		loadConfigs(envType,true,confPaths);
		
		if("127001".equals(fixSample) )
		{
			fixSample = JRandomUtil.getRandomSequence(99);
			fixSample = JRandomUtil.randomInt(12);
		}
		
		XLog.loginit("SENQUENCE_PREFIX:%s", fixSample);
	}
	
	/**
	 * 将三种类型的配置统一解析成文件路径.
	 * Nov 23, 20176:06:59 PM
	 * getProFileArray
	 * @param envFile
	 * @param envType
	 * @return
	 */
	private String[] getProFileArray( String envFile, String envType )
	{
		List<String> proList = new ArrayList<String>();
		
		// conf/system/def/XXX.properties.
		String[] confs = getEnvValue(envFile,"defProps").split(",");
		for ( int i = 0; i < confs.length; ++i )
		{
			String confStr = confs[i];
			if ( JStringUtil.isEmpty(confStr) )
			{
				continue;
			}
			
			proList.add(String.format("classpath:conf/system/def/%s.properties",confStr));
		}
		
		// conf/system/ENV/XXX_ENV.properties.
		confs = getEnvValue(envFile,"envProps").split(",");
		for ( int i = 0; i < confs.length; ++i )
		{
			String confStr = confs[i];
			if ( JStringUtil.isEmpty(confStr) )
			{
				continue;
			}
			
			proList.add(String.format("classpath:conf/system/%s/%s_%s.properties", envType,confStr,envType));
		}
		
		// classpath path.
		confs = getEnvValue(envFile,"clpProps").split(",");
		for ( int i = 0; i < confs.length; ++i )
		{
			String confStr = confs[i];
			if ( JStringUtil.isEmpty(confStr) )
			{
				continue;
			}
			
			//env aware still.
			if ( confStr.contains(":ENV:") )
			{
				confStr = confStr.replaceFirst(":ENV:", envType);
				proList.add(String.format("classpath:%s_%s.properties", confStr,envType));
				continue;
			}
			
			proList.add(String.format("classpath:%s.properties", confStr));
		}
		
		//absolute system path.
		confs = getEnvValue(envFile,"sysProps").split(",");
		for ( int i = 0; i < confs.length; ++i )
		{
			String confStr = confs[i];
			if ( JStringUtil.isEmpty(confStr) )
			{
				continue;
			}
			
			proList.add(confStr);
		}
		
		int id = 0;
		for ( Object s1 : proList )
		{
			XLog.log("NAME=[%s]", s1.getClass().getName());
			XLog.log("__CONF_LIST[%d][%s]", ++id,s1 );
		}
		
		return proList.toArray(new String[]{});
	}
	
	/**
	 * 
	 * @author zhangcq
	 * @date Mar 1, 2017
	 * @comment ipLen建议为4.
	 * @param ipLen			1.1.1.1(4) --> 255.255.255.255(12)
	 * @param dateFmt		yyyyMMddHHmmssSSS
	 * @param seqLen
	 * @return
	 */
	public String getUniqNumberStr( int ipLen, String dateFmt, int seqLen )
	{
		int fixLen = fixSample.length() - ipLen;
		
		//IP地址长度.
		StringBuilder sbuffer = new StringBuilder();
		sbuffer.append(new SimpleDateFormat(dateFmt).format(new Date()))
					.append(fixSample.substring( fixLen > 0 ? fixLen : 0 ));
		
		try
		{
			randLock.lock();
			if ( seqno++ < 0 )
			{
				seqno = 775809;
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			throw new RuntimeException("_SEQ_ERR9:"+e.getMessage());
		}
		finally
		{
			randLock.unlock();
		}
		
		String tempStr = String.format("%0"+seqLen+"d", seqno);
		sbuffer.append(tempStr.subSequence(tempStr.length()-seqLen, tempStr.length()));
		
		return sbuffer.toString();
	}
	
	public static void main(String args[])
	{
//		String dateFmt = "yyyyMMddHHmmssSSS";
		String dateFmt = "yyMMddHHmmssSSS";
		
		JSystemConfig config = null;
		try {
			config = new JSystemConfig();
		} catch (JSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for ( int i = 0; i < 2000; ++i )
		{
			XLog.log("--P %s", config.getUniqNumberStr(4,",yyyy/MM/dd HH:mm:ss-SSS,",4));
//			XLog.log("--P %s", JSystemConfig.getInstance().getUniqNumberStr(4,dateFmt,2));
/*			try {
//				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
//			XLog.log("----------------"+config.getString("app.name"));
		}
	}
}
