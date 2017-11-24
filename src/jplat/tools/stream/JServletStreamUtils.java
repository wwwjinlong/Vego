package jplat.tools.stream;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import jplat.base.constant.KPlatResponseCode;
import jplat.error.exception.JSystemException;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import z.log.tracelog.XLog;

public class JServletStreamUtils
{
	private static Logger logger = LogManager.getLogger(JServletStreamUtils.class);
	
	/**
	 * 读取文件.
	 * @author zhangcq
	 * @date Dec 26, 2016
	 * @comment 
	 * @param request
	 * @return
	 * @throws JSystemException 
	 */
	public static byte[] readInputStream( HttpServletRequest request ) throws JSystemException
	{
		int cl = request.getContentLength();		

		if ( cl == 0 )
		{
			return "".getBytes();
		}

		//chunked?
		if ( cl < 0 )
		{
			logger.error(XLog.CONN_MARK+"content-length not found,无法获取报文长度,transfer-coding="+request.getHeader("transfer-coding"));
			throw new JSystemException(KPlatResponseCode.CD_APPCONN_ERROR,KPlatResponseCode.MSG_APPCONN_ERROR+"t=c");
		}

		ByteArrayOutputStream bous = new ByteArrayOutputStream();
		byte[] indata = new byte[3072];	//3k;

		BufferedInputStream ins = null;
		try {
			ins = new BufferedInputStream(request.getInputStream());
			int totalLen = 0;
			while (true)
			{
				// 暂未做超时处理.
				//				logger.info("SAVAILABLE:"+ins.available());
				int len = ins.read(indata);
				//EOF
				if ( len == -1 || totalLen == cl )
				{
					logger.info(String.format(XLog.CONN_MARK+"__FINAL_READ:fl_len=%d,tl_len=%d,cl_len=%d",len,totalLen,cl));
					break;
				}

				//				logger.info(XLog.CONN_MARK+"__ONE_READ:rd_len="+len);

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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if( ins != null )
			{
				try {
					ins.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		logger.info("__APP_FILE_SIZE="+bous.size());
		return bous.toByteArray();
	}
}
