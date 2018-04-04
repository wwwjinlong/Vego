package jplat.tools.string;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JDateUtil extends DateUtil
{
	/**
	 * 计算起始日期之后的几天几时几分几秒的秒数.
	 * Oct 25, 20173:13:46 PM
	 * countSeconds
	 * @param startDate
	 * @param days
	 * @param timePart
	 * @return
	 */
	public static long countSecondsFuture( Date startDate, int days, String timeStr )
	{
		//计算后一天.
		Date nextDate = JDateUtil.beforeDate(startDate, -days);
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
			nextDate = formatter.parse(JDateUtil.format(nextDate, "yyyyMMdd " )+timeStr);
		}
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException( e.getMessage() );
		}

		return ((nextDate.getTime() - (new Date().getTime()))/1000);
	}
	
/*	public static void main( String args[] )
	{
		XLog.log(""+countSecondsFuture( new Date(), 1, "15:33:00" ));
	}*/
}
