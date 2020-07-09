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
	location.href = url;
}
function delFun(root, boardNumber, pageNumber){
	var url = root + "/board/delete.do?boardNumber=" + boardNumber + "&pageNumber=" + pageNumber;
	//alert(url);
	location.href = url;
	
	// 밑에 로직은 자바스크립트단에서 바로 OkCommand로 보내줘서 바로 삭제되게 한다.
	// 이 경우에는 password를 따로 넘겨줄 수 없기 때문에 중요하지 않은거 삭제할 때 쓰면된다.(물론 넘기는방법도 있긴있음)
// 	var value = confirm("삭제 하시겠습니까?"); 
// 	if (value) {
// 		var url = root + "/board/deleteOk.do?boardNumber=" + boardNumber + "&pageNumber=" + pageNumber;
// 		location.href = url;
// 	} else {
// 		alert('삭제취소오키');
// 	}
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
			<input type="button" value="글삭제" onclick="delFun('${root}','${boardDto.boardNumber}','${pageNumber}')"/>
			<input type="button" value="답글" onclick="replyFun('${root}','${boardDto.boardNumber}','${boardDto.groupNumber}','${boardDto.sequenceNumber}','${boardDto.sequenceLevel}')"/>
			<input type="button" value="글목록" onclick="location.href='${root}/board/list.do'"/>
		</div>
	</div>
</body>
</html>