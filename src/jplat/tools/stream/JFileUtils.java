package jplat.tools.stream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
}
