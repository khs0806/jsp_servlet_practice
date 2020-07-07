package com.java.board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.java.database.ConnectionProvider;
import com.java.database.JdbcUtil;

public class BoardDao {
	private static BoardDao instance = new BoardDao();
	
	public static BoardDao getInstance() {
		return instance;
	}

	public int insert(BoardDto boardDto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int value = 0;
		
		writeNumber(boardDto, conn);
		
		try {
			String sql = "insert into board values(board_board_number_seq.nextval,?,?,?,?,?,?,?,?,?,?)";
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
				
			pstmt.setString(1, boardDto.getWriter());
			pstmt.setString(2, boardDto.getSubject());
			pstmt.setString(3, boardDto.getEmail());
			pstmt.setString(4, boardDto.getContent());
			pstmt.setString(5, boardDto.getPassword());
				
//			Date date = boardDto.getWriteDate(); 
//			long time = date.getTime(); 
//			Timestamp ts = new Timestamp(time);
			pstmt.setTimestamp(6, new Timestamp(boardDto.getWriteDate().getTime()));
			pstmt.setInt(7, boardDto.getReadCount());
			pstmt.setInt(8, boardDto.getGroupNumber());
			pstmt.setInt(9, boardDto.getSequenceNumber());
			pstmt.setInt(10, boardDto.getSequenceLevel());
			
			value = pstmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn);
			JdbcUtil.close(pstmt);
		}
		
		
		return value;
	}
	public void writeNumber(BoardDto boardDto, Connection conn) {
		//그룹번호(부모), 글순서(자식), 글레벨(자식)
		int boardNumber = boardDto.getBoardNumber();		// 0
		int groupNumber = boardDto.getGroupNumber();		// 1
		int sequenceNumber = boardDto.getSequenceNumber();	// 0
		int sequenceLevel = boardDto.getSequenceLevel();	// 0
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			
			if(boardNumber == 0) {		// 부모글 디비 처리, groupNumber(그룹번호) 증가
				sql = "select max(group_number) from board";
				conn = ConnectionProvider.getConnection();
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
					
				if(rs.next()) {
					int max = rs.getInt(1);
					boardDto.setGroupNumber(max+1);
				}
			} else {					// 답글 디비 처리, sequenceNumber(글순서)와 sequenceLevel(글레벨) 증가
				sql = "update board set sequence_number = sequence_number + 1"
						+ "where group_number = ? and sequence_number > 0";
				conn = ConnectionProvider.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, groupNumber);
				pstmt.setInt(2, sequenceNumber);
				
				sequenceNumber++;
				sequenceLevel++;
			}
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
	}

	public int getCount() {
		int value = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select count(*) from board";
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) value = rs.getInt(1);
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		
		
		return value;
	}

	public ArrayList<BoardDto> getBoardList(int startRow, int endRow) {
		ArrayList<BoardDto> boardList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select * from (select rownum as rnum, a.* from" 
					+ " (select * from board order by group_number desc, sequence_number asc) a) b "
					+ " where b.rnum >= ? and b.rnum <= ?";
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				BoardDto boardDto = new BoardDto();
				boardList.add(boardDto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		
		
		return boardList;
	}
	
	
}