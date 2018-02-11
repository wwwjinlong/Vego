package jplat.tools.stream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jplat.base.constant.KPlatResponseCode;
import jplat.error.exception.JSystemException;
import jplat.tools.config.JLogConfig;
import z.log.tracelog.JTraceLogUtils;
import z.log.tracelog.KTraceLog;
import z.log.tracelog.XLog;

public class JServletStreamUtils
{
	private static Logger logger = LoggerFactory.getLogger(JServletStreamUtils.class);
	
	public static String readInputString( HttpServletRequest request, String charset, int maxBts ) throws JSystemException
	{
		try {
			return new String ( readInputStream( request, maxBts ), charset);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new JSystemException( KPlatResponseCode.CD_IO_ERR,KPlatResponseCode.MSG_IO_ERR);
		}
	}
	
	/**
	 * 读取文件.
	 * @author zhangcq
	 * @date Dec 26, 2016
	 * @comment chunked supported by underlying implementation.
	 * @param request
	 * @return
	 * @throws JSystemException 
	 */
	public static byte[] readInputStream( HttpServletRequest request, int maxBts ) throws JSystemException
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
				if ( totalLen >  maxBts )
				{
					logger.error(JTraceLogUtils.getTraceLog(KTraceLog.ACTION_DATACHECK, KTraceLog.EVENT_POINT,
							"", JTraceLogUtils.buildUserData("HTTP_READ","DATA_OVERFLOW_ERR","total="+totalLen,"max="+maxBts )));
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
	
	/**
	 * 向Http响应写入请求数据.
	 * @author zhangcq
	 * @date Jan 10, 2017
	 * @comment 
	 * @param response
	 * @throws JSystemException 
	 */
	public static void writeHttpResponse( HttpServletResponse response, byte[] data, String contentType ) throws JSystemException
	{
		BufferedOutputStream outs = null;
		try
		{
			//先设置.
			response.setContentLength(data.length);
			response.setContentType(contentType);
			//			response.setContentType("text/plain;charset:utf-8");
			//			response.setHeader(JAppConnectInfo.H_RET_HEAD, JAppConnectInfo.V_HRETCODE_SUCC);		//返回成功.

			//后传数据.
			outs = new BufferedOutputStream( response.getOutputStream());
			outs.write(data);
			return;
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
