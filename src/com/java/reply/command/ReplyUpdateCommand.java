package com.java.reply.command;

import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.java.command.Command;
import com.java.reply.model.ReplyDao;

public class ReplyUpdateCommand implements Command {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		int bunho=Integer.parseInt(request.getParameter("bunho"));
		String reply=request.getParameter("value");

		logger.info(logMsg+bunho+": "+reply);

		int check=ReplyDao.getInstance().update(bunho, reply);

		logger.info(logMsg+"수정결과: "+check);

		if(check>0) {
			response.setContentType("application/txt;charset=utf-8;");
			String str=bunho+","+reply;
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("bunho", bunho);
			map.put("reply", reply);
			
			JSONObject obj = new JSONObject(map);
			
			PrintWriter out=response.getWriter();
			out.print(obj);
		}
		return null;
	}

}
