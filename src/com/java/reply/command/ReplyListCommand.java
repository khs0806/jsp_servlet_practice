package com.java.reply.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.command.Command;
import com.java.reply.model.ReplyDao;
import com.java.reply.model.ReplyDto;

public class ReplyListCommand implements Command {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// DB List
		
		List<ReplyDto> replyList=ReplyDao.getInstance().getList();
		logger.info(logMsg+replyList.size());
		
		
		request.setAttribute("replyList", replyList);
		return "/WEB-INF/views/ajax/reply/list.jsp";
	}

}
