package com.java.reply.command;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.command.Command;
import com.java.reply.model.ReplyDao;

public class ReplyDeleteCommand implements Command {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		int bunho = Integer.parseInt(request.getParameter("bunho"));
		
		logger.info(logMsg+bunho);
		
		int check=ReplyDao.getInstance().delete(bunho);
		logger.info(logMsg+ check);
		
		if(check>0) {
			response.setContentType("application/text;charset=utf-8");
			PrintWriter out=response.getWriter();
			out.print(bunho);
		}
		
		return null;
	}

}
