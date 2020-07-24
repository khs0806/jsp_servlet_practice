<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${root}/xhr/jquery.js"></script>
<script type="text/javascript" src="${root}/xhr/xhr.js"></script>
<script type="text/javascript" src="${root}/javascript/jqueryReply/replyWrite.js"></script>
<script type="text/javascript" src="${root}/javascript/jqueryReply/replyDelete.js"></script>
<script type="text/javascript" src="${root}/javascript/jqueryReply/replyUpdate.js"></script>
<script type="text/javascript" src="${root}/javascript/jqueryReply/replyList.js"></script>
<script>
	$(document).ready(function(){
		ListToServer('${root}');
	});
</script>
<style>
	.replyDiv {
		margin:5px 0px;
	}
	
	.replyDiv > span {
		margin:10px;
	}
	
</style>
</head>
<body>
	<div>실시간 댓글이 가능합니다.</div>
	<br><br>
	
	<div>
		<input type="text" id="writeReply" name="write" size="50"/>
		<input type="button" value="한줄댓글" onclick="writeToServer('${root}')"/>
	</div>
	<div id="listAllDiv">
		<!-- 실시간 댓글 -->
	</div>
</body>
</html>