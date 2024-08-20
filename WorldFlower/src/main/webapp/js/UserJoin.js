/**
 * 
 */
console.log("js 호출")
function checkUserid(){
	
	let userid = $("#userid").val();
	$.ajax({
	           type:'post',
	           async:false, 
	           url:'http://localhost:8383/checkUserid.do',
	           dataType:'text',
	           data:{userid:userid},
	           success: function(data, textStatus) {
				console.log(userid);
	              
					if(userid==""){
						$('#message').empty();
						return false;
					} else{
						if(data === 'useable') {
						$('#message').text('사용할 수 있는 ID입니다.');   
		               } else {
						$('#message').text('이미 사용 중인 아이디입니다.');
						alert('이미 사용중인 아이디입니다. 다시 작성해주세요');
						return false;  
		               }
					}
	           },
	           error:function (data, textStatus) {
	               console.log('error');
	           }
	       })    //ajax
} //checkUserid end

//checkuUseremail
function checkUserEmail(){
	$('#emailAuth').hide();
	$('#emailLabel').hide();
	$('#emailCheck').hide();
	$('#checkBtn').hide();
	let email = $("#email").val();
	$.ajax({
	           type:'post',
	           async:false, 
	           url:'http://localhost:8383/checkEmail.do',
	           dataType:'text',
	           data:{email:email},
	           success: function(data, textStatus) {
				console.log(data);
	               if(	$('#email').val()==''){
						$('#messageEmail').empty();
				   }else{
						if(data === 'useable') {
						$('#messageEmail').text('사용 가능한 이메일입니다.');  
						$('#emailAuth').text('email코드 발송')
						$('#emailAuth').show();
		               } else {
						$('#messageEmail').text('이미 사용 중인 이메일입니다.'); 
						alert('이미 사용중인 이메일입니다. 다시 작성해주세요');                 
		               }
				   }
	           },
	           error:function (data, textStatus) {
	               console.log('error');
	           }
	       })    //ajax
} //checkUserEmail end

//sendEmail
function sendEmail(){
	let email = $("#email").val();
	$.ajax({
		           type:'post',
		           async:false, 
		           url:'http://localhost:8383/SendEmailAuth.do',
		           dataType:'text',
		           data:{email:email},
		           success: function(data, textStatus) {
						alert("해당 이메일로 발송되었습니다 확인해주세요");
						//emailAuthCheck(data);		
						$('#emailAuth').text('email코드 재발송')
						$('#emailLabel').show();
						$('#emailCheck').show();
						$('#checkBtn').show();		               
		           },
		           error:function (data, textStatus) {
		               console.log('error');
		           }
		       })    //ajax
}//sendEmail end


function emailAuthCheck(){
	let emailCheck = $("#emailCheck").val();
	$.ajax({
		           type:'post',
		           async:false, 
		           url:'http://localhost:8383/checkCode.do',
		           dataType:'text',
		           data:{emailCheck:emailCheck
				   },
		           success: function(data, textStatus) {
					if(data === 'access') {
						$('#messageEmail').text('인증되었습니다');  
						$('#emailCheck').hide();
						$('#emailLabel').hide();
						$('#emailAuth').hide();
						$('#checkBtn').hide();
						$('#email').prop('readonly', true);
						
					} else {
						$('#messageEmail').text('인증실패하였습니다.');
						
										
		               }
		           },
		           error:function (data, textStatus) {
		               console.log('error');
		           }
		       })    //ajax
}//emailAuthCheck end



    function validateForm(form){
    	console.log('val 호출');

        if(form.nickname.value==""){
            alert("닉네임을 입력하세요");
            form.nickname.focus();  
            return false;
        }
        if(form.pwd.value==""){  
            alert("비밀번호를 입력하세요");
            form.pwd.focus();
            return false;
        }
        if(!validatePassword(form.pwd.value)){
            alert("비밀번호는 8자 이상, 영문, 숫자, 특수문자(@$!%*#?&)를 포함해야 합니다.");
            form.pwd.focus();
            return false;
        }
        if(form.pwdch.value==""){  
            alert("비밀번호 확인을 입력하세요");
            form.pwdch.focus();
            return false;
        }
        if(form.pwdch.value!=form.pwd.value){
            alert("비밀번호와 일치하지 않습니다.");
            form.pwdch.focus();
            return false;
        }
		if($('#message').text()=='이미 사용 중인 아이디입니다.'){
			alert('이미 사용중인 아이디입니다. 다시 작성해주세요');
			form.nickname.focus();
			return false; 
		}

    }

    function validatePassword(password) {
        const passwordRegex = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
        return passwordRegex.test(password);
    }
	function removeECheck(){
		$('#messageEmail').empty();
		$('#emailCheck').hide();
		$('#emailLabel').hide();
		$('#emailAuth').hide();
		$('#checkBtn').hide();
	}
	
    $(document).ready(function(){
        $('#pwd').on('input', function() {
            const strongPasswordMessage = $('.strongPassword-message');
            if(validatePassword($(this).val())){
                strongPasswordMessage.addClass('hide');
            } else {
                strongPasswordMessage.removeClass('hide');
            }
        });
		
		$('#message').on('change',function(){
			$('message').text('');
		})
    });
