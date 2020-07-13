package com.java.member.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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


public class MemberDao { //Data Access Object
	// singleton pattern : 단 한개의 객체를 가지고 설계한다.
	private static MemberDao instance = new MemberDao();
	
	private static SqlSessionFactory sqlSessionFactory = SqlManager.getInstance();
	
	private SqlSession session;
	
	private MemberDao() {}
	
	public static MemberDao getInstance() {
		return instance;
	}

//	회원가입
	public int insert(MemberDto memberDto) {
		int value = 0;
		
		try {
			session = sqlSessionFactory.openSession();
			value = session.insert("member_insert", memberDto);
			session.commit();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return value;
	}
	
//	회원가입 시 ID 중복체크
	public int idCheck(String id) {
		int value = 0;
		
		try {
			session = sqlSessionFactory.openSession();
			String checkId = session.selectOne("member_id_check", id);
			
			if(checkId != null) value = 1;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return value;
	}

//	우편번호 목록 불러오기
	public List<ZipcodeDto> zipcodeReader(String checkDong) {
		List<ZipcodeDto> arrayList = null;
		
		try {
			session = sqlSessionFactory.openSession();
			arrayList = session.selectList("member_zipcode", checkDong);
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return arrayList;
	}

//	로그인
	public String loginCheck(String id, String password) {
		
		String value = null;
		
		try {
			HashMap<String, String> memberMap = new HashMap<String, String>();
			memberMap.put("id", id);
			memberMap.put("password", password);
			
			session = sqlSessionFactory.openSession();
			value = session.selectOne("member_login", memberMap);
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return value;
	}

//	회원정보화면 출력
	public MemberDto updateId(String id) {
		MemberDto memberDto = null;
		
		try {
			session = sqlSessionFactory.openSession();
			memberDto = session.selectOne("member_select", id);
			System.out.println("test    "+memberDto);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return memberDto;
	}
	
//	회원정보 수정
	public int update(MemberDto memberDto) {
		int value = 0;
		
		try {
			session = sqlSessionFactory.openSession();
			value = session.update("member_update", memberDto);
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return value;
	}

//	회원 탈퇴
	public int delete(String id, String password) {
		int value = 0;
		
		try {
			Map<String,String> hMap = new HashMap<>();
			hMap.put("id", id);
			hMap.put("password", password);
			
			session = sqlSessionFactory.openSession();
			value = session.delete("member_delete", hMap);
			session.commit();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return value;
	}
}