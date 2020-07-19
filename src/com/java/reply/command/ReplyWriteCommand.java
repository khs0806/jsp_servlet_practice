package com.java.reply.command;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.command.Command;
import com.java.reply.model.ReplyDao;
import com.java.reply.model.ReplyDto;

public class ReplyWriteCommand implements Command {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		String writeReply=request.getParameter("writeReply");
		String user_ip=request.getRemoteAddr();
		logger.info(logMsg+user_ip+writeReply);
		
		ReplyDto replyDto=new ReplyDto();
		replyDto.setLine_reply(writeReply);
		replyDto.setUser_ip(user_ip);
		
		int check=ReplyDao.getInstance().insert(replyDto);
		logger.info(logMsg+check);
		
		if(check>0) {
			//DB에서 가장 큰 번호 가져오기
			int bunho=ReplyDao.getInstance().getBunho();
			logger.info(logMsg+writeReply+", "+bunho);
			
			//jsp와 java는 jstl을 통해 request로 데이터 주고받을수 있었다.
			//java와 js는 그런게 없으므로 텍스트로 주고받야아한다.
			//행이 하나에 열이 여러개를 보내야하면 -> JSON
			//행도 여러개 열도 여러개를 보내야하면 -> JSONarray
			//우리는 열이 두개뿐이라 두개를 합쳐서 responseText하나로 보내고 js에서 split()을 이용하여 배열에 저장할것
			//보내는 방식은 response.setContetType("text");
			
			String str=bunho+","+writeReply+","+user_ip;	//JSON - SPRING
			response.setContentType("application/text;charset=utf-8");	//json이 넘어가면 application/json, 필터 안해주기 때문에 charset해줘야함
			PrintWriter pw=response.getWriter();
			
			pw.print(str);//소켓스트림 통해서 java에서 js단으로 넘어가는것
		}
		/*
		 * System.out.println("요청 프로토콜: "+request.getProtocol());
		 * System.out.println("클라이언트IP 주소: "+request.getRemoteAddr());
		 * System.out.println("클라이언트가 접속한 포트: "+request.getRemotePort());
		 * 
		 * 나중에 db에 컬럼추가로 ip나 작성시간 같은거도 해보고싶음
		 */
		return null;
	}

}
