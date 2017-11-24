package jplat.tools.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import jplat.tools.stream.JFileUtils;
import z.log.tracelog.JTraceLogUtils;
import z.log.tracelog.KTraceLog;
import z.log.tracelog.XLog;

public class JConfigUtils
{
	public static Properties loadPropertis( String proPath )
	{
		if ( !proPath.endsWith("properties"))
		{
			proPath = proPath+".properties";
		}
		
		XLog.log(JTraceLogUtils.getTraceLog(KTraceLog.ACTION_JINIT, KTraceLog.EVENT_START, "nomark", "load:"+proPath));
		InputStream is = null;
		try
		{
			is = JFileUtils.loadFileStream(proPath);
			
			Properties prop = new Properties();
			prop.load(is);
			
			XLog.log(JTraceLogUtils.getTraceLog(KTraceLog.ACTION_JINIT, KTraceLog.EVENT_SUCCESS, "nomark", "success load:"+proPath));
			return prop;
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			new RuntimeException(e.getMessage());
		}
		finally
		{
			if ( is != null )
			{
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return null;
	}
}
