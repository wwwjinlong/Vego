package jplat.service.asyntask;

/**
 * 异步任务样例 该类执行完成后返回一个String类型.
 * @author zhangcq
 *
 * @param <String>
 */
public class JASynTaskExample extends JAsynTask<String> {

	@Override
	public void run()
	{
		//do something here.
		try {
			Thread.sleep(5*1000);		//5 seconds.
//			Thread.sleep(500);			//0.5 seconds.
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//then return success.
		try {
			put("SUCCESS.");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
