/**
 * 
 */

var arr = new Array();
var root=null;

function writeToServer(requestRoot){
	var writeReply = document.getElementById("writeReply").value;
	root = requestRoot;
	//arr.push(root+", "+writeReply);
	
	var url = root+"/reply/replyWrite.do";
	var param = "writeReply="+writeReply;
	sendRequest("POST", url, param, writeFromServer);
	
}

function writeFromServer(){
	if(xhr.readyState==4 && xhr.status==200){
		//arr.push("마지막출력 : "+xhr.responseText);
		
		var result=xhr.responseText.split(",");
		
		var bunho=result[0].trim();	//혹시 모를 양옆 공백제거
		var reply=result[1].trim();
		var userIp=result[2];
		document.getElementById("writeReply").value="";
		
		var listAllDiv=document.getElementById("listAllDiv");
		var div=document.createElement("div");
		
		div.className="replyDiv";
		div.id=bunho;
		
		
		var spanBunho=document.createElement("span");
		spanBunho.innerText=bunho;
		spanBunho.className="cssBunho";
		
		var spanReply=document.createElement("span");
		spanReply.innerText=reply;
		spanReply.className="cssReply";
		
		var spanIp=document.createElement("span");
		spanIp.innerText=userIp;
		spanIp.className="cssIp";
		
		var spanUpDel=document.createElement("span");
		spanUpDel.className="cssUpDel";
		
		var aDelete=document.createElement("a");
		//aDelete.href="javascript:deleteToServer('"+bunho+"', '"+root+"')";
		//aDelete.style="color:blue;cursor:pointer;";
		aDelete.href='#';
		aDelete.innerHTML="삭제 &nbsp;";
		aDelete.onclick=function(){
			deleteToServer(bunho,root);
		};
		
		
		var aUpdate=document.createElement("a");
		aUpdate.href='#';
		aUpdate.innerHTML="수정";
		aUpdate.onclick=function(){
			selectToServer(bunho,root);
		};
		spanUpDel.appendChild(aDelete);
		spanUpDel.appendChild(aUpdate);
		
		div.appendChild(spanBunho);
		div.appendChild(spanIp);
		div.appendChild(spanReply);
		div.appendChild(spanUpDel);
		listAllDiv.appendChild(div);
		listAllDiv.insertBefore(div, listAllDiv.firstChild);
		//alert(arr.join("\n"));
	}
}