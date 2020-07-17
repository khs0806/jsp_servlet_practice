/**
 * 
 */

var arr = new Array();
function writeToServer(root){
	var writeReply = document.getElementById("writeReply").value;
	arr.push(root + " " + writeReply);
	
	var url = root + "/reply/replyWrite.do";
	var params = "writeReply=" + writeReply;
	
	sendRequest("POST", url, params, writeFromServer)
}

function writeFromServer(){
	if (xhr.readyState==4 && xhr.status==200){
		arr.push(xhr.responseText);
		
		alert(arr.join("\n"));
	}
}