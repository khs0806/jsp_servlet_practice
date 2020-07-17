<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:set var="root" value="${pageContext.request.contextPath}"/>
	
	<h3>사원 테이블</h3>
	<a href="${root}/sawon/list.do">사원목록</a>
	<br><br>
	<h3>실시간 댓글</h3>
	<a href="${root}/reply/replyList.do">한줄답글시작</a>
</body>
</html>