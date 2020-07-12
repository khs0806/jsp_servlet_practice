package com.java.fileBoard.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.java.database.ConnectionProvider;
import com.java.database.JdbcUtil;

public class BoardDao {
	private static BoardDao instance = new BoardDao();
	
	public static BoardDao getInstance() {
		return instance;
	}
	
	// 게시글 작성하기
	public int insert(BoardDto boardDto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int value = 0;
		String sql = null;
		
		writeNumber(boardDto, conn);
		
		try {
			if (boardDto.getFileSize() == 0) {	// 파일 첨부가 되지 않았을 경우에 DB 등록 
				sql = "insert into board (board_number, writer, subject, content, password, "
						+ "write_date, read_count, group_number, sequence_number, sequence_level) "
						+ "values(board_board_number_seq.nextval,?,?,?,?,?,?,?,?,?)";
				conn = ConnectionProvider.getConnection();
				pstmt = conn.prepareStatement(sql);
					
				pstmt.setString(1, boardDto.getWriter());
				pstmt.setString(2, boardDto.getSubject());
				pstmt.setString(3, boardDto.getContent());
				pstmt.setString(4, boardDto.getPassword());
				pstmt.setTimestamp(5, new Timestamp(boardDto.getWriteDate().getTime()));
				pstmt.setInt(6, boardDto.getReadCount());
				pstmt.setInt(7, boardDto.getGroupNumber());
				pstmt.setInt(8, boardDto.getSequenceNumber());
				pstmt.setInt(9, boardDto.getSequenceLevel());
				
			} else {							// 파일첨부가 됬을 경우에 DB 등록
				sql = "insert into board (board_number, writer, subject, content, password, "
						+ "write_date, read_count, group_number, sequence_number, sequence_level, "
						+ "file_name, path, file_size) "
						+ "VALUES (board_board_number_seq.nextval,?,?,?,?,?,?,?,?,?,?,?,?)";
				conn = ConnectionProvider.getConnection();
				pstmt = conn.prepareStatement(sql);
					
				pstmt.setString(1, boardDto.getWriter());
				pstmt.setString(2, boardDto.getSubject());
				pstmt.setString(3, boardDto.getContent());
				pstmt.setString(4, boardDto.getPassword());
				pstmt.setTimestamp(5, new Timestamp(boardDto.getWriteDate().getTime()));
				pstmt.setInt(6, boardDto.getReadCount());
				pstmt.setInt(7, boardDto.getGroupNumber());
				pstmt.setInt(8, boardDto.getSequenceNumber());
				pstmt.setInt(9, boardDto.getSequenceLevel());
				pstmt.setString(10, boardDto.getFileName());
				pstmt.setString(11, boardDto.getPath());
				pstmt.setLong(12, boardDto.getFileSize());
			}
			
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
						+ " where group_number = ? and sequence_number > ?";
				conn = ConnectionProvider.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, groupNumber);
				pstmt.setInt(2, sequenceNumber);
				pstmt.executeUpdate();
				
				boardDto.setSequenceNumber(++sequenceNumber);
				boardDto.setSequenceLevel(++sequenceLevel);
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
				boardDto.setBoardNumber(rs.getInt("board_number"));
				boardDto.setWriter(rs.getString("writer"));
				boardDto.setSubject(rs.getString("subject"));
				boardDto.setContent(rs.getString("content"));
				
				boardDto.setPassword(rs.getString("password"));
				boardDto.setWriteDate(new Date(rs.getTimestamp("write_date").getTime()));
				boardDto.setReadCount(rs.getInt("read_count"));
				boardDto.setGroupNumber(rs.getInt("group_number"));			// 답글 작성시 필요한 3가지 변수
				boardDto.setSequenceNumber(rs.getInt("sequence_number"));	// group number, sequence number
				boardDto.setSequenceLevel(rs.getInt("sequence_level"));		// sequence level 이 3가지로 본문인지 답글인지 판단.
				System.out.println(boardDto);
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

	public BoardDto read(int boardNumber) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardDto boardDto = null;
		
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			String sqlUpdate = "update board set read_count = read_count + 1"
							  +" where board_number = ?";
			pstmt = conn.prepareStatement(sqlUpdate);
			pstmt.setInt(1, boardNumber);
			int value = pstmt.executeUpdate();
			
			if (value > 0) JdbcUtil.close(pstmt);
			
			String sqlSelect = "select * from board where board_number = ?";
			pstmt = conn.prepareStatement(sqlSelect);
			pstmt.setInt(1, boardNumber);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				boardDto = new BoardDto();
				boardDto.setBoardNumber(rs.getInt("board_number"));
				boardDto.setWriter(rs.getString("writer"));
				boardDto.setSubject(rs.getString("subject"));
				boardDto.setContent(rs.getString("content"));
				
				boardDto.setPassword(rs.getString("password"));
				boardDto.setWriteDate(new Date(rs.getTimestamp("write_date").getTime()));
				boardDto.setReadCount(rs.getInt("read_count"));
				boardDto.setGroupNumber(rs.getInt("group_number"));
				boardDto.setSequenceNumber(rs.getInt("sequence_number"));
				boardDto.setSequenceLevel(rs.getInt("sequence_level"));
				
				boardDto.setFileName(rs.getString("file_name"));
				boardDto.setPath(rs.getString("path"));
				boardDto.setFileSize(rs.getLong("file_size"));
				
			}
			conn.commit();
			
		} catch(Exception e) {
			e.printStackTrace();
			JdbcUtil.rollBack(conn);
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		
		return boardDto;
	}

	public int delete(int boardNumber, String password) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		int check = 0;
		
		try {
			String sql = "delete from board where board_number = ? and password = ?";
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, boardNumber);
			pstmt.setString(2, password);
			check = pstmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		
		return check;
	}

	public BoardDto updateView(int boardNumber) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardDto boardDto = null;
		
		try {
			conn = ConnectionProvider.getConnection();
			
			String sqlSelect = "select * from board where board_number = ?";
			pstmt = conn.prepareStatement(sqlSelect);
			pstmt.setInt(1, boardNumber);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				boardDto = new BoardDto();
				boardDto.setBoardNumber(rs.getInt("board_number"));
				boardDto.setWriter(rs.getString("writer"));
				boardDto.setSubject(rs.getString("subject"));
				boardDto.setContent(rs.getString("content"));
				
				boardDto.setPassword(rs.getString("password"));
				boardDto.setWriteDate(new Date(rs.getTimestamp("write_date").getTime()));
				boardDto.setReadCount(rs.getInt("read_count"));
				boardDto.setGroupNumber(rs.getInt("group_number"));
				boardDto.setSequenceNumber(rs.getInt("sequence_number"));
				boardDto.setSequenceLevel(rs.getInt("sequence_level"));
				
				boardDto.setFileName(rs.getString("file_name"));
				boardDto.setPath(rs.getString("path"));
				boardDto.setFileSize(rs.getLong("file_size"));
				
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		
		return boardDto;
	}

	public int update(BoardDto boardDto, int fileUp) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int value = 0;
		
		try {
			if (fileUp == 0) {	// 파일 수정을 하지 않았을 때..
				String sql = "update board set subject = ?, content = ?, password = ? where board_number = ?";
				conn = ConnectionProvider.getConnection();
				pstmt = conn.prepareStatement(sql);
				
			    pstmt.setString(1, boardDto.getSubject());
				pstmt.setString(2, boardDto.getContent());
				pstmt.setString(3, boardDto.getPassword());
				pstmt.setInt(4, boardDto.getBoardNumber());
				
				value = pstmt.executeUpdate();
			} else {	// 파일 수정을 했을 때. OR 파일을 삭제 했을 때.
				String sql = "update board set subject = ?, content = ?, password = ?"
						+ ", file_name = ?, path = ?, file_size = ? where board_number = ?";
				conn = ConnectionProvider.getConnection();
				pstmt = conn.prepareStatement(sql);
				
			    pstmt.setString(1, boardDto.getSubject());
				pstmt.setString(2, boardDto.getContent());
				pstmt.setString(3, boardDto.getPassword());
				pstmt.setString(4, boardDto.getFileName());
				pstmt.setString(5, boardDto.getPath());
				pstmt.setLong(6, boardDto.getFileSize());
				pstmt.setInt(7, boardDto.getBoardNumber());
				
				value = pstmt.executeUpdate();
			} 
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		
		return value;
	}
	
	public BoardDto select(int boardNumber) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardDto boardDto = null;
		
		try {
			conn = ConnectionProvider.getConnection();
			String sql = "select * from board where board_number = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNumber);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				boardDto = new BoardDto();
				boardDto.setFileName(rs.getString("file_name"));
				boardDto.setPath(rs.getString("path"));
				boardDto.setFileSize(rs.getLong("file_size"));
				
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		
		return boardDto;
	}
}
