<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<c:set var="root" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div align="center">
	<table>
		<tr>
			<td>
				<a href="${root}/fileBoard/write.do">글쓰기</a>
			</td>
		</tr>
	</table>
</div>
<c:if test="${count==0 || boardList.size()==0}"> <!-- 게시물이 없을때 -->
	<div align="center">
		게시판에 저장된 글이 없습니다.
	</div>
</c:if>
<c:if test="${count > 0}">
	<div align="center">
		<!-- 게시글 -->
		<table border="1">
			<tr>
				<td align="center" width="50">번호</td>
				<td align="center" width="250">제목</td>
				<td align="center" width="70">작성자</td>
				<td align="center" width="100">작성일</td>
				<td align="center" width="50">조회수</td>
			</tr>
			
			<c:forEach var="board" items="${boardList}">
				<tr>
					<td width="50">${board.boardNumber}</td>
					<td width="350">
						<c:if test="${board.sequenceLevel > 0}">
							<c:forEach begin="1" end="${board.sequenceLevel}">
							&nbsp;
							</c:forEach>
							Re:
						</c:if>
						<a href="${root}/fileBoard/read.do?boardNumber=${board.boardNumber}&pageNumber=${currentPage}">
							${board.subject}
						</a>
					</td>
					<td width="90">${board.writer}</td>
					<td width="200">
						<fmt:formatDate value="${board.writeDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td align="center" width="50">${board.readCount}</td>
				</tr>
			</c:forEach>
		</table>
		<!-- 페이징 -->
		<div align="center">
			<%-- 	1.한페이지 당 게시물 수 : 10개
					2.총 페이지 수 : 10페이지 = 전체 게시물 수(100) / 페이지 당 게시물 수(10)
								     11페이지 = 전체 게시물 수(102) / 페이지 당 게시물 수(10)
					3.페이지번호 블럭 : 10 , [1][2][3][4][5]...[10]
									  요청페이지번호가 5이면 시작페이지번호 1, 끝페이지번호 10
									  15이면 시작페이지번호 11, 끝페이지 번호 20으로 설정되게 해야한다.
									  
									  int startPage = (int) ((currentPage - 1) / pageBlock) * pageBlock + 1;
									  int endPage = startPage + pageBlock - 1;
									  
					4.boardSize, currentPage, count
					5.		  
			 --%>
<%-- 			 <fmt:parseNumber var="pageCount" value="${count / boardSize + (count % boardSize == 0 ? 0 : 1)}" integerOnly="true"/> --%>
			 <c:set var="pageCount" value="${count / boardSize + (count % boardSize == 0 ? 0 : 1)}"/>
			 <c:set var="pageBlock" value="${5}"/>
			 
			 <fmt:parseNumber var="result" value="${(currentPage - 1) / pageBlock}" integerOnly="true" />
			 <c:set var="startPage" value="${result*pageBlock+1}"/>
			 <c:set var="endPage" value="${startPage+pageBlock-1}"/>
 
			 <c:if test="${endPage > pageCount}">
			 	<c:set var="endPage" value="${pageCount}"/>
			 </c:if>
			 
			 <c:if test="${startPage > pageBlock}">
			 	<a href="${root}/fileBoard/list.do?pageNumber=${startPage - 1}">[이전]</a>
			 </c:if>
			 
			 <c:forEach var="i" begin="${startPage}" end="${endPage}">
			 	<a href="${root}/fileBoard/list.do?pageNumber=${i}">[${i}]</a>
			 </c:forEach>
			 
			 <c:if test="${endPage < pageCount}">
			 	<a href="${root}/fileBoard/list.do?pageNumber=${endPage + 1}">[다음]</a>
			 </c:if>
			 
		</div>
	</div>
</c:if>
</body>
</html>