package jplat.tools.data.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解,是否必输.
 * 该注解对于基本类型（字符串不是基本类型）是无效的。
 * 因为基本类型默认是0值后者false，是有初始值的.
 * @author zhangcq
 * @date Jan 20, 2017
 * @comment
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JDataRuleRequired
{
	public boolean value() default true;
}
