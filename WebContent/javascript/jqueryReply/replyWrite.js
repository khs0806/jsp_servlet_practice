/**
 * 
 */

var arr = new Array();
var root=null;

function writeToServer(requestRoot){
	root = requestRoot;
	var writeReply = $("#writeReply").val();
	var params = "writeReply="+writeReply;
	var url = root + "/reply/replyWrite.do?" + params;
	
	$.ajax({
		url:url,
		type:"get",
		dataType:"text",
		success:writeProcess
	});
}

function writeProcess(data){
	var obj = $.parseJSON(data);
	var bunho = obj.bunho;
	var reply = obj.reply;
	
	$("#writeReply").val("");
	var newReplyText = "<div class='replyDiv' id='" + bunho + "'>";
	newReplyText += "<span class='cssBunho'>" + bunho + "</span>";
	newReplyText += "<span class='cssReply'>" + reply + "</span>";
	newReplyText += "<span class='cssUpDel'>";
	newReplyText += "<a href='javascript:deleteToServer("+bunho+", \""+root+"\")'>삭제</a>&nbsp;";
	newReplyText += "<a href='javascript:selectToServer("+bunho+", \""+root+"\")'>수정</a>";
	
	newReplyText += "</span>";
	newReplyText += "</div>";
	
	$("#listAllDiv").prepend(newReplyText);
}
