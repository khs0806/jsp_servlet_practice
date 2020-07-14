package com.java.fileBoard.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.java.database.ConnectionProvider;
import com.java.database.JdbcUtil;
import com.java.myBatis.SqlManager;

public class BoardDao {
	private static BoardDao instance = new BoardDao();
	
	private static SqlSessionFactory sqlSessionFactory = SqlManager.getInstance();
	private SqlSession session;
	
	public static BoardDao getInstance() {
		return instance;
	}
	
	// 게시글 작성하기
	public int insert(BoardDto boardDto) {
		int value = 0;
		
		writeNumber(boardDto);
		
		try {
			if (boardDto.getFileSize() == 0) {	// 파일 첨부가 되지 않았을 경우에 DB 등록 
				session = sqlSessionFactory.openSession();
				value = session.update("fileBoard_insert", boardDto);
				session.commit();
				
			} else {							// 파일첨부가 됬을 경우에 DB 등록
				session = sqlSessionFactory.openSession();
				value = session.update("fileBoard_insert_add_file", boardDto);
				session.commit();
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return value;
	}
	// 게시글 작성시 부모글인지 자식글인지 처리
	public void writeNumber(BoardDto boardDto) {
		//그룹번호(부모), 글순서(자식), 글레벨(자식)
		int boardNumber = boardDto.getBoardNumber();		// 0
		int groupNumber = boardDto.getGroupNumber();		// 1
		int sequenceNumber = boardDto.getSequenceNumber();	// 0
		int sequenceLevel = boardDto.getSequenceLevel();	// 0
		
		try {
			
			if(boardNumber == 0) {		// 부모글 디비 처리, groupNumber(그룹번호) 증가
				session = sqlSessionFactory.openSession();
				int max = session.selectOne("fileBoard_group_number_max");
				
				if (max != 0) boardDto.setGroupNumber(max+1);
			} else {					// 답글 디비 처리, sequenceNumber(글순서)와 sequenceLevel(글레벨) 증가
				session = sqlSessionFactory.openSession();
				session.update("fileBoard_seqeunce_number", boardDto);
				session.commit();
				
				boardDto.setSequenceNumber(++sequenceNumber);
				boardDto.setSequenceLevel(++sequenceLevel);
			}
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
//	게시글 총개수 가져오기
	public int getCount() {
		int value = 0;
		
		try {
			session = sqlSessionFactory.openSession();
			value = session.selectOne("fileBoard_count");
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return value;
	}
//	게시글 목록 가져오기
	public List<BoardDto> getBoardList(int startRow, int endRow) {
		List<BoardDto> boardList = new ArrayList<>();
		Map<String, Integer> hMap = new HashMap<>();
		
		hMap.put("startRow", startRow);
		hMap.put("endRow", endRow);
		
		try {
			session = sqlSessionFactory.openSession();
			boardList = session.selectList("fileBoard_list", hMap);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return boardList;
	}

//	게시글 읽기
	public BoardDto read(int boardNumber) {
		BoardDto boardDto = null;
		
		try {
			session = sqlSessionFactory.openSession();
			session.update("fileBoard_read_count", boardNumber);
			boardDto = session.selectOne("fileBoard_read", boardNumber);
			session.commit();
		} catch(Exception e) {
			e.printStackTrace();
			session.rollback();
		} finally {
			session.close();
		}
		
		return boardDto;
	}

//	게시글 삭제하기
	public int delete(int boardNumber, String password) {
		
		int check = 0;
		
		Map<String,Object> hMap = new HashMap<>();
		hMap.put("boardNumber", boardNumber);
		hMap.put("password", password);
		
		try {
			session = sqlSessionFactory.openSession();
			check = session.delete("fileBoard_delete",hMap);
			session.commit();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return check;
	}

//	게시글 수정화면 가져오기
	public BoardDto updateView(int boardNumber) {
		BoardDto boardDto = null;
		
		try {
			session = sqlSessionFactory.openSession();
			boardDto = session.selectOne("fileBoard_read", boardNumber);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return boardDto;
	}

	public int update(BoardDto boardDto, int fileUp) {
		int value = 0;
		try {
			if (fileUp == 0) {	// 파일 수정을 하지 않았을 때..
				
				session = sqlSessionFactory.openSession();
				value = session.update("fileBoard_update", boardDto);
				session.commit();
				
			} else {	// 파일 수정을 했을 때. OR 파일을 삭제 했을 때.
				session = sqlSessionFactory.openSession();
				value = session.update("fileBoard_update_add_file", boardDto);
				session.commit();
			} 
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return value;
	}
	
//	다운로드
	public BoardDto select(int boardNumber) {
		BoardDto boardDto = null;
		
		try {
			session = sqlSessionFactory.openSession();
			boardDto = session.selectOne("fileBoard_read", boardNumber);
		} catch(Exception e) {
			e.printStackTrace();
			session.rollback();
		} finally {
			session.close();
		}
		
		return boardDto;
	}
}
