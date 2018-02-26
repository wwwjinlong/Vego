package jplat.tools.stream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import jplat.tools.coder.MD5Utils;
import z.log.tracelog.JLog;

public class JRandomFileReader
{
	private File filepath;
	private RandomAccessFile filereader;
	
	public JRandomFileReader( File filep )
	{
		filepath = filep;
	}
	
	public boolean write( long offset, byte[] data ) throws IOException
	{
		filereader.seek(offset);
		filereader.write(data);
		return true;
	}
	
	public byte[] read( long offset, long length ) throws IOException
	{
		int rlen = (int)length;
		byte[] data = new byte[rlen];
		filereader.seek(offset);
		filereader.read(data, 0, rlen);
		
		return data;
	}
	
	public boolean open( String mode )
	{
		try {
			filereader = new RandomAccessFile(filepath,mode);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean close()
	{
		try {
			filereader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static void main(String args[]) throws Exception
	{
		String path = "E:\\lqm.doc";
		String path1 = "E:\\lqm1.doc";
		
		File file = new File(path);
		File file1 = new File(path1);
		
		JRandomFileReader writer = new JRandomFileReader(file1);
		JRandomFileReader reader = new JRandomFileReader(file);
		reader.open("r");
		writer.open("rw");
		for ( int i = 0; i < 103424-1; ++i )
		{
			byte[] data = reader.read(i,2);
			writer.write(i, data);
		}
		reader.close();
		writer.close();
		
		byte[] d = JFileUtils.loadFile(file.getAbsolutePath());
		byte[] d1 = JFileUtils.loadFile(file.getAbsolutePath());
		
		JLog.log("%d-%d", d.length,d1.length);
		JLog.log("%s-%s", MD5Utils.getMD5(d),MD5Utils.getMD5(d1));
	}
}
