package a.autocode.amain.packer;

public class KBigPacker
{
	/***********    主干环境  start **************/
	public static final String TRUNK_LS_HOME = "E:\\EBANK_CODE-svn\\EBANK_SERVER\\PACK\\__PACK.LS\\__PRO_PACK\\";
	
	public static final String MANAGER_DIRECT_TRUNK_LS = TRUNK_LS_HOME+"directManager_pack_pro.ls";
	
	public static final String DIRECTBANK_TRUNK_LS = TRUNK_LS_HOME+"directBank_pack_pro.ls";
	
	public static final String DBANK_TRUNK_LS = TRUNK_LS_HOME+"dbank_pack_pro.ls";
	
	public static final String ESHOP_TRUNK_LS = TRUNK_LS_HOME+"eShop_pack_pro.ls";
	
	public static final String DOLPHINE_TRUNK_LS = TRUNK_LS_HOME+"dolphine_pack_pro.ls";
	
	public static final String FILEBOXY_TRUNK_LS = TRUNK_LS_HOME+"fileboxy_pack_pro.ls";
	
	public static final String MKCD_TRUNK_LS = TRUNK_LS_HOME+"mkcd_pack_pro.ls";
	/***********    主干环境 end  **************/
	
	//CONFIG:the classes or xml file-list path and the charset of the file.
	public static final String PACK_HOME_DIR = "D:\\MarketingCloudWorkSpace\\PACK\\__PACK.LS\\chenz\\";
	
	//E钱庄
	public static final String DIRECTBANK_LS  = PACK_HOME_DIR+"directBank_pack.ls";
	
	//E钱庄(新)
	public static final String DBANK_LS  = PACK_HOME_DIR+"dbank_pack.ls";

	//E钱庄管理台
	public static final String MGR_LS = PACK_HOME_DIR+"directManager_pack.ls";
	
	//数字保管箱.
	public static final String FILE_LS = PACK_HOME_DIR+"fileboxy_pack.ls";
	
	//营销平台
	public static final String MKCD_LS = PACK_HOME_DIR+"mkcd_pack.ls";

	//智慧厅堂.
	public static final String ZHTT_LS = "E:\\ebanksvn2\\source\\hbank\\zhtt\\bcs_zhtt\\pack_zhtt.ls";
	
	public static final String ZHTT_MBANK_LS = PACK_HOME_DIR+"mbank_pack.ls";
	
	//============= 不能直接打包的配置文件.
	public static String[] blkList = {
			"web.xml",
			"applicationContext.xml",
			"applicationContext-ibatis.xml",
			"context.xml",
			"conf.properties",
			"sql-map-config.xml"
			//							"mbank-servlet.xml"
	};
}
