/**
 * 
 */
function ListToServer(root){
	$.getJSON(root + "/reply/replyAjax.do", function(data){
		var str = '';
		$(data).each(function(){
			str += "<div class='replyDiv' id='" + this.bunho + "'>";
			str += "<span class='cssBunho'>" + this.bunho + "</span>";
			str += "<span class='cssReply'>" + this.reply + "</span>";
			str += "<span class='cssUpDel'>";
			str += "<a href='javascript:deleteToServer("+this.bunho+", \""+root+"\")'>삭제</a>&nbsp;";
			str += "<a href='javascript:selectToServer("+this.bunho+", \""+root+"\")'>수정</a>";
			str += "</span>";
			str += "</div>";
		});
		$("#listAllDiv").html(str);
	});
}
