package com.java.sawon.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.command.Command;
import com.java.sawon.model.SawonDao;
import com.java.sawon.model.SawonDto;

public class ListOkCommand implements Command {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
		String departmentName = request.getParameter("departmentName");
		logger.info(logMsg + departmentName);
		
		List<SawonDto> sawonList = SawonDao.getInstance().select(departmentName);
		logger.info(logMsg + sawonList.size());
		
		// List -> JSONArray -> JSP page 출력시 태그를 만들어야 함.
		// 우선 JSON 라이브러리 다운받아야하고, JSON형태의 코딩을 구현해야한다.
		// 여기서는 약식으로 그냥 JSP를 바로 던져주기만 한다.
		
		request.setAttribute("sawonList", sawonList);
		
		return "/WEB-INF/views/ajax/sawon/listOk.jsp";
	}

}
