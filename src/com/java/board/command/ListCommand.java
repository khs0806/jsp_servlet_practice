package com.java.board.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.board.model.BoardDao;
import com.java.board.model.BoardDto;
import com.java.command.Command;

public class ListCommand implements Command {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		logger.info(logMsg + "ListCommand");
		
		String pageNumber = request.getParameter("pageNumber");
		if (pageNumber == null) pageNumber="1";
		
		int currentPage = Integer.parseInt(pageNumber);		// 1페이지를 요청
		
		int boardSize = 10;									// 페이지당 게시글 개수 10개
		int startRow = boardSize * (currentPage-1) + 1;		// 시작번호, 1페이지에선 시작번호가 1, 2페이지에선 시작번호가 11
		int endRow = boardSize * currentPage;				// 끝번호,   1페이지에서 끝번호가 10, 2페이지에선 끝번호가 20
		
		BoardDao dao = BoardDao.getInstance();
		int count = dao.getCount();							// 전체 게시물 개수를 가져오는 dao
		logger.info(logMsg + "전체 게시물 수 : " + count);
		
		if (count > 0) {
			// BoardDto, endRow
			ArrayList<BoardDto> boardList = dao.getBoardList(startRow, endRow);	// startRow 시작페이지와, endRow 마지막 페이지를 
																				// 파라미터로 넘겨줘서 한페이지의 리스트를 불러옴.
			logger.info(logMsg + boardList.size());
		}
		
		return null;
	}

}
