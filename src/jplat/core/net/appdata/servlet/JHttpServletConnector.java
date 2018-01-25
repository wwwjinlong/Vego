package jplat.core.net.appdata.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jplat.base.constant.KPlatResponseCode;
import jplat.core.net.appdata.IAppDataConnector;
import jplat.core.trans.JAppContext;
import jplat.core.trans.impl.JServletAppContext;
import jplat.error.exception.JSystemException;
import jplat.tools.config.JAppConfig;
import jplat.tools.config.JLogConfig;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import z.log.tracelog.JTraceLogUtils;
import z.log.tracelog.KTraceLog;
import z.log.tracelog.XLog;

public class JHttpServletConnector implements IAppDataConnector
{
	private static Logger logger = LoggerFactory.getLogger(JHttpServletConnector.class);
	
	//default 5M max.
	private static int MAX_PACKET_LENGTH = JAppConfig.getInt("max_packet_len",5242880);
	
	public String readInputString( JAppContext context,String charset ) throws JSystemException 
	{
		try {
			return new String(readInputBytes(context),charset);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new JSystemException(e,KPlatResponseCode.CD_IO_ERR,KPlatResponseCode.MSG_IO_ERR);
		}
		
		return null;
	}
	
	public byte[] readInputBytes( JAppContext context ) throws JSystemException
	{
		JServletAppContext appCtx = (JServletAppContext)context;
		return readInputBytes__(appCtx.getRequest());
	}
	
	/**
	 * 从servlet中读取数据，容器底层已支持chunkedStream模式.
	 * @author zhangcq
	 * @date Dec 7, 2016
	 * @comment 
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws JSystemException 
	 */
	private byte[] readInputBytes__( HttpServletRequest request ) throws JSystemException
	{
		// String contentType = request.getContentType();
/*		if ( request == null )
		{
			return "".getBytes();
		}*/

		int cl = request.getContentLength();	
		if ( "GET".equalsIgnoreCase(request.getMethod()) )
		{
			return "".getBytes();
		}
		
		try
		{
			ByteArrayOutputStream bous = new ByteArrayOutputStream();
			byte[] indata = new byte[3072];	//3k;
			
			int totalLen = 0;
			BufferedInputStream ins = new BufferedInputStream(request.getInputStream());
			while (true)
			{
				// 暂未做超时处理.
/*				if ( JLogConfig.canPrintNetRead())
				{
					logger.info("SAVAILABLE:"+ins.available());
				}*/
				
				int len = ins.read(indata);
				//EOF
				if ( len == -1 )
				{
					logger.info(String.format(XLog.CONN_MARK+"__FINAL_READ:fl_len=%d,tl_len=%d,cl_len=%d",len,totalLen,cl));
					break;
				}
				
				if ( JLogConfig.canPrintNetRead())
				{
					logger.info(XLog.CONN_MARK+"__ONE_READ:rd_len="+len);
				}

				totalLen += len;
				if ( totalLen > MAX_PACKET_LENGTH )
				{
					logger.error(JTraceLogUtils.getTraceLog(KTraceLog.ACTION_DATACHECK, KTraceLog.EVENT_POINT,
							"", JTraceLogUtils.buildUserData("HTTP_READ","DATA_OVERFLOW_ERR","total="+totalLen,"max="+MAX_PACKET_LENGTH)));
					throw new JSystemException(KPlatResponseCode.CD_APPCONN_ERROR,KPlatResponseCode.MSG_APPCONN_ERROR+"(09)");
				}
				
				bous.write(indata, 0, len);
			}

			ins.close();
			bous.close();
			
			return bous.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		throw new JSystemException(KPlatResponseCode.CD_APPCONN_ERROR,KPlatResponseCode.MSG_APPCONN_ERROR);
	}

	@Override
	public int writeOutputString(JAppContext context, String dataStr, String charset) throws JSystemException {
		// TODO Auto-generated method stub
		try {
			return writeOutputBytes(context,dataStr.getBytes(charset));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new JSystemException(e,KPlatResponseCode.CD_IO_ERR,KPlatResponseCode.MSG_IO_ERR);
		}
		
		return -1;
	}

	@Override
	public int writeOutputBytes(JAppContext context, byte[] retData ) throws JSystemException
	{
		// TODO Auto-generated method stub
		JServletAppContext appCtx = (JServletAppContext)context;
		return writeOutputBytes__(appCtx.getResponse(),retData);
	}
	
	private int writeOutputBytes__( HttpServletResponse response, byte[] retData ) throws JSystemException
	{
		BufferedOutputStream outs = null;
		try
		{
//			response.setContentType("application/json;charset=utf-8");
//			response.setHeader(JAppConnectInfo.H_RET_HEAD, JAppConnectInfo.V_HRETCODE_SUCC);
			response.setContentLength(retData.length);
			
			outs = new BufferedOutputStream( response.getOutputStream());
			outs.write(retData);
			return retData.length;
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new JSystemException(KPlatResponseCode.CD_WRITE_ERR,KPlatResponseCode.MSG_WRITE_ERR);
		}
		finally
		{
			if ( outs != null )
			{
				try {
					outs.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
}
