package jplat.tools.little;

import z.log.tracelog.JLog;

public class JDoubleUtils
{
	public static boolean isEqual( double d1, double d2 )
	{
		if ( d1 - d2 < 0.0001 )
		{
			return true;
		}
		
		return false;
	}
	
	public static String format( double d1, int precision )
	{
		return String.format("%."+precision+"f", d1);
	}
	
	public static void main( String args[] )
	{
		double rate = 0.0035;
		JLog.log(format(rate*100*360,4));
		
		if ( isEqual((rate/100)*100,rate ) )
		{
			JLog.log("isEqual ---------true");
		}
		else
		{
			JLog.log("isEqual ---------false");
		}
		
		if ( (rate/100)*100 == rate )
		{
			JLog.log("== ---------true");
		}
		else
		{
			JLog.log("== ---------false");
		}
	}
}
