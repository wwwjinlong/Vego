package jplat.tools.net.http;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import z.log.tracelog.XLog;

public class JHttpUtils
{
	public static String HTTP_GET = "GET";
	public static String HTTP_POST = "POST";

	/***
	 * 请求获得URL数据.
	 * @param url
	 * @return
	 * @throws IOException
	 */

	public static byte[] executeUrl( String url, String method ) throws IOException
	{
		XLog.log("__executeUrl:"+url);

		URL realUrl = new URL(url);
		HttpURLConnection  conn = (HttpURLConnection)realUrl.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod(method);
		conn.setRequestProperty("Accept", "*/*");
		//        conn.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
		//        conn.setReadTimeout(timeout);
		conn.setChunkedStreamingMode(-1);		//不要按照流模式输出.

		//HTTP header length.
		byte[] data = new byte[8*1024];
		BufferedInputStream ins = new BufferedInputStream(conn.getInputStream());
		int totallen = 0;

		Map<String,List<String>> paraMap = conn.getHeaderFields();
		for ( Map.Entry<String, List<String>> ent : paraMap.entrySet() )
		{
			XLog.log("key="+ent.getKey()+":value"+ent.getValue());
		}

		int sumLen = conn.getContentLength();
		XLog.log("totallen="+sumLen);
		if ( sumLen <= 0 )
		{
			XLog.log("no data need to be read:content-length="+sumLen);
			return "".getBytes();
		}

		ByteArrayOutputStream savaStream = new ByteArrayOutputStream();
		while ( true )
		{
			int len = ins.read(data);
			if ( len == -1 )		//EOF
			{
				break;
			}

			savaStream.write(data, 0, len);

			//end of http stream.
			totallen+=len;
			if( totallen >= sumLen )
			{
				break;
			}
		}

		ins.close();
		conn.disconnect();

		return savaStream.toByteArray();
	}
	
	/**
	 * POST方法 .
	 * @author zhangcq
	 * @date Dec 30, 2016
	 * @comment 
	 * @param url
	 * @param dataStr
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String postHttpString( String url, String dataStr, String charset ) throws IOException
	{
		return new String(postHttpData(url,dataStr,charset),charset);
	}
	
	/**
	 * 
	 * @author zhangcq
	 * @date Dec 30, 2016
	 * @comment 
	 * @param url
	 * @param dataStr
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static byte[] postHttpData( String url, String dataStr, String charset ) throws IOException
	{
		return postHttpData(url,dataStr.getBytes(charset));
	}
	
	private static byte[] postHttpData( String url, byte[] byteData ) throws IOException
	{
		HttpPost httpPost = new HttpPost(url);
		
		ByteArrayEntity byteEntity = new ByteArrayEntity(byteData);
		httpPost.setEntity(byteEntity);
		
		return executeHttp(httpPost);
	}
	
	private static String getHttpString( String url, String charset ) throws IOException
	{
		return new String( getHttpData(url), charset );
	}
	
	private static byte[] getHttpData( String url ) throws IOException
	{
		HttpGet httpGet = new HttpGet(url);
		return executeHttp(httpGet);
	}
	
	/**
	 * 
	 * @author zhangcq
	 * @date Dec 30, 2016
	 * @comment 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	private static byte[] executeHttp( HttpRequestBase httpReq )
			throws IOException
	{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try
		{
//			HttpGet httpget = new HttpGet(url);
			CloseableHttpResponse response = httpclient.execute(httpReq);
			
			try
			{
				HttpEntity entity = response.getEntity();
				byte[] data = handleByteEntity(entity);
				response.close();
				return data;
			}
			finally
			{
				response.close();
			}
		}
		finally
		{
			httpclient.close();
		}
	}

	private static byte[] handleByteEntity(HttpEntity entity)
	{
		if (entity == null) {
			return null;
		}

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];

		InputStream is = null;
		try
		{
			int len = 0;
			is = entity.getContent();
			while ((len = is.read(buffer)) != -1)
			{
				outStream.write(buffer, 0, len);
			}

		}
		catch (IllegalStateException | IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try {
				outStream.close();
				if( is != null)
					is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		byte[] data = outStream.toByteArray();
		return data;
	}
	
	public static void main(String args[]) throws IOException
	{
		String url = "http://162.16.2.157:8080/directBank/login/third/auth.do?autype=K&sessId=4653B4F21522B715DFE397D3DC0A55AB";
		String retMsg = getHttpString(url,"utf-8");
		
		XLog.log(retMsg);
	}
}
