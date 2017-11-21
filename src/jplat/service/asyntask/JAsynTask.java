package jplat.service.asyntask;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class JAsynTask<T> implements Runnable
{
	private ArrayBlockingQueue<T> block = new ArrayBlockingQueue<T>(1);
	
	protected void put( T e ) throws InterruptedException
	{
		block.put(e);
	}
	
	//return null fi time is out.
	public T poll( int seconds ) throws InterruptedException
	{
		return block.poll( seconds, TimeUnit.SECONDS );
	}
	
	//return null fi time is out.
	public T pollMillis( int millsseconds ) throws InterruptedException
	{
		return block.poll(millsseconds, TimeUnit.MILLISECONDS );
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}
