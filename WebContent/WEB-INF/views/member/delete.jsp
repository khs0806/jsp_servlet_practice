<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<c:set var="root" value="${ pageContext.request.contextPath }"/>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="../../../index.jsp"/>
	<form action ="${root}/memeber/deleteOk.do" method="post">
		<table>
			<tr>
				<td>회원아이디</td>
				<td><input type="text" name="id" value="${id}" readonly="readonly"/></td>
			<tr>
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="password"/></td>
			<tr>
			<tr>
				<td colspan="2" aling="center">
					<input type="submit" value="회원탈퇴"/>
					<input type="reset" value="취소"/>
				</td>
			<tr>
		</table>
	</form>		
</body>
</html>