package com.java.member.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.java.database.ConnectionProvider;
import com.java.database.JdbcUtil;

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
}
