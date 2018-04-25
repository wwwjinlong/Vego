package a.autocode.amain.icop;

import a.autocode.icop.maker.ICOPServiceMaker;

public class GaGaICOPPortMain
{
	/************************
	 * true-覆盖 false-不覆盖.
	 ****************************/
	public static boolean OVERRIDE = false;				//!!!确认是否覆盖现有代码.
	public static boolean MAKE_SERVICE = true;			//是否需要生成service.
	
	static
	{
		OVERRIDE = true;
//		MAKE_SERVICE = false;
	}
	
	public static void main(String args[])
	{
		//配置文件读取和文件字符集 ******* 修改这里.
		String confpath = "classpath:a/autocode/amain/icop/icop_autocode.conf";

		ICOPServiceMaker maker = new ICOPServiceMaker(confpath,"utf-8");
		maker.override = OVERRIDE;
		maker.make_service = MAKE_SERVICE;
		
		maker.make();
	}
}
