<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table board="1">
		<tr>
			<td align="center" bgcolor="#DEDEDE" width="100">사번</td>
			<td align="center" bgcolor="#DEDEDE" width="100">이름</td>
			<td align="center" bgcolor="#DEDEDE" width="100">입사년도</td>
			<td align="center" bgcolor="#DEDEDE" width="100">직군</td>
			<td align="center" bgcolor="#DEDEDE" width="100">부서번호</td>
			<td align="center" bgcolor="#DEDEDE" width="100">부서명</td>
		</tr>
		
		<c:forEach var="sawon" items="${sawonList}" >
			<tr>
				<td width="100" align="center">${sawon.employee_id}</td>
				<td width="100" align="center">${sawon.first_name}</td>
				
				<td width="100" align="center">
					<fmt:formatDate value="${sawon.hire_date}" pattern="yyyy-MM-dd"/>
				</td>
				
				<td width="100" align="center">${sawon.job_id}</td>
				<td width="100" align="center">${sawon.department_id}</td>
				<td width="100" align="center">${sawon.department_name}</td>
			</tr>		
		</c:forEach>
	</table>
</body>
</html>