package a.autocode.amain.database;

import a.autocode.database.maker.JTableServiceMaker;

/**
 * 用来生成数据库的Service服务.
 * @author zhangcq
 *
 */
public class GaGaTableServiceMain
{
	public static String sheetsName = "jn_device_auth";						//excel sheet.
//	public static String sheetsName = GaGaTableSQLMain.sheetsName;			//excel sheet.
	
	public static String excelName = "dbank_table_public.xlsx";				// excel name.
//	public static String excelName = GaGaTableSQLMain.excelName;			// excel name.
	
	private static String confPath = GaGaTableSQLMain.confPath;
	
	public static void main (String args[])
	{
		JTableServiceMaker maker = new JTableServiceMaker(confPath,excelName,sheetsName);
		maker.make();
	}
}
