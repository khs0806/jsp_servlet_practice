package com.java.command.parsing;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

import com.java.command.Command;

public class ProxyCommand implements Command{
	// 프록시 서버란?
	// 시스템에 방화벽을 가지고 있는 경우, 접근이 불가하다.
	// 그래서 외부와의 통신을 위해 만들어 놓은 서버
	// 방화벽 안쪽에 있는 서버들의 외부 연결은 프록시 서버를 통해 이루어진다.
	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
		String url="http://www.kma.go.kr/weather/forecast/mid-term-rss3.jsp?stnId=109";
		GetMethod method = new GetMethod(url);
		
		HttpClient client = new HttpClient();
		int statusCode = client.executeMethod(method);
		logger.info(logMsg + statusCode);
		
		if(statusCode == HttpStatus.SC_OK) {
			String result = method.getResponseBodyAsString();
			logger.info(logMsg + result);
			
			response.setContentType("application/xml;charset=utf-8");		// application/text, application/json
			PrintWriter out = response.getWriter();
			out.print(result);
		}
		
		return null;
	}
}
