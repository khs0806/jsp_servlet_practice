/**
 * 
 */

function toServer(root){
	var departmentName = document.getElementById("sawonForm").departmentName.value;
	arr.push(departmentName);
	if(departmentName != "부서 선택하세요."){
		
		url = root + "/sawon/listOk.do";
		params = "departmentName=" + departmentName;
		
		sendRequest("POST", url, params, resultProcess)
	}
}

function resultProcess(){
	if (xhr.readyState==4 && xhr.status==200){
		arr.push(xhr.responseText);
		var resultDisp = document.getElementById("resultDisp");
		resultDisp.innerHTML = xhr.responseText;
	}
	console.log(xhr.responseText);
}