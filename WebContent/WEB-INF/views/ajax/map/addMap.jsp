<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${root}/xhr/xhr.js"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=2992107a6cdd4a70cae5c448140c5fd1"></script>
<script type="text/javascript">
	var arr = new Array();
	function toServer(){
		var addr = document.getElementById("addr").value;
		arr.push("addr:"+addr);
// 		alert(arr.join("\n"));

		var url = "https://dapi.kakao.com/v2/local/search/address.json";
		var params = "query=" + addr;
		
		sendRequest("GET", url, params, fromServer);
	}
	function fromServer(){
		
		if (xhr.readyState==4 && xhr.status==200){
// 			arr.push("상태,응답코드:" + xhr.readyState + "," + xhr.status);
			console.log("응답텍스트" + xhr.responseText);
			processJson();
		}
	}
	function processJson(){
		var obj = JSON.parse(xhr.responseText);
		var y = obj.documents[0].y;
		var x = obj.documents[0].x;
		arr.push("x,y:" + x + "," + y)
		alert(arr.join("\n"));
		
		var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
		var options = { //지도를 생성할 때 필요한 기본 옵션
			center: new kakao.maps.LatLng(y, x), //지도의 중심좌표.
			level: 3 //지도의 레벨(확대, 축소 정도)
		};

		var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴
		var mapTypeControl = new kakao.maps.MapTypeControl();
		var zoomControl = new kakao.maps.ZoomControl();
		
		map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);
		map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
		
		// 마커가 표시될 위치입니다 
		var markerPosition  = new kakao.maps.LatLng(y, x); 

		// 마커를 생성합니다
		var marker = new kakao.maps.Marker({
		    position: markerPosition
		});

		// 마커가 지도 위에 표시되도록 설정합니다
		marker.setMap(map);
	}
</script>
</head>
<body>
	<h3>주소</h3>
	<input id="addr" type="text"/>
	<input type="button" value="주소검색" onclick="toServer()"/>
	
	<div id="map" style="width:500px;height:400px;"></div>
</body>
</html>