package com.java.command.parsing;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.command.Command;

public class MapCommand implements Command {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		return "/WEB-INF/views/ajax/map/kakaoMap.jsp";
	}

}
