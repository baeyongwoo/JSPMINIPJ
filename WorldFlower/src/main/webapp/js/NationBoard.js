// 수정 페이지로 이동하는 함수



console.log('nationBoard');
function goEdit(nname) {
	alert("개발중입니다.");
}



function setThumbnail(e){
	var reader = new FileReader();
	
	reader.onload = function(e){
		$("#uploadImgDiv img").remove();
		var img = document.createElement("img");
		img.setAttribute("src", e.target.result);
		img.setAttribute("width", "100%");
		$("#uploadImgDiv").prepend(img);
	};
	
	reader.readAsDataURL(e.target.files[0]);
}

// 리셋 눌렀을 때 로고이미지 다시 나오게
function resetImg(e){
	$("#uploadImgDiv img").remove();
	var img = document.createElement("img");
	img.setAttribute("src", "img/logo.png");
	img.setAttribute("width", "100%");
	$("#uploadImgDiv").prepend(img);
}


function checkselect(){
	let selectElement = document.getElementById("nlocation");
	    if (selectElement.value === "null") {
	        alert("대륙을 선택해주세요");
			selectElement.focus();
			return false;
	    } else {
	    }
	
}

