package jplat.tools.stream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;

import z.log.tracelog.XLog;

public class JFileUtils {
	
	public static String CLPATH = "classpath:";
	
	public static InputStream loadFileStream( String file_path ) throws FileNotFoundException
	{
		String filepath = file_path;

		//classpah:abc/my/package.
		if ( file_path.startsWith(CLPATH) )
		{
			filepath = file_path.substring(CLPATH.length());
			
			//getResourceAsStream,data was cached.
			//BufferedReader reader = new BufferedReader( new InputStreamReader(getClass().getClassLoader().getResourceAsStream(cnf),"utf-8"));
			//String path = JCodeUtils.class.getClassLoader().getResource(filepath).toURI().getPath();

			ClassLoader classloader = JFileUtils.class.getClassLoader();
			URL url = classloader.getResource(filepath);
			if ( url == null )
			{
				String errmsg = String.format("fail to load file[%s]", filepath);
				XLog.logerr(errmsg);
				throw new RuntimeException("NOFILE:"+errmsg);
			}

			try {
				return url.openStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				new RuntimeException(e.getMessage());
			}
		}

		return new FileInputStream(new File(filepath));
	}
	
	public static void archiveFile( TarArchiveOutputStream taos, String filepath, byte[] filedata ) throws Exception {
		int BUFFER = 4096;
		TarArchiveEntry entry = new TarArchiveEntry(filepath);

		entry.setSize(filedata.length);

		taos.putArchiveEntry(entry);
		taos.write(filedata);
		taos.closeArchiveEntry();
	}
	

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
	

	
	public static void log( String lgmsg )
	{
		Thread currTh = Thread.currentThread();
		System.out.println( "[ZLOG]"+"["+currTh.getName()+"]:"+lgmsg );
	}


}
