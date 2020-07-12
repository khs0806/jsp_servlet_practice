package com.java.fileBoard.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.command.Command;

public class WriteCommand implements Command {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		logger.info(logMsg + "WriteCommand");
		
		// 부모글
		int boardNumber = 0;	// ROOT 글이면 0
		int groupNumber = 1;	// 그룹번호
		int sequenceNumber = 0;	// 글순서
		int sequenceLevel = 0;	// 글레벨
		
		// 답글은 부모글의 글번호, 그룹번호, 글순서, 글레벨을 가져와야댐.
		if(request.getParameter("boardNumber") != null) {
			boardNumber = Integer.parseInt(request.getParameter("boardNumber"));
			groupNumber = Integer.parseInt(request.getParameter("groupNumber"));
			sequenceNumber = Integer.parseInt(request.getParameter("sequenceNumber"));
			sequenceLevel = Integer.parseInt(request.getParameter("sequenceLevel"));
		}
		logger.info(logMsg + "boardNumber : " + boardNumber);
		logger.info(logMsg + "groupNumber : " + groupNumber);
		logger.info(logMsg + "sequenceNumber : " + sequenceNumber);
		logger.info(logMsg + "sequenceLevel : " + sequenceLevel);
		
		request.setAttribute("boardNumber", boardNumber);
		request.setAttribute("groupNumber", groupNumber);
		request.setAttribute("sequenceNumber", sequenceNumber);
		request.setAttribute("sequenceLevel", sequenceLevel);
		
		return "/WEB-INF/views/fileBoard/write.jsp";
	}

}
