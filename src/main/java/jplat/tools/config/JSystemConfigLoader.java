package jplat.tools.config;

import java.util.ArrayList;
import java.util.List;

import jplat.error.exception.JSystemException;
import jplat.tools.string.DateUtil;
import jplat.tools.string.JStringUtil;
import z.log.tracelog.JLog;

/**
 * 父类基础类.该类不应该直接使用和修改.
 * 系统基础信息在JAppconfig中.
 * @author zhangcq
 * @date Jan 5, 2017
 * @comment
 */
public class JSystemConfigLoader extends JBasePropertiesReader
{
	//环境切换配置文件.
	private static String envFile = "classpath:conf/system/AENV";
	private static String envHolder = ":ENV:";
	
	public String create_time = DateUtil.todayfulldata();
	
	public JSystemConfigLoader() throws JSystemException
	{
		init();
	}
	
	private void init() throws JSystemException
	{
		//环境类型
		String envType = getEnvValue(envFile,"environment");
		JLog.loginit("USE ENV_TYPE:"+envType);
		
		//parser config paths.
		String[] confPaths = getProFileArray(envFile,envType);
		
		//load config files.
		loadConfigs(envType,true,confPaths);
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
			if ( confStr.contains(envHolder) )
			{
				confStr = confStr.replaceFirst(envHolder, envType);
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
			JLog.log("NAME=[%s]", s1.getClass().getName());
			JLog.log("__CONF_LIST[%d][%s]", ++id,s1 );
		}
		
		return proList.toArray(new String[]{});
	}
	
	public static void main(String args[])
	{
//		String dateFmt = "yyyyMMddHHmmssSSS";
		String dateFmt = "yyMMddHHmmssSSS";
		
		JSystemConfigLoader config = null;
		try {
			config = new JSystemConfigLoader();
		} catch (JSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
