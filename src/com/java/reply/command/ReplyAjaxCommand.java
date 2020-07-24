package com.java.reply.command;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.java.command.Command;
import com.java.reply.model.ReplyDao;
import com.java.reply.model.ReplyDto;

public class ReplyAjaxCommand implements Command {
	
	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
		List<ReplyDto> list = ReplyDao.getInstance().getList();
		JSONArray jArray = new JSONArray();
		
		for (int i=0; i<list.size(); i++) {
			ReplyDto dto = list.get(i);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("bunho", dto.getBunho());
			map.put("reply", dto.getLine_reply());
			
			JSONObject obj = new JSONObject(map);
			jArray.add(obj);
		}
		
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(jArray);
		
		return null;
	}

}
