/**
 * http://usejsdoc.org/
 */
var root=null;

function selectToServer(bunho,requestRoot){
	root=requestRoot;

	var param="bunho="+bunho;
	var url=root+"/reply/replySelect.do?" + param;
	var urll=root+"/reply/replySelect.do";
	
	$.ajax({
		url:url,
		type:"get",
		dataType:"text",
		success:selectFromServer
	});

	//arr.push(url+","+param);
}

function selectFromServer(data){
	alert(data);
	var result = data.split(",");
	var bunho = result[0].trim();
	var reply = result[1].trim();
	
	var div = document.createElement("div");
	$(div).attr("id","up" + bunho);
	
	var inputText = document.createElement("input");
	$(inputText).attr("type","text").val(reply);
	
	var inputBtn = document.createElement("input");
	$(inputBtn).attr("type","button").val("수정");
	inputBtn.onclick=function(){
		updateToServer(bunho, inputText.value);
	}
	
	$(div).append(inputText, inputBtn);
	$("#" + bunho).append(div);

}

function updateToServer(bunho, value){
	var param="bunho="+bunho+"&value="+value;
	var url=root+"/reply/replyUpdate.do?" + param;
	
	$.ajax({
		url:url,
		type:"get",
		dataType:"text",
		success:updateFromServer
	})

}
function updateFromServer(data){
	var obj = $.parseJSON(data);
	var bunho = obj.bunho;
	var reply = obj.reply;
	alert(bunho + " " + reply);
	$("#" + bunho).children("span:eq(1)").text(reply);
	$("#up" + bunho).remove();
	
	//alert(arr.join("\n"));
} 