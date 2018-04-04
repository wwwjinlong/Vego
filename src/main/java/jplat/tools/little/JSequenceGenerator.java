package jplat.tools.little;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

import jplat.error.exception.JSystemException;
import jplat.tools.string.JRandomUtil;
import jplat.tools.string.StringUtil;
import z.log.tracelog.JLog;

/**
 * 序号发生器.
 * @author zhangcq
 *
 */
public class JSequenceGenerator
{
	//本机IP地址.
	private String localIpAddr = StringUtil.getLocalIP();
	
	//锁.
	private ReentrantLock randLock = new ReentrantLock();
	private String fixSample = localIpAddr.replace(".", "");
	private long seqno = 0;
	
	private JSequenceGenerator()
	{
		try {
			init();
		} catch (JSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static final class Holer
	{
		private static final JSequenceGenerator gentor = new JSequenceGenerator();
	}
	
	public static JSequenceGenerator getInstance()
	{
		return Holer.gentor;
	}
	
	private void init() throws JSystemException
	{
		if("127001".equals(fixSample) )
		{
			fixSample = JRandomUtil.getRandomSequence(99);
			fixSample = JRandomUtil.randomInt(12);
		}
		
		JLog.loginit("SENQUENCE_PREFIX:%s", fixSample);
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
		
		long tmpSeq = 0;
		try
		{
			randLock.lock();
			if ( seqno++ < 0 )
			{
				seqno = 775809;
			}
			
			tmpSeq = seqno;
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
		
		String tempStr = String.format("%0"+seqLen+"d", tmpSeq);
		sbuffer.append(tempStr.subSequence(tempStr.length()-seqLen, tempStr.length()));
		
		return sbuffer.toString();
	}
}
