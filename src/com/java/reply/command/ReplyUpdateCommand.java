package com.java.reply.command;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.command.Command;
import com.java.reply.model.ReplyDao;

public class ReplyUpdateCommand implements Command {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		int bunho=Integer.parseInt(request.getParameter("bunho"));
		String lineReply=request.getParameter("value");

		logger.info(logMsg+bunho+": "+lineReply);

		int check=ReplyDao.getInstance().update(bunho, lineReply);

		logger.info(logMsg+"수정결과: "+check);

		if(check>0) {
			String str=bunho+","+lineReply;
			response.setContentType("application/txt;charset=utf-8;");
			PrintWriter out=response.getWriter();
			out.print(str);
		}
		return null;
	}

}
