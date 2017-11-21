package jplat.service.asyntask;

public class JTestTask implements Runnable {

	public JTestTask()
	{
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stu
		System.out.println("TD"+Thread.currentThread().getId());
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
