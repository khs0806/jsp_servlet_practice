package com.java.fileBoard.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.fileBoard.model.BoardDao;
import com.java.fileBoard.model.BoardDto;
import com.java.command.Command;

public class UpdateCommand implements Command {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		int boardNumber = Integer.parseInt(request.getParameter("boardNumber"));
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		logger.info(logMsg + "boardNumber : " + boardNumber + " pageNumber : " + pageNumber);
		
		BoardDao dao = BoardDao.getInstance();
		BoardDto boardDto = dao.updateView(boardNumber);
		logger.info(logMsg + boardDto);
		String storedFileName = boardDto.getFileName();
		
		if (boardDto.getFileSize() != 0) {
			int index = boardDto.getFileName().indexOf("_") + 1;
			String fileName = boardDto.getFileName().substring(index);
			boardDto.setFileName(fileName);
		}
		
		request.setAttribute("storedFileName", storedFileName);
		request.setAttribute("boardNumber", boardNumber);
		request.setAttribute("pageNumber", pageNumber);
		request.setAttribute("boardDto", boardDto);
		
		return "/WEB-INF/views/fileBoard/update.jsp";
	}

}
