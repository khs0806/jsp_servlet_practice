<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>'
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>'

<!DOCTYPE html>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link type="text/css" rel="stylesheet" href="${root}/css/board/boardStyle.css">
<script type="text/javascript">
function replyFun(root, boardNumber, groupNumber,sequenceNumber, sequenceLevel){
	var url = root + "/board/write.do?boardNumber=" + boardNumber;
		url += "&groupNumber=" + groupNumber;
		url += "&sequenceNumber=" + sequenceNumber;
		url += "&sequenceLevel=" + sequenceLevel;
	alert(url);
	location.href = url;
}

</script>
</head>
<body>
	<div id="power">
	${boardDto.boardNumber}, ${boardDto.groupNumber}, ${boardDto.sequenceNumber}, ${boardDto.sequenceLevel}
		<div>
			<label class="six">글번호</label>
			<p>${boardDto.boardNumber}</p>
		</div>
		<div>
			<label class="six">작성자</label>
			<p>${boardDto.writer}</p>
		</div>
		<div>
			<label class="six">제목</label>
			<p>${boardDto.subject}</p>
		</div>
		<div>
			<label class="six">조회수</label>
			<p>${boardDto.readCount}</p>
		</div>
		<div>
			<label class="six" style="height: 151px;">내용</label>
			<textarea name="content" rows="10" cols="50" readonly="readonly">${boardDto.content}</textarea>
		</div>
		<div>
			<label class="six">작성일</label>
			<p><fmt:formatDate value="${boardDto.writeDate}" pattern="yyyy-MM-dd hh:mm"/></p>
		</div>
		<div class="bottom">
			<input type="button" value="글수정" onclick=""/>
			<input type="button" value="글삭제" onclick=""/>
			<input type="button" value="답글" onclick="replyFun('${root}','${boardDto.boardNumber}','${boardDto.groupNumber}','${boardDto.sequenceNumber}','${boardDto.sequenceLevel}')"/>
			<input type="button" value="글목록" onclick=""/>
		</div>
	</div>
</body>
</html>