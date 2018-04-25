package a.autocode.amain.packer;

import a.autocode.packer.JBigPacker;

/**
 * mapRule:directBank/src ---> directBank/WEB-INF/classes
 * mapRule:directBank/WebContent ---> directBank
 * @comment 适合一个项目 多个上线包的情况. 先复制到packtmp_目录，然后从packtmp_目录打成tar包.
 * @author zhangcq
 * @log	1. 新增支持多个环境共用一个打包列表pack list,并输出到统一目录.
 */

public class GaGaPacker 
{
	/***********## !!!!打包前确保工程已由Eclipse完成编译 *************/
	
	/***********## 工程环境选择 *************************/
//	public static String ENV = "test152";				//测试环境.
//	public static String ENV = "test157";				//测试环境.
//	public static String ENV = "uat158";				//UAT.

//	public static String ENV = "dev152";				//开发环境.
//	public static String ENV = "dev169";				//营销开发环境.
	
	public static String ENV = "trunk";				//生产环境.
	
//	public String ENV = "dev134";				//智慧厅堂开发环境.
	
	/************## 开发测试LS文件位置  ******************/
//	private static String packLS = KBigPacker.DIRECTBANK_LS;			//E钱庄directBank
//	private static String packLS = KBigPacker.DBANK_LS;					//E钱庄dbank

//	private static String packLS = KBigPacker.MGR_LS;					//E钱庄管理台
//	private static String packLS = KBigPacker.ZHTT_MBANK_LS;			//智慧厅堂 mbank.
//	private static String packLS = KBigPacker.FILE_LS;					//数字保管箱.
	
	/***********## 生产环境LS文件位置 ******************/
	private static String packLS = KBigPacker.DIRECTBANK_TRUNK_LS;		//E钱庄 directBank-App
//	private static String packLS = KBigPacker.DBANK_TRUNK_LS;			//E钱庄App-dbank.
//	private static String packLS = KBigPacker.ESHOP_TRUNK_LS;			//E商户App
//	private static String packLS = KBigPacker.DOLPHINE_TRUNK_LS;		//E商户管理端.
//	private static String packLS = KBigPacker.MKCD_LS;					//营销云平台.
//	private static String packLS = KBigPacker.MANAGER_DIRECT_TRUNK_LS;	//E钱庄  管理端
//	private static String packLS = KBigPacker.FILEBOXY_TRUNK_LS;		//E钱庄 directBank-App
	
	/*************** CONFIG END ****************/
	
	public static void main( String[] args )
	{
		JBigPacker packer = new JBigPacker(ENV,packLS);
		packer.charset = "gbk";
		
		packer.pack();
	}
}