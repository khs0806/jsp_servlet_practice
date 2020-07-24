/**
 * http://usejsdoc.org/
 */

function deleteToServer(bunho, root){
	
	var param = "bunho="+bunho;
	var url = root+"/reply/replyDelete.do?" + param;
	
	$.ajax({
		url:url,
		type:"get",
		dataType:"text",
		success:function(bunho){
			$("#" + bunho).remove();
		}
	});
}
