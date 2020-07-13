package com.java.member.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.java.command.Command;
import com.java.member.model.MemberDao;
import com.java.member.model.MemberDto;

public class UpdateOkCommand implements Command {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
		HttpSession session = request.getSession();
		
		MemberDto memberDto = new MemberDto();
		memberDto.setNum(Integer.parseInt(request.getParameter("num")));
		memberDto.setId((String) session.getAttribute("id"));
		memberDto.setPassword(request.getParameter("password"));
		memberDto.setEmail(request.getParameter("email"));
		memberDto.setZipcode(request.getParameter("zipcode"));
		
		memberDto.setAddress(request.getParameter("address"));
		memberDto.setJob(request.getParameter("job"));
		memberDto.setMailing(request.getParameter("mailing"));
		memberDto.setInterest(request.getParameter("interest"));
		logger.info(logMsg + memberDto); // VO객체에서 toString을 명시안해도 내부적으로 불러온다.
		
		MemberDao dao = MemberDao.getInstance();
		int value = dao.update(memberDto);
		logger.info(logMsg + value);
		
		request.setAttribute("check", value);
		return "/WEB-INF/views/member/updateOk.jsp";
	}

}
