package com.java.member.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.java.database.ConnectionProvider;
import com.java.database.JdbcUtil;
import com.sun.media.jfxmedia.logging.Logger;


public class MemberDao { //Data Access Object
	// singleton pattern : 단 한개의 객체를 가지고 설계한다.
	private static MemberDao instance = new MemberDao();
	
	private MemberDao() {}
	
	public static MemberDao getInstance() {
		return instance;
	}

	public int insert(MemberDto memberDto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int value = 0;
		
		try {
			String sql = "insert into member values(member_num_seq.nextval, ?,?,?,?,?,?,?,?,?,?,?,?, sysdate)";
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, memberDto.getId());
			pstmt.setString(2, memberDto.getPassword());
			pstmt.setString(3, memberDto.getName());
			pstmt.setString(4, memberDto.getJumin1());
			pstmt.setString(5, memberDto.getJumin2());
			
			pstmt.setString(6, memberDto.getEmail());
			pstmt.setString(7, memberDto.getZipcode());
			pstmt.setString(8, memberDto.getAddress());
			pstmt.setString(9, memberDto.getJob());
			pstmt.setString(10, memberDto.getMailing());
			
			pstmt.setString(11, memberDto.getInterest());
			pstmt.setString(12, memberDto.getMemberLevel());
			
			value = pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		
		return value;
	}
	
	public int idCheck(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int value = 0;
		
		try {
			String sql = "select id from member where id = ?";
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) value = 1;
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		
		return value;
	}

	public List<ZipcodeDto> zipcodeReader(String checkDong) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<ZipcodeDto> arrayList = null;
		
		try {
			String sql = "select * from zipcode where dong = ?";
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, checkDong);
			rs = pstmt.executeQuery();
			
			arrayList = new ArrayList<ZipcodeDto>();
			while(rs.next()) {
				ZipcodeDto address = new ZipcodeDto();
				address.setZipcode(rs.getString("zipcode"));	// getString()의 파라미터는 sql 절에서 as로 닉네임 붙여준 값을 넣어줄수 있다.
				address.setSido(rs.getString("sido"));			// as 로 닉네임을 안붙여주면 열의 이름으로 대신한다.
				address.setGugun(rs.getString("gugun"));
				address.setDong(rs.getString("dong"));
				address.setRi(rs.getString("ri"));
				address.setBunji(rs.getString("bunji"));
				
				arrayList.add(address);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		return arrayList;
	}

	public String loginCheck(String id, String password) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String value = null;
		
		try {
			String sql = "select member_level from member where id = ? and password=?";
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			
			if(rs.next()) value = rs.getString("member_level");
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		
		return value;
	}

	public MemberDto updateId(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberDto memberDto = null;
		
		try {
			String sql = "select * from member where id = ?";
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				memberDto = new MemberDto();
				
				memberDto.setNum(rs.getInt("num"));
				memberDto.setId(rs.getString("id"));
				memberDto.setPassword(rs.getString("password"));
				memberDto.setName(rs.getString("name"));
				memberDto.setJumin1(rs.getString("jumin1"));
				
				memberDto.setJumin2(rs.getString("jumin2"));
				memberDto.setEmail(rs.getString("email"));
				memberDto.setZipcode(rs.getString("zipcode"));
				memberDto.setAddress(rs.getString("address"));
				memberDto.setJob(rs.getString("job"));
				
				memberDto.setInterest(rs.getString("interest"));
				memberDto.setMailing(rs.getString("mailing"));
				memberDto.setMemberLevel(rs.getString("member_level"));
				System.out.println("testtesttesttest");
//				Timestamp ts = rs.getTimestamp("register_date"); // SQL에서 날짜타입을 가져올 때 TimeStamp 타입으로 받고
//				long time = ts.getTime();						 // 다시 long 타입으로 시간을 받아 온뒤
//				Date date = new Date(time);						 // Date 타입으로 변환하여 dto에 set 해준다
				memberDto.setRegisterDate(new Date(rs.getTimestamp("register_date").getTime()));
				
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		
		return memberDto;
	}
	
	public int update(MemberDto memberDto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int value = 0;
		
		try {
			String sql = "update member set password=?, email=?, zipcode=?, address=?, job=?, mailing=?, interest=?"
					+ " where num = ?";
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			
		    pstmt.setString(1, memberDto.getPassword());
			pstmt.setString(2, memberDto.getEmail());
			pstmt.setString(3, memberDto.getZipcode());
			pstmt.setString(4, memberDto.getAddress());
			pstmt.setString(5, memberDto.getJob());
			pstmt.setString(6, memberDto.getMailing());
			pstmt.setString(7, memberDto.getInterest());
			pstmt.setInt(8, memberDto.getNum());
			
			value = pstmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		
		return value;
	}

	public int delete(String id, String password) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstmt = null;
		int value = 0;
		
		try {
			String sql = "delete from member where id = ? and password = ?";
			
			conn = ConnectionProvider.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			
			value = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt);
			JdbcUtil.close(conn);
		}
		
		return value;
	}
}