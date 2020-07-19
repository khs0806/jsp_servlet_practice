package com.java.reply.command;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.command.Command;
import com.java.reply.model.ReplyDao;
import com.java.reply.model.ReplyDto;

public class ReplySelectCommand implements Command {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub

		int bunho=Integer.parseInt(request.getParameter("bunho"));
		ReplyDto replyDto=ReplyDao.getInstance().select(bunho);

		logger.info(logMsg+replyDto);

		if(replyDto !=null) {
			String str=replyDto.getBunho()+","+replyDto.getLine_reply();

			response.setContentType("application/txt;charset=utf-8");
			PrintWriter out=response.getWriter();
			out.print(str);
		}

		return null;
	}

}
