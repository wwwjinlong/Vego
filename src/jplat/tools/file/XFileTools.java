package jplat.tools.file;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import jplat.base.constant.KPlatResponseCode;
import jplat.error.exception.JSystemException;

public class XFileTools
{
	public static String loadFile( String filepath, String charset )
	{
		try {
			return new String(loadFile(filepath),charset);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	//根据路径读取文件.
	public static byte[] loadFile( String filepath ) throws FileNotFoundException
	{
		BufferedInputStream bis=null;
		try {
			bis = new BufferedInputStream(new FileInputStream(new File(filepath )));
			ByteArrayOutputStream oas = new ByteArrayOutputStream();
			
			byte[] readBuffer = new byte[2048];
			int readlen = 0;
			while ( true )
			{
				readlen = bis.read(readBuffer);
				if ( readlen == -1 )
				{
					break;
				}

				oas.write(readBuffer,0,readlen);
			}
			bis.close();
			
			return oas.toByteArray();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	//根据路径读取文件.
	public static String readFile( String filepath, String charset )
	{
		byte[] readbts=null;
		try {
			readbts = loadFile( filepath );
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if ( readbts == null )
		{
			return null;
		}
		
		String retStr;
		try {
			retStr = new String( readbts, charset );
			return retStr;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static boolean writeFile( String filepath, byte[] odata )
	{
		try {
			BufferedOutputStream os = new BufferedOutputStream( new FileOutputStream( filepath ));
			os.write(odata);
			os.close();
			
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
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
	
	public static void log( String lgmsg )
	{
		Thread currTh = Thread.currentThread();
		System.out.println( "[ZLOG]"+"["+currTh.getName()+"]:"+lgmsg );
	}

}
