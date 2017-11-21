package a.autocode.amain.packer;

import java.io.File;

import a.autocode.packer.JBigPackExector;
import a.autocode.packer.JMasterPacker;
import a.autocode.utils.QLogUtils;

/**
 * 为了复用其他开发人员的ls文件，在配置目录中的文件会覆盖掉原ls文件的相同key的对应的value值。
 * 一般覆盖的是源码目录和输出目录两项.
 * 配置文件的查找规则是配置目录(PACK_CONF_PATH)中对应的文件名字为{appname}_def.cnf的配置文件.
 * @author zhangcq
 *
 */


public class GaGaMasterPacker
{
	/********* **********************************
	 * 					工程环境选择 >>>>>>>>>>>> 
	 * 									********************/
	/********* !!!!打包前确保工程已由Eclipse完成编译 *************/
	
//	private static String ENV = "test157";				//测试环境.
	private static String ENV = "eshop158,uat158";		//UAT.
	
//	private static String ENV = "dev152";				//开发环境.
	
//	private static String ENV = "trunk";				//生产环境.
	
	/******** 路径配置 ************* >>>>. ****/
	//LS文件总目录
	public static String PACK_MASTER_DIR = "E:\\EBANK_CODE-svn\\EBANK_SERVER\\PACK\\__PACK.LS";
	
	//配置根目录。
	public static String PACK_CONF_PATH = "E:\\EBANK_CODE-svn\\EBANK_SERVER\\PACK\\__PACK.LS\\__PRO_PACK\\config";
	
	//打包输出目录.
//	public static String PACK_OUT_DIR = "E:\\EBANK_CODE-svn\\EBANK_SERVER\\PACK\\__PACK.LS\\__PRO_TARS\\";
	
	//所有项目的主目录.
//	public static String PROJEC_HOME = "E:\\EBANK_CODE-svn\\EBANK_SERVER";
	
	//打包过滤名单,如果为空则打包所有目录.
	public static String[] PACK_DIRS  = {"zhangcq","#liuxx","#baoxz","#yur","#chenz"};
//	public static String[] PACK_DIRS  = null;
	
	public boolean debug = false;
	
	public static void main( String args[] )
	{
		JMasterPacker masterPakcer = new JMasterPacker(ENV,PACK_MASTER_DIR,PACK_CONF_PATH);
		masterPakcer.masterPack();
	}
}
