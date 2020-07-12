package com.java.fileBoard.command;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.java.fileBoard.model.BoardDao;
import com.java.fileBoard.model.BoardDto;
import com.java.command.Command;

public class UpdateOkCommand implements Command {

	@Override
	public String proRequest(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("utf-8");
		
		FileItemFactory factory = new DiskFileItemFactory();		// 파일보관 객체
		ServletFileUpload upload = new ServletFileUpload(factory);	// 요청처리 객체
		List<FileItem> list = upload.parseRequest(request);			// 요청된 파라미터들을 전부 list에 담는다.
		
		Iterator<FileItem> iter = list.iterator();
		BoardDto boardDto = new BoardDto();
		Map<String, String> dataMap = new HashMap<>();
		
		int fileUp = 0;
		String fileDel = "";
		
		while(iter.hasNext()) {
			FileItem fileItem = iter.next();						// fileItem에 list의 파라미터들을 iter로 담는다.
			
			if(fileItem.isFormField()) {							// fileItem에 담긴 파라미터가 바이너리인지 문자열인지 구분한다.
				
				String name = fileItem.getFieldName();					// 위에 분기 방식을 Map방식으로 간편하게 처리한다.
				String value = fileItem.getString("utf-8");
				logger.info(logMsg + name + "," + value);
				
				dataMap.put(name, value);							
				
			} else {
				if (fileItem.getFieldName().equals("file")) {
					
					if (fileItem.getName() == null || fileItem.getName().equals("")) continue;
					fileDel = fileItem.getFieldName();
					upload.setFileSizeMax(1024*1024*10);	// 파일 최대 크기 byte * kb * mb * gb
					String fileName = System.currentTimeMillis() + "_" + fileItem.getName();
					
					// 내 서버 절대경로
					String dir = "C:\\Users\\김현수\\Desktop\\khsworkspace\\java_workspace\\MVCHomePage\\WebContent\\pds\\";
					File file = new File(dir, fileName);
					
					BufferedInputStream bis = null;
					BufferedOutputStream bos = null;
					
					try {	// 파일을 서버에 저장 하는 부분
						bis = new BufferedInputStream(fileItem.getInputStream(), 1024);
						bos = new BufferedOutputStream(new FileOutputStream(file), 1024);
						while (true) {
							int data = bis.read();
							if(data == -1) break;
							
							bos.write(data);
						}
						bos.flush();
					} catch(IOException e) {
						e.printStackTrace();
					} finally {
						if(bis != null) bis.close();
					}
					
					boardDto.setFileName(fileName);
					boardDto.setFileSize(fileItem.getSize());
					boardDto.setPath(file.getAbsolutePath());
					fileUp = 1;
				}
			}
		}
		
		if (dataMap.get("fileDelete").equals("1") && fileDel.equals("")) {
			boardDto.setFileName(null);
			boardDto.setFileSize(0);
			boardDto.setPath(null);
			fileUp = 1;
		}
		
		boardDto.setSubject(dataMap.get("subject"));
		boardDto.setContent(dataMap.get("content"));
		boardDto.setPassword(dataMap.get("password"));
		boardDto.setBoardNumber(Integer.parseInt(dataMap.get("boardNumber")));
		logger.info(boardDto.toString());
		
		BoardDao dao = BoardDao.getInstance();
		int check = dao.update(boardDto, fileUp);
		logger.info(logMsg + "check : " + check);
		
		request.setAttribute("check", check);
		request.setAttribute("boardNumber", dataMap.get("boardNumber"));
		request.setAttribute("pageNumber", dataMap.get("pageNumber"));
		
		return "/WEB-INF/views/fileBoard/updateOk.jsp";
	}

}
