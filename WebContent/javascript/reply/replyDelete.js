/**
 * http://usejsdoc.org/
 */

function deleteToServer(bunho, root){
	//자바스크립트에서의 요청
	arr.push(bunho+", "+root);
	
	var url = root+"/reply/replyDelete.do";
	var param = "bunho="+bunho;
	sendRequest("GET", url, param, deleteFromServer);

}
function deleteFromServer(){
	//자바에서 한 응답
	if(xhr.readyState==4 && xhr.status==200){
		var bunho=xhr.responseText;
		var div=document.getElementById(bunho);

		var listDiv=document.getElementById("listAllDiv");
		listDiv.removeChild(div);
	}
//	arr.push(xhr.readyState);
//	alert(arr.join("\n"));
} 