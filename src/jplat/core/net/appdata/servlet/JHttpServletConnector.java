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
import jplat.tools.config.JLogConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import z.log.tracelog.XLog;

public class JHttpServletConnector implements IAppDataConnector
{
	private static Logger logger = LogManager.getLogger(JHttpServletConnector.class);
	
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
	 * 从servlet中读取数据，不支持chunkedStream模式.
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
		if ( cl == 0 || "GET".equalsIgnoreCase(request.getMethod()) )
		{
			return "".getBytes();
		}
		
		//chunked?
		if ( cl < 0 )
		{
			logger.error(XLog.CONN_MARK+"content-length not found,无法获取报文长度,transfer-coding="+request.getHeader("transfer-coding"));
			throw new JSystemException(KPlatResponseCode.CD_APPCONN_ERROR,KPlatResponseCode.MSG_APPCONN_ERROR+"t=c");
		}

		try
		{
			ByteArrayOutputStream bous = new ByteArrayOutputStream();
			byte[] indata = new byte[3072];	//3k;
			
			BufferedInputStream ins = new BufferedInputStream(request.getInputStream());
			int totalLen = 0;
			while (true)
			{
				// 暂未做超时处理.
				if ( JLogConfig.canPrintNetRead())
				{
					logger.info("SAVAILABLE:"+ins.available());
				}
				
				int len = ins.read(indata);
				//EOF
				if ( len == -1 || totalLen == cl )
				{
					logger.info(String.format(XLog.CONN_MARK+"__FINAL_READ:fl_len=%d,tl_len=%d,cl_len=%d",len,totalLen,cl));
					break;
				}
				
				if ( JLogConfig.canPrintNetRead())
				{
					logger.info(XLog.CONN_MARK+"__ONE_READ:rd_len="+len);
				}

				totalLen += len;
				bous.write(indata, 0, len);
			}

			ins.close();
			if (cl != bous.size())
			{
				logger.error(XLog.CONN_MARK+"__READ_ERROR,contentLength=" + cl+",readlen="+ bous.size());
				return "".getBytes();
			}

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
