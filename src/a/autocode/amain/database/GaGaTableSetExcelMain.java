package a.autocode.amain.database;

import a.autocode.database.maker.JTableMethodMaker;

/**
 * 根据excel配置生成数据bean的set方法.
 * @author zhangcq
 *
 */
public class GaGaTableSetExcelMain
{
	public static String sheetsName = "jn_device_auth";						//excel sheet.
//	public static String sheetsName = GaGaTableSQLMain.sheetsName;			//excel sheet.
	
	public static String excelName = "dbank_table_public.xlsx";				// excel name.
//	public static String excelName = GaGaTableSQLMain.excelName;			// excel name.
	
	private static String confPath = GaGaTableSQLMain.confPath;
	
	public static void main( String args[] )
	{
		JTableMethodMaker maker = new JTableMethodMaker(confPath,excelName,sheetsName);
		maker.make();
	}
}
