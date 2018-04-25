package a.autocode.amain.database;

import a.autocode.database.maker.JTableSetCodeMaker;

/**
 * 根据class信息生成bean的setters方法.
 * @author zhangcq
 *
 */
public class GaGaTableSetClazzMain
{
	private static Class<?> classType = null;
	
	public static void main ( String args[] )
	{
		JTableSetCodeMaker maker = new JTableSetCodeMaker( classType );
		maker.make();
	}
}
