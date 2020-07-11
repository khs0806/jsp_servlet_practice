<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<c:set var="root" value="${ pageContext.request.contextPath }"/>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="./../css/board/boardStyle.css">
<script type="text/javascript" src="${root}/javascript/board/board.js"></script>
</head>
<body>
	<div id="power">
		<form action="${root}/board/updateOk.do" method="post" 
			onsubmit="return boardCheck(this)">
			<input type="hidden" name="boardNumber" value="${boardDto.boardNumber}"/>
			<input type="hidden" name="pageNumber" value="${pageNumber}"/>
			<div>
				<label class="six">작성자</label>
				<p>${boardDto.writer}</p>
			</div>
			<div>
				<label class="six">제목</label>
				<input type="text" name="subject" value="${boardDto.subject}">
			</div>
			<div>
				<label class="six" style="height: 155px;">내용</label>
				<textarea name="content" rows="10" cols="50">${boardDto.content}</textarea>
			</div>
			<div>
				<label class="six">비밀번호</label>
				<input type="password" name="password" placeholder="수정할 비밀번호 입력">
			</div>
			<div class="bottom">
				<input type="submit" value="글수정">
				<input type="reset" value="다시작성">
				<input type="button" value="목록보기" onclick="location.href='${root}/board/list.do?pageNumber=${pageNumber}'">
			</div>
		</form>
	</div>
</body>
</html>