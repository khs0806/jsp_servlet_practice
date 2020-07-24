package com.java.sawon.command;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
	  	
	  	// JSON - DB에서 가져온 사원데이터를 JSON 형식의 데이터로 만들어줘서 반환해야한다.
	  	JSONArray jsonArray = new JSONArray();
	  	if (sawonList != null) {
	  		for (int i=0; i<sawonList.size(); i++) {
		  		SawonDto sawonDto = sawonList.get(i);
//		  		JSONObject obj = new JSONObject(map);
//		  		obj.put("employee_id",sawonDto.getEmployee_id());
//		  		obj.put("first_name",sawonDto.getFirst_name());
//		  		obj.put("job_id",sawonDto.getJob_id());
//		  		obj.put("department_id",sawonDto.getDepartment_id());
//		  		obj.put("department_name",sawonDto.getDepartment_name());
		  		
		  		HashMap<String, Object> map = new HashMap<>();
		  		map.put("employee_id",sawonDto.getEmployee_id());
		  		map.put("first_name",sawonDto.getFirst_name());
		  		map.put("job_id",sawonDto.getJob_id());
		  		map.put("department_id",sawonDto.getDepartment_id());
		  		map.put("department_name",sawonDto.getDepartment_name());
		  		
		  		JSONObject obj = new JSONObject(map);
		  		jsonArray.add(obj);
	  		}
	  		
	  		logger.info(logMsg + jsonArray);
	  		response.setContentType("application/text; charset=utf-8");
	  		PrintWriter out = response.getWriter();
	  		out.print(jsonArray);
	  	}
	  
	  	// =====JSON을 안썼을시에=====
	  	// List -> JSONArray -> JSP page 출력시 태그를 만들어야 함. // 우선 JSON 라이브러리 다운받아야하고,
	  	// JSON형태의 코딩을 구현해야한다. // 여기서는 약식으로 그냥 JSP를 바로 던져주기만 한다.
	  	// request.setAttribute("sawonList", sawonList);

	  	// return "/WEB-INF/views/ajax/sawon/listOk.jsp";
	  	
	  	return null;
		 
	}

}
