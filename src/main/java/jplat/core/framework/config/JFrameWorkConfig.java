package jplat.core.framework.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * not used yet.
 * @author zhangcq
 *
 */
@Configuration  
//@ComponentScan(basePackages = {"jplat.service.ctl","jplat.api.rel.controller"})
@ComponentScan(basePackages = "jplat.api.rel.controller" )  
public class JFrameWorkConfig
{
	public JFrameWorkConfig()
	{
		System.err.println("JFrameWorkConfig constructor.");
	}
}
