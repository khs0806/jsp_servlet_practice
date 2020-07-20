/**
 * 
 */
var arr=new Array();

function createXHR(){
   if(window.XMLHttpRequest){
      return new XMLHttpRequest;         
   }else{
      return new ActiveXObject("Microsoft.XMLHTTP");
   }
}

var xhr=null;
function sendRequest(method, url, param, callback){
	var httpMethod = method.toUpperCase();
	var httpUrl = url;
	var httpParams = (param==null || param=="") ? null:param;
	
	if (httpMethod == "GET" && httpParams != null){ //GET 방식일때
		httpUrl += "?" + httpParams;
	}
	
//	arr.push(httpMethod, httpUrl, httpParams);
	
	xhr = createXHR();
	xhr.open(httpMethod, httpUrl, true);
	//xhr.setRequestHeader~는 POST일때만 적용되고 GET이면 점프한다
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded"); // GET일때는 실행 안됨, POST만 실행
	xhr.setRequestHeader("Authorization", "KakaoAK 8c08273a21863da84621e6bb6aca71ee");	// 권한에 관한 설정
	xhr.send(httpMethod=="POST" ? httpParams : null); //GET이면 null, POST면 파라미터 갖음
	xhr.onreadystatechange = callback;
}