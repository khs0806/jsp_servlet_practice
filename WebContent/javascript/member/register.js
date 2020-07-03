/**
 * 
 */

function createForm(obj){
	var str="";
	for (var i=0; i<obj.interest.length; i++){
		if(obj.interest[i].checked==true){
			str += obj.interest[i].value + ",";
		}
	}
	//alert(str);
	obj.resultInterest.value=str;
}

function idCheck(obj, root){
	if(obj.id.value == ""){
		alert("아이디를 입력하세요");
		obj.id.focus();
		return false;
	}
	var url = root + "/member/idCheck.do?id=" + obj.id.value;
	//console.log(url);
	window.open(url, "", "width=400, height=200");
}

function zipCode(root){
	var url = root + "/member/zipcode.do";
	//alert(url);
	window.open(url, "", "width=500, height=500, scrollbars=yes");
}