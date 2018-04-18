package jplat.core.net.appdata.check;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jplat.base.cache.redis.JRedisConnectorImpl;
import jplat.base.constant.KPlatResponseCode;
import jplat.core.net.appdata.IDataChecker;
import jplat.core.trans.JAppContext;
import jplat.error.exception.JSystemException;
import jplat.tools.config.JAppConfig;
import jplat.tools.string.JDateUtil;
import jplat.tools.string.JRandomUtil;
import jplat.tools.string.StringUtil;
import z.log.tracelog.JTraceLogUtils;
import z.log.tracelog.KTraceLog;

/**
 * 防重检测是用于特定报文请求进行防重放控制.
 * 出于效率原因，只有在特定的交易中才进行调用.
 * @author zhangcq
 * @date Feb 13, 2017
 * @comment
 */
public class JDataChecker implements IDataChecker
{
	private Logger logger = LoggerFactory.getLogger(JDataChecker.class);
	
	private static String keyPrefix = "sys:dfD_";
	
	@Override
	public boolean checkRepeat( JAppContext appCtx, String funCode,	String userData ) throws JSystemException
	{
		//未开启则直接通过.
		if ( !openCheck(appCtx,funCode) )
		{
			return true;
		}
		
		String mark = JRandomUtil.getRandomSequence(KTraceLog.MARK_LENGTH);
		logger.info(JTraceLogUtils.getStartTraceLog( KTraceLog.ACTION_DATACHECK,mark,JTraceLogUtils.buildUserData(funCode,userData) ));
		
		//未传则直接通过.
		String rokcet = appCtx.getReqHead().getRocket();
		if ( StringUtil.isEmpty(rokcet) )
		{
			logger.info(JTraceLogUtils.getTraceLog( KTraceLog.ACTION_DATACHECK,KTraceLog.EVENT_FAIL,mark, "defend:nullKey" ));
			throw new JSystemException(KPlatResponseCode.CD_REQ_DUPLICATE+"0",KPlatResponseCode.MSG_REQ_DUPLICATE );
		}
		
		//记录请求防止下次重复.
		int secs = (int)JDateUtil.countSecondsFuture(new Date(), 1, "00:30:00");
		String defKey = getDefendKey(funCode,"");
		
		long ret = JRedisConnectorImpl.getInstance().sadd( defKey, secs, rokcet);
		if( ret != 1 )
		{
			logger.info(JTraceLogUtils.getTraceLog( KTraceLog.ACTION_DATACHECK,KTraceLog.EVENT_FAIL,mark,JTraceLogUtils.buildUserData("REPEAT",funCode,userData,rokcet) ));
			throw new JSystemException(KPlatResponseCode.CD_REQ_DUPLICATE+"1",KPlatResponseCode.MSG_REQ_DUPLICATE);
		}
		
		logger.info(JTraceLogUtils.getTraceLog( KTraceLog.ACTION_DATACHECK,KTraceLog.EVENT_SUCCESS,mark,"defend:pass" ));
		
		return true;
	}
	
	@Override
	public boolean openCheck( JAppContext appCtx, String funCode ) {
		// TODO Auto-generated method stub
		return JAppConfig.getConfigCache().SAFE_REPEAT;
	}

	/**
	 * 如果有个系统在改键超时之后再加入集合则该集合可能不会有超时.
	 * 产生的原因是因为该系统时间不准太过延后.
	 * Oct 25, 20173:02:47 PM
	 * getDefendKey
	 * @param funcCode
	 * @param dateFmt
	 * @return
	 */
	public String getDefendKey( String funcCode, String dateFmt )
	{
		String defKey = new StringBuilder()
		.append(keyPrefix)
		.append(funcCode)
		.append(JDateUtil.todayStr("_yyMMdd")).toString();

		return defKey;
	}
}
