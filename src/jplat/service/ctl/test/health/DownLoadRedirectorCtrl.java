package jplat.service.ctl.test.health;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DownLoadRedirectorCtrl
{
	private static String DOWN_KEY = "downloadUrl";
	private static String DOWNDIR_URL = "http://localhost:8080/fileboxy/download/";
	
	@RequestMapping("/download/{appName}.apk")
	public String redirectDownloadUrl( HttpServletRequest request, @PathVariable String appName )
	{
		request.setAttribute(DOWN_KEY, DOWNDIR_URL+appName );
		return "/test/redownload";
	}
}
