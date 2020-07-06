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
	<c:choose>
		<c:when test="${check > 0}">
			<c:remove var="id" scope="session"/>
			<c:remove var="memberLevel" scope="session"/>
		
			<script type="text/javascript">
				alert("회원 탈퇴가 완료 되었습니다.");
				location.href="${root}/member/register.do";
			</script>
		</c:when>
		<c:when test="${check == 0}">
			<script type="text/javascript">
				alert("회원 탈퇴가 되지 않았습니다.");
				location.href="${root}/member/delete.do";
			</script>		
		</c:when>
	</c:choose>
</body>
</html>