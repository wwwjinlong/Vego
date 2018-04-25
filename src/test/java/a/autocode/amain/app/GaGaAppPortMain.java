package a.autocode.amain.app;

import a.autocode.app.maker.AppServiceMaker;

public class GaGaAppPortMain
{
	/************************
	 * true-覆盖 false-不覆盖.
	 ****************************/
	private static boolean OVERRIDE = false;
	
	static {
		OVERRIDE = true;
	}
	
	private static String confpath = "classpath:a/autocode/amain/app/app_autocode.conf";

	public static void main(String args[])
	{
		String charset = "utf-8";

		AppServiceMaker maker = new AppServiceMaker(confpath,"utf-8");

		//为了防止错误覆盖文件，请先确认是否需要覆盖已存在文件. true-覆盖 false-不覆盖.
		maker.override = OVERRIDE;
		maker.make();
	}
}
