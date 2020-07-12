package com.java.fileBoard.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.fileBoard.model.BoardDao;
import com.java.fileBoard.model.BoardDto;
import com.java.command.Command;

public class ReadCommand implements Command{
	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
		int boardNumber = Integer.parseInt(request.getParameter("boardNumber"));
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		logger.info(logMsg + " 글번호:" + boardNumber + " 페이지번호:" + pageNumber);
		
		BoardDao dao = BoardDao.getInstance();
		BoardDto dto = dao.read(boardNumber);
		logger.info(logMsg + dto);
		
		if (dto.getFileSize() != 0) {
			int index = dto.getFileName().indexOf("_") + 1;
			String fileName = dto.getFileName().substring(index);
			dto.setFileName(fileName);
		}
		
		request.setAttribute("pageNumber", pageNumber);
		request.setAttribute("boardDto", dto);
		
		return "/WEB-INF/views/fileBoard/read.jsp";
	}
}
