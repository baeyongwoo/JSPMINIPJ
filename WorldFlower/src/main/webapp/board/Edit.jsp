<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <link href="https://fonts.googleapis.com/css2?family=Hi+Melody&family=Nanum+Pen+Script&display=swap" rel="stylesheet">
    <link href="/css/style.css" rel="stylesheet">
    <title>수정</title>
    <script type="text/javascript">
        function validateForm(form){
            if(form.title.value==""){
                alert("제목을 입력하세요");
                form.title.focus();
                return false;
            }
            if(form.fname.value=="no"){
                alert("꽃을 선택하세요");
                form.nname.focus();
                return false;
            }
            if(form.content.value==""){
                alert("내용을 입력하세요");
                form.content.focus();
                return false;
            }		
            //파일크기체크
            var inputFile = document.getElementById("file");
            var files = inputFile.files;       
            if(files[0].size>5*1024*1024 ){
                alert("파일크기는 5Mbyte를 초과할 수 없습니다.");
                return false;
            }      
        }
    </script>
    <script>
 // 썸네일 보이게
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
		img.setAttribute("src", "/upload/${ dto.fimgsrc}");
		img.setAttribute("width", "100%");
		$("#uploadImgDiv").prepend(img);
	}
	
	// 꽃을 선택했을 때 해당나라 나오게
	function getNationList(e){
		$("#optflower").hide();
		var fname=$("#selectFlower").val();
		$.ajax({
			url:"/getNationList.do",
			type:"get",
			data:{fname:fname},
			contentType:"application/json;charset=UTF-8",
			success:function(data){
				//alert("수신성공");
                $('#selectWorld').html(data.split(', ').map(function(nation) {
                    return '<option value="' + nation + '">' + nation + '</option>';
                }).join(''));
			},
			error: function( request, status, error ){
				alert("status : " + request.status + ", message : " + request.responseText + ", error : " + error);
			}
		});

	}
    function goList(e){
        location.href="/list.do";
     }
</script>
</head>
<body>
    <%@ include file = "/common/header.jsp" %>
   	<div style="width:95%;" class="mx-auto">   
    <form name="writeFrm" method="post" enctype="multipart/form-data" action="/edit.do" onsubmit="return validateForm(this);">
      	<input type="hidden" name="bno" value="${ param.bno }"/>
		<input type="hidden" name="prevOfile" value="${ dto.fimgname }"/>
		<input type="hidden" name="prevSfile" value="${ dto.fimgsrc }"/>
		<input type="hidden" name="fcode" value="${ dto.fcode }"/>
        
        <div class="manu" style="text-align: center; padding: 50px;">
            <h1>수정</h1>
            <div class="row">
                <div class="col-sm-6">
                    <label for="title" class="form-label">제목</label>
                    <div class="form-floating mb-3 mt-3">
                        <input type="text" name ="title" value="${ dto.title }">
                    </div>
                    <!-- 꽃 항목 추가 -->
                    <label for="flowername" class="form-label">꽃이름</label>
                    <div class="form-floating mb-3 mt-3">
						<select name="fname" class="form-select" aria-label="Default select example" id="selectFlower" onchange="getNationList(event)">
							<option id="optflower" value="${dto.fname }">${dto.fname }</option>
							<c:forTokens var="var" items="anemone,Azelea,barley_tree,carnation,RoseofSharon,rose,jasmine,Edelweiss,Sophora_japonica,lotus,maple" delims=",">
								<option id="optflower" value="${var}">${var}</option>
							</c:forTokens>
						</select>
                    </div>
                    <!-- 꽃 항목 추가 끝 -->
                        
                    <label for="worldname" class="form-label">나라이름</label>
                    <div class="form-floating mb-3 mt-3">
                    <input type="hidden" id="getNList" name="getNList"/>
						<select name="nname" class="form-select" aria-label="Default select example" id="selectWorld" ">
							<option>${dto.nname }</option>
						</select>
                    </div>
                    <label for="content" class="form-label">내용:</label>
                <textarea name="content" rows="10" cols="60">${ dto.content }</textarea>
                </div>
                <div class="col-sm-6">
                    <div id="uploadImgDiv">
                    	<c:choose>
                    		<c:when test="${empty dto.fimgsrc }">
                    			<img src="/img/logo.png" width="100%">
                    		</c:when>
                    		<c:otherwise>
                   				<img src="/upload/${ dto.fimgsrc }" width="100%">
                    		</c:otherwise>
                    	</c:choose>

                        <input type="file" name="fimgname" onchange="setThumbnail(event);"/>
                    </div>
                </div>
                <div class="container d-flex justify-content-center">    
                    <button type="submit" class="btn btn-success">수정완료</button>
                    <button type="reset" onclick="resetImg(event)" class="btn btn-warning">취소</button>
                    <button type="button" value="목록" onclick="goList(event)" class="btn btn-info">목록으로</button>
                </div>
            </div>
        </div>
    </form>
    </div>
    <%@ include file = "/common/footer.jsp" %>
</body>
    </html>