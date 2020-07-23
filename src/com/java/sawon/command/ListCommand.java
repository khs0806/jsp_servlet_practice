package com.java.sawon.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.command.Command;

public class ListCommand implements Command {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
//		return "/WEB-INF/views/ajax/sawon/list.jsp";
		return "/WEB-INF/views/jquery/sawon/list.jsp";
	}

}
