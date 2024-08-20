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
    <link href="css/style.css" rel="stylesheet">
    <title>글쓰기</title>
    <script>
    	// 내용물 등록
		function validateForm(form){
		    if(form.title.value==""){
		        alert("제목을 입력하세요");
		        form.title.focus();
		        return false;
		    }
		    if($("#selectFlower").val()=="no"){
		        alert("꽃을 선택해주세요");  
	        	return false;
	    	}
		    if(form.content.value==""){
		        alert("내용을 입력하세요");
		        form.content.focus();  
		        return false;
		    }
		}
		
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
			$('#selectWorld').empty();
			$('#selectWorld').prepend("<option>꽃을 먼저 골라주세요.</option>");
			var img = document.createElement("img");
			img.setAttribute("src", "img/logo.png");
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
					alert("통신 중 에러가 발생하였습니다.");
/* 					alert("status : " + request.status + ", message : " + request.responseText + ", error : " + error); */
				}
			});

		}
		function goList(e){
			location.href="/list.do";
		}
		
    </script>
</head>
<body>
    <%@ include file="/common/header.jsp" %>
    <div style="width:95%;" class="mx-auto">
    <form action="/write.do" method="post" enctype="multipart/form-data" onsubmit="return validateForm(this);">
        <div class="manu" style="text-align: center; padding: 50px;">
            <h1>등록</h1>
            <div class="row">
                <div class="col-sm-6" style="float: left;">
                    <label for="title" class="form-label">제목</label>
                    <div class="form-floating mb-3 mt-3">
                        <input type="text" name="title" id="title" placeholder="제목">
                    </div>
                    
                    <!-- 꽃 항목 추가 -->
                    <label for="flowername" class="form-label">꽃이름</label>
                    <div class="form-floating mb-3 mt-3">
						<select name="fname" class="form-select" aria-label="Default select example" id="selectFlower" onchange="getNationList(event)">
							<option id="optflower" value="no">꽃을 골라주세요</option>
							<c:forTokens var="var" items="anemone,Azelea,barley_tree,carnation,RoseofSharon,rose,jasmine,Edelweiss,Sophora_japonica,lotus,maple" delims=",">
								<option id="optflower" value="${var}">${var}</option>
							</c:forTokens>
						</select>
                    </div>
                    <!-- 꽃 항목 추가 끝 -->
                        
                    <label for="worldname" class="form-label">나라이름</label>
                    <div class="form-floating mb-3 mt-3">
                    <input type="hidden" id="getNList" name="getNList"/>
						<select name="nname" class="form-select" aria-label="Default select example" id="selectWorld">
							<option>꽃을 먼저 골라주세요.</option>
						</select>
                    </div>
                    
                  
                    <label for="content" class="form-label">내용:</label>
               		<textarea name="content" id="content" rows="10" cols="60" placeholder="내용"></textarea>
                </div>
                <div class="col-sm-6">
                    <div id="uploadImgDiv">
                       	<img src="img/logo.png" width="100%">
                        <input type="file" name="fimgname" onchange="setThumbnail(event);"/>
                    </div>
                </div>
                <div class="container d-flex justify-content-center">    
                    <button type="submit" class="btn btn-success">등록하기</button>
                    <button type="reset" onclick="resetImg(event)" class="btn btn-warning">취소</button>
                    <button type="button" onclick="goList(event)" class="btn btn-info">목록으로</button>
                </div>
            </div>
        </div>
    </form>
    </div>
	<%@ include file = "/common/footer.jsp" %>
</body>
</html>