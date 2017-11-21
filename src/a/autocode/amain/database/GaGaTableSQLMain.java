package a.autocode.amain.database;

import a.autocode.database.JTableSQLMaker;
import a.autocode.database.maker.JTableSqlGenerator;

/**
 * table autocode
 * @author zhangcq
 * @comment 
 */
public class GaGaTableSQLMain
{
	public static String sheetsName = "slog_req_trace";					//excel sheet.
	
	public static String excelName = "dbank_table_tracelog.xlsx";			// excel name.
	
	private static int DB_TYPE = JTableSqlGenerator.DB_TYPE_ORACLE;			// database type: Oracle
//	private static int DB_TYPE = JTableSqlGenerator.DB_TYPE_MYSQL;			// database type: MySQL
	
	public static String confPath = "classpath:a/autocode/amain/database/table_autocode.conf";
	
	public static void main( String args[] )
	{
		JTableSQLMaker maker = new JTableSQLMaker(confPath,DB_TYPE,excelName,sheetsName);
		maker.make();
	}
}
