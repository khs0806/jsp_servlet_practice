package com.java.sawon.model;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.java.myBatis.SqlManager;

public class SawonDao {
	private static SawonDao instance = new SawonDao();
	private static SqlSessionFactory sqlSessionFactory = SqlManager.getInstance();
	private SqlSession sqlSession;
	
	public static SawonDao getInstance() {
		return instance;
	}
	
	private SawonDao() {}

	public List<SawonDto> select(String departmentName) {
		List<SawonDto> sawonList = null;
		
		sqlSession = sqlSessionFactory.openSession();
		sawonList = sqlSession.selectList("sawonList", departmentName);
		sqlSession.close();
		return sawonList;
	}
	
	
}
