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
	<c:if test="${check > 0}">
		<script type="text/javascript">
			alert("수정되었습니다.");
			location.href="${root}/board/list.do?pageNumber=${pageNumber}";
		</script>
	</c:if>
	<c:if test="${check == 0}">
		<script type="text/javascript">
			alert("수정 실패;");
			location.href="${root}/board/read.do?boardNumber=${boardNumber}&pageNumber=${pageNumber}";
		</script>
	</c:if>
</body>
</html>