package com.java.reply.command;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.command.Command;
import com.java.reply.model.ReplyDao;
import com.java.reply.model.ReplyDto;

public class ReplyWriteCommand implements Command{
	
	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		String writeReply = request.getParameter("writeReply");
		logger.info(logMsg + writeReply);
		
		ReplyDto replyDto = new ReplyDto();
		replyDto.setLine_reply(writeReply);
		
		int check = ReplyDao.getInstance().insert(replyDto);
		logger.info(logMsg + check);
		
		if (check > 0) {
			int bunho = ReplyDao.getInstance().getBunho();
			logger.info(logMsg + writeReply + "," + bunho);
			
			// 자바스크립트에는 setAttribute로 값을 전달 못하기 때문에 텍스트(JSON)로 보내야한다.
			String str = bunho + "," + writeReply;	// spring 에선 JSON으로 보낼 것.
			response.setContentType("application/text; charset=utf-8");
			PrintWriter pw = response.getWriter();
			pw.print(str);
		}
		
		return null;
	}
	
}
