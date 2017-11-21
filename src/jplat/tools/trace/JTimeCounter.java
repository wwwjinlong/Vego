package jplat.tools.trace;

import java.util.Date;

public class JTimeCounter
{
	private Date startDate, endDate;
	
	public JTimeCounter()
	{
		startDate = new Date();
	}
	
	public void startTick()
	{
		startDate = new Date();
	}
	
	public long endTick()
	{
		endDate = new Date();
		return ( endDate.getTime() - startDate.getTime() );
	}
}
