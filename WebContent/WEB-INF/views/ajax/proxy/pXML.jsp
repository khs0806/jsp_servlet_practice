<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<c:set var="root" value="${pageContext.request.contextPath }"/>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${root}/xhr/xhr.js"></script>
<script type="text/javascript">
	function toServer(root){
		var url = root + "/pXML.do";
		sendRequest("GET", url, null, fromServer);
	}
	function fromServer(){
// 		alert(xhr.readyState+","+xhr.status);
		if (xhr.readyState==4 && xhr.status==200){
// 			alert(xhr.responseXML);
			processXML();
		}
	}
	function processXML(){
		var xmlDoc = xhr.responseXML;
		var location = xmlDoc.getElementsByTagName("location");
// 		alert(location.length);
		
		var titleWf = xmlDoc.getElementsByTagName("wf");
		document.getElementById("titleWf").innerHTML = titleWf[0].childNodes[0].nodeValue;
		
		var city = location[1].getElementsByTagName("city");
		document.getElementById("city").innerText = city[0].firstChild.nodeValue;
		
		var data = location[1].getElementsByTagName("data");
		var wf = data[1].getElementsByTagName("wf");
		document.getElementById("wf").innerText = wf[0].firstChild.nodeValue;
	}
	
</script>
</head>
<body>
	<input type="button" value="오늘의 날씨" onclick="toServer('${root}')"/>
	<div>
		<span id="titleWf" style="color:red;"></span><br/>
		<span id="city" style="color:blue;"></span><br/>
		<span id="wf"></span><br/>
	</div>
</body>
</html> 