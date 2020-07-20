
package com.java.controller;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.command.Command;


public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public Logger logger = Logger.getLogger(FrontController.class.getName());
	public String logMsg = "logMsg========= ";
	
	private HashMap<String,Object> commandMap = new  HashMap<>();
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		String configFile = config.getInitParameter("configFile");
		String path = config.getServletContext().getRealPath(configFile);
		logger.info(logMsg + path);
		
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		Properties pro = new Properties();
		
		try {
			fis = new FileInputStream(path);
			bis = new BufferedInputStream(fis);
			pro.load(bis);
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(bis!=null) bis.close();
				if(fis!=null) fis.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		Iterator<Object> keyIter = pro.keySet().iterator();
		while(keyIter.hasNext()) {
			String command = (String) keyIter.next();
			String className = pro.getProperty(command);
			logger.info(logMsg + command + " " + className);
			
			try {
				Class<?> handlerClass = Class.forName(className);
				Object handlerInstance = handlerClass.getDeclaredConstructor().newInstance();
				
				commandMap.put(command, handlerInstance);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cmd = request.getServletPath();
		logger.info(logMsg + cmd);
		
		String viewPage = null;
		
		try {
			Command com = (Command) commandMap.get(cmd);
			viewPage = com.proRequest(request, response);
		} catch(Throwable e) {
			e.printStackTrace();
		}
		
		RequestDispatcher rd = null;
		// viewPage가 반환되어 null이 아닐 때
		if(viewPage != null) {
						// 우편번호페이지와 아이디중복체크 페이지는 template를 거치지 않고 바로 포웨이드한다.
			if (viewPage.equals("/WEB-INF/views/member/zipcode.jsp") || viewPage.equals("/WEB-INF/views/member/idCheck.jsp")) {
				rd = request.getRequestDispatcher(viewPage);
				
			} else {	// 그 외의 다른 페이지들은 template를 통해 반환하여 포웨이드 해준다
				logger.info(logMsg + "viewPage : " +viewPage);
				rd = request.getRequestDispatcher("/template/templateIndex.jsp");
				request.setAttribute("viewPage", viewPage);
			}
			rd.forward(request, response);
			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
