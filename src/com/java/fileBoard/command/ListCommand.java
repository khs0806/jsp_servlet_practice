package com.java.fileBoard.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.fileBoard.model.BoardDao;
import com.java.fileBoard.model.BoardDto;
import com.java.command.Command;

public class ListCommand implements Command {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		logger.info(logMsg + "ListCommand");
		
		String pageNumber = request.getParameter("pageNumber");
		if (pageNumber == null) pageNumber="1";
		
		int currentPage = Integer.parseInt(pageNumber);		// 1페이지를 요청, 현재 페이지
		logger.info(logMsg + "현재페이지 : " + currentPage);
		int boardSize = 10;									// 페이지당 게시글 개수 10개
		int startRow = boardSize * (currentPage-1) + 1;		// 화면에 뿌려질 게시글 시작행
		int endRow = boardSize * currentPage;				// 화면에 뿌려질 게시글 마지막행
		
		BoardDao dao = BoardDao.getInstance();
		int count = dao.getCount();							// 전체 게시물 개수를 가져오는 dao
		
		logger.info(logMsg + "전체 게시물 수 : " + count);
		
		ArrayList<BoardDto> boardList = null;
		if (count > 0) {
			// BoardDto, endRow
			boardList = dao.getBoardList(startRow, endRow);	// startRow 시작행과, endRow 마지막행을 
															// 파라미터로 넘겨줘서 한페이지의 리스트를 불러옴.
			logger.info(logMsg + boardList.size());
		}
		request.setAttribute("boardList", boardList);
		request.setAttribute("boardSize", boardSize);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("count", count);
		
		return "/WEB-INF/views/fileBoard/list.jsp";
	}

}
