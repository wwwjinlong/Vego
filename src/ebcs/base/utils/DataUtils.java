package ebcs.base.utils;

import java.lang.reflect.Field;
import java.util.List;

public class DataUtils {
	
	/**
	 * 过滤空节点.只支持一级节点.
	 * @author zhangcq
	 * @date Apr 18, 2017
	 * @comment 
	 * @param obj
	 */
	public static int removeEmptyList( Object obj )
	{
		int upCnt = 0;
		
		Field[] fds = obj.getClass().getDeclaredFields();
		for ( int i = 0; i < fds.length; ++i )
		{
//			XLog.log("name=%s,type=%s,isPri=%s", fds[i].getName(),fds[i].getType().getName(),fds[i].getType().isPrimitive()+"");
			if ( "java.util.List".equals(fds[i].getType().getName())) 
			{
//				XLog.log("meet list:%s", fds[i].getName());
				fds[i].setAccessible(true);
				try
				{
					List list = (List)fds[i].get(obj);
					
					if ( list != null && list.size() == 1 )
					{
						Object item = list.get(0);
						
						if ( checkEmptyObject( item ) )
						{
							fds[i].set(obj, null);
							
							upCnt++;
						}
					}
					
					//是否有不为空的String.
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return upCnt;
	}
	
	public static boolean checkEmptyObject( Object obj )
	{
		Field[] fds = obj.getClass().getDeclaredFields();
		for ( int i = 0; i < fds.length; ++i )
		{
			//值判断了非基本类型 应该已经够用了吧.
			if( !fds[i].getType().isPrimitive() )
			{
				fds[i].setAccessible(true);
				try
				{
					if ( fds[i].get(obj) != null )
					{
						return false;
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return true;
	}

}
