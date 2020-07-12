package com.java.fileBoard.command;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.fileBoard.model.BoardDao;
import com.java.fileBoard.model.BoardDto;
import com.java.command.Command;

public class DeleteOkCommand implements Command {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		int boardNumber = Integer.parseInt(request.getParameter("boardNumber"));
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		String password = request.getParameter("password");
		logger.info(logMsg + "boardNumber:" + boardNumber + " pageNumber:" + pageNumber + " password:" + password);
		
		
		BoardDao boardDao = BoardDao.getInstance();
		
		BoardDto readBoard = boardDao.select(boardNumber);
		int check = boardDao.delete(boardNumber, password);
		logger.info(logMsg + "글삭제 체크? : " + check);
		logger.info(logMsg + "삭제 파일경로? : " + readBoard.getPath());
		
		if (check > 0 && readBoard.getPath() != null) {
			File file = new File(readBoard.getPath());
			if (file.exists() && file.isFile()) file.delete();
		}
		
		request.setAttribute("check", check);
		request.setAttribute("pageNumber", pageNumber);
		return "/WEB-INF/views/fileBoard/deleteOk.jsp";
	}

}
