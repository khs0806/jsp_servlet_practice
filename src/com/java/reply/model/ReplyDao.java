package com.java.reply.model;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.java.myBatis.SqlManager;

public class ReplyDao {
	private static ReplyDao instance = new ReplyDao();
	private static SqlSessionFactory sqlSessionFactory = SqlManager.getInstance();
	private SqlSession session;
	
	public static ReplyDao getInstance() {
		return instance;
	}
	
	private ReplyDao() {}

	public int insert(ReplyDto replyDto) {
		int value = 0;
		
		session = sqlSessionFactory.openSession();
		value = session.insert("reply_writer", replyDto);
		session.commit();
		session.close();
		return value;
	}

	public int getBunho() {
		int value = 0;
		
		session = sqlSessionFactory.openSession();
		value = session.selectOne("reply_getBunho");
		
		session.close();
		
		return value;
	}
	
}
