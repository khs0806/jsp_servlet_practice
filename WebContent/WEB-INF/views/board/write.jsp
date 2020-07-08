<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="./../css/board/boardStyle.css">
<script type="text/javascript" src="${root}/javascript/board/board.js"></script>
</head>
<body>
	<div id="power">
		<div class="title">
			<span><a href="${root}/board/list.do">글목록</a></span>
		</div>
		<form action="${root}/board/writeOk.do" method="post" 
			onsubmit="return boardCheck(this)">
			${boardNumber}, ${groupNumber}, ${sequenceNumber}, ${sequenceLevel}
			<input type="hidden" name="boardNumber" value="${boardNumber}"/>
			<input type="hidden" name="groupNumber" value="${groupNumber}"/>
			<input type="hidden" name="sequenceNumber" value="${sequenceNumber}"/>
			<input type="hidden" name="sequenceLevel" value="${sequenceLevel}"/>
			<div>
				<label class="six">작성자</label>
				<input type="text" name="writer" style="vertical-align:middle;">
			</div>
			<div>
				<label class="six">제목</label>
				<input type="text" name="subject">
			</div>
			<div>
				<label class="six">이메일</label>
				<input type="text" name="email">
			</div>
			<div>
				<label class="six" style="height: 155px;">내용</label>
				<textarea name="content" rows="10" cols="50"></textarea>
			</div>
			<div>
				<label class="six">비밀번호</label>
				<input type="password" name="password">
			</div>
			<div class="bottom">
				<input type="submit" value="글쓰기">
				<input type="reset" value="다시작성">
				<input type="button" value="목록보기">
			</div>
		</form>
	</div>
</body>
</html>