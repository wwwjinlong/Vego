package jplat.tools.coder;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import jplat.tools.file.XFileTools;
import z.log.tracelog.XLog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class JsonCoder
{
	/**
	 * Object to A String.
	 * @param obj
	 * @return
	 */
	public static String toJsonString( Object obj )
	{
		return new Gson().toJson(obj);
	}
	
	/**
	 * Convert json to Object.
	 * @param json
	 * @param classz
	 * @return
	 */
	public static <T> T fromJsonString( String json, Class<T> clazz )
	{
		return new Gson().fromJson(json, clazz);
	}
	
	/**
	 * Convert json to Object.
	 * @author zhangcq
	 * @date Dec 8, 2016
	 * @comment 
	 * @param je
	 * @param clazz
	 * @return
	 */
	public static <T> T fromJson( JsonElement je, Class<T> clazz  )
	{
		return  new Gson().fromJson(je, clazz);
	}
	
	/**
	 * 格式化json
	 * @author zhangcq
	 * @date Jan 6, 2017
	 * @comment
	 */
	public static void prettyJson()
	{
		String filepath = "C:\\Users\\zhangcq\\Desktop\\临时文件夹\\33.json";
		String charset = "gbk";
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String uglyJsonStr = XFileTools.loadFile(filepath, charset);
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(uglyJsonStr);
		String prettyJsonStr2 = gson.toJson(je);
		System.out.println("prettyJsonStr2: " + prettyJsonStr2);
	}
	
	public static void main( String args[] )
	{
//		testGenericJson();
//		testGenericJson1();
		prettyJson();
	}
}
