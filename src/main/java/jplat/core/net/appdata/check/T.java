package jplat.core.net.appdata.check;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import z.log.tracelog.JLog;
import jplat.base.cache.redis.JRedisConnectorImpl;
import jplat.tools.string.JDateUtil;

public class T {
	public static void main( String atgs[] )
	{

		if( true )
		{
			//计算后一天.
			Date nextDate = JDateUtil.beforeDate(new Date(), -1);
			try
			{
				SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd HH:mm:ss");
				nextDate = formatter.parse(JDateUtil.format(nextDate, "yyMMdd")+" 00:30:00");
			}
			catch (ParseException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException( e.getMessage() );
			}
			
			int seconds = (int)((nextDate.getTime()- (new Date().getTime()))/1000);
			JLog.log("seconds=%s", seconds+"");
		}
	
	}

}
