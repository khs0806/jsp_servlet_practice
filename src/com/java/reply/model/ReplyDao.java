package com.java.reply.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public List<ReplyDto> getList() {
		List<ReplyDto> replyList=null;
		
		try {
			session=sqlSessionFactory.openSession();
			replyList=session.selectList("reply_list");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		
		return replyList;
	}
	public int delete(int bunho) {
		int value=0;
		try {
			session=sqlSessionFactory.openSession();
			value=session.delete("reply_delete", bunho);
			session.commit();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		
		return value;
	}

	public ReplyDto select(int bunho) {
		
		ReplyDto replyDto=new ReplyDto();

		try {
			session=sqlSessionFactory.openSession();
			replyDto=session.selectOne("reply_select", bunho);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		return replyDto;
	}
	
	public int update(int bunho, String lineReply) {
		// TODO Auto-generated method stub
		int value=0;
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("bunho", bunho);
		map.put("line_reply", lineReply);
		try {
			session=sqlSessionFactory.openSession();
			value=session.update("reply_update", map);
			session.commit();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}

		return value;
	}
	
}
