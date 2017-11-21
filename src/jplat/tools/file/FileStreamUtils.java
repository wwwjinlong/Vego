package jplat.tools.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jplat.error.exception.JSystemException;
import jplat.error.exception.JTransException;
import jplat.tools.config.JConfigManager;
import jplat.tools.string.DateUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fileStorage.auth.Credential;
import com.fileStorage.service.FSClient;

/**
 * 大数据平台文件操作
 * @author zhangcq
 * @date Dec 26, 2016
 * @comment
 */
public class FileStreamUtils
{
	private static Logger logger = LogManager.getLogger(FileStreamUtils.class);
	private static String tmpDir = "0";//暂时无用的参数
	private static String TRCE_ACTION_CLDSTRORAGE_INIT = "00";//云服务初始化
	private static String TRCE_ACTION_CLDSTRORAGE_DEL = "01";//云文件删除
	private static String TRCE_ACTION_CLDSTRORAGE_SAVE = "02";//云文件保存
	private static String TRCE_ACTION_CLDSTRORAGE_GET = "03";//云文件获取
	private static String EVENT_SUCCESS = "操作成功";
	private static String EVENT_FAIL = "操作失败";
	
	private static String FILE_SYSTEM_IP = JConfigManager.getInstance().getSystemConfig().getString("FILE_SYSTEM_IP");//结构化文件系统ip
	private static String FILE_SYSTEM_PORT = JConfigManager.getInstance().getSystemConfig().getString("FILE_SYSTEM_PORT");//结构化文件系统端口
	private static String FILE_SYSTEM_ID = JConfigManager.getInstance().getSystemConfig().getString("FILE_SYSTEM_ID");//结构化文件系统用户id
	private static String FILE_SYSTEM_PASSWORD = JConfigManager.getInstance().getSystemConfig().getString("FILE_SYSTEM_PASSWORD");//结构化文件系统密码
	
	
	/**
	 * 修改
	 * @param authId
	 * @param fileName
	 * @param data
	 * @return
	 */
	public static boolean fileWrite( String authId, String fileName, byte[] data )
	{
		String filePath = getTempFilePath(authId,fileName);

		logger.info("__FILE_WRITE:"+filePath);
		BufferedOutputStream fileOs = null;
		try
		{
			fileOs = new BufferedOutputStream(new FileOutputStream(filePath));
			fileOs.write(data);
			fileOs.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				if ( fileOs != null )
				{
					fileOs.close();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	public static String getTempFilePath( String authId, String fileName )
	{
		String fileDir = checkDir(authId);		//@Prob		是否支持并发.

		String filePath = fileDir+fileName;

		return filePath;
	}

	/**
	 * 获取文件存储路径.
	 * @author zhangcq
	 * @date Dec 26, 2016
	 * @comment 
	 * @param authId
	 * @return
	 */
	public static String checkDir( String authId )
	{
		File filedir = new File( tmpDir+authId);
		if ( !filedir.exists() )
		{
			filedir.mkdirs();
		}

		return filedir.getAbsolutePath()+File.separator;
	}

	/**
	 * 读取文件.
	 * @author zhangcq
	 * @date Dec 26, 2016
	 * @comment 
	 * @param request
	 * @return
	 * @throws JSystemException 
	 */
	public static byte[] readInputStream( HttpServletRequest request )
	{
		int cl = request.getContentLength();		

		if ( cl == 0 )
		{
			return "".getBytes();
		}

		//chunked?
		if ( cl < 0 )
		{
			logger.error("ERROR_INFO:content-length not found,无法获取报文长度,transfer-coding="+request.getHeader("transfer-coding"));
			return null;
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
					logger.info(String.format("ERROR_INFO:__FINAL_READ:fl_len=%d,tl_len=%d,cl_len=%d",len,totalLen,cl));
					break;
				}

//				logger.info(XLog.CONN_MARK+"__ONE_READ:rd_len="+len);

				totalLen += len;
				bous.write(indata, 0, len);
			}

			ins.close();
			if (cl != bous.size())
			{
				logger.error("ERROR_INFO:__READ_ERROR,contentLength=" + cl+",readlen="+ bous.size());
				return "".getBytes();
			}

			bous.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally
		{
			if( ins != null )
			{
				try {
					ins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		logger.info("__APP_FILE_SIZE="+bous.size());
		return bous.toByteArray();
	}
	
	/**
	 * 获得文件序号.
	 * @author zhangcq
	 * @date Dec 26, 2016
	 * @comment 
	 * @param seqno
	 * @return
	 */
	public static String getPieceFileName( long seqNo, long filesize )
	{
		return String.format("%d.%d.tmp",seqNo, filesize );
	}

	/**
	 * 上传文件到大数据平台.
	 * @author yur
	 * @comment 
	 * @param data
	 * @throws JTransException
	 */
	public static String saveInCloud( byte[] data )
	{
		String mark = DateUtil.format(new Date(), "yyyyMMddHHmmssSSS");
		try
		{
			
			FSClient client = initStorageClient();
			logger.info(String.format("code:%s,mark:%s,value:%s",TRCE_ACTION_CLDSTRORAGE_SAVE, mark,""));
			Map<String,String> metaMap = new HashMap<String,String>();
			logger.info(String.format("code:%s,mark:%s,value:%s",TRCE_ACTION_CLDSTRORAGE_INIT, mark, FILE_SYSTEM_IP ));
			
			String rowKey = client.putObject(data, metaMap);
			logger.info("rowKey="+rowKey);
			return rowKey;
		} catch (Exception e) {
			logger.info(String.format("code:%s,msg:%s,mark:%s,value:%s",TRCE_ACTION_CLDSTRORAGE_SAVE,EVENT_FAIL, mark,""));
			return null;
		}
	}
	
	/**
	 * 下载文件.
	 * @author zhangcq
	 * @date Dec 27, 2016
	 * @comment 
	 * @param rowKey
	 * @return
	 * @throws JTransException
	 */
	public static byte[] getFileFromCloud( String rowKey )
	{
		String mark = DateUtil.format(new Date(), "yyyyMMddHHmmssSSS");
		
		try
		{
			FSClient client = initStorageClient();
			
			logger.info(String.format("code:%s,mark:%s,value:%s",TRCE_ACTION_CLDSTRORAGE_GET, mark,rowKey));
			
			byte[] data = client.getObject(rowKey);
			
			logger.info(String.format("code:%s,msg:%s,mark:%s,value:%s",TRCE_ACTION_CLDSTRORAGE_GET,EVENT_SUCCESS, mark,data.length+""));
			
			return data;
		} catch (IOException e) {
			e.printStackTrace();
			logger.info(String.format("code:%s,msg:%s,mark:%s,value:%s",TRCE_ACTION_CLDSTRORAGE_GET,EVENT_FAIL, mark,e.getMessage()));
			return null;
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.info(String.format("code:%s,msg:%s,mark:%s,value:%s",TRCE_ACTION_CLDSTRORAGE_GET,EVENT_FAIL, mark,e.getMessage()));
			return null;
		}
	}
	
	/**
	 * 删除文件.
	 * @author zhangcq
	 * @date Dec 27, 2016
	 * @comment 
	 * @param rowKey
	 * @return
	 * @throws JTransException
	 */
	public static boolean deleteFileFromCloud( String rowKey )
	{
		String mark = DateUtil.format(new Date(), "yyyyMMddHHmmssSSS");
		
		try
		{
			FSClient client = initStorageClient();
			
			logger.info(String.format("code:%s,mark:%s,value:%s",TRCE_ACTION_CLDSTRORAGE_DEL, mark,""));
			
			client.deleteObject(rowKey);
			
			logger.info(String.format("code:%s,msg:%s,mark:%s,value:%s",TRCE_ACTION_CLDSTRORAGE_DEL,EVENT_SUCCESS, mark,""));
			
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.info(String.format("code:%s,msg:%s,mark:%s,value:%s",TRCE_ACTION_CLDSTRORAGE_DEL,EVENT_FAIL, mark,e.getMessage()));
			return false;
		}
	}
	
	/**
	 * 初始化客户端.
	 * @author zhangcq
	 * @date Dec 28, 2016
	 * @comment 
	 * @return
	 * @throws IOException 
	 */
	private static FSClient initStorageClient() throws Exception
	{
		return FSClient.getInstance(FILE_SYSTEM_IP, Integer.valueOf(FILE_SYSTEM_PORT),
				new Credential(FILE_SYSTEM_ID, FILE_SYSTEM_PASSWORD));
	}

	public static void main(String args[]) {
		try {
			System.out.println("Begin save file...");
			FileInputStream fis = new FileInputStream("C:\\Users\\YR\\Desktop\\PCM1303.TXT");
			byte data[] = new byte[fis.available()];
			fis.read(data);
			fis.close();
			System.out.println(FileStreamUtils.saveInCloud(data));
			byte data2[] =FileStreamUtils.getFileFromCloud("56633973b6b371c0f7aeb414b567287e");
		    System.out.println(new String(data2));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
