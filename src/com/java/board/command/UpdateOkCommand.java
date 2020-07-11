package com.java.board.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.board.model.BoardDao;
import com.java.board.model.BoardDto;
import com.java.command.Command;

public class UpdateOkCommand implements Command {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
		BoardDto boardDto = new BoardDto();
		
		boardDto.setBoardNumber(Integer.parseInt(request.getParameter("boardNumber")));
		boardDto.setSubject(request.getParameter("subject"));
		boardDto.setContent(request.getParameter("content"));
		boardDto.setPassword(request.getParameter("password"));
		
		int check = BoardDao.getInstance().update(boardDto);
		logger.info(logMsg + "check : " + boardDto.getSubject());
		
		
		request.setAttribute("check", check);
		request.setAttribute("boardNumber", request.getParameter("boardNumber"));
		request.setAttribute("pageNumber", request.getParameter("pageNumber"));
		
		return "/WEB-INF/views/board/updateOk.jsp";
	}

}
