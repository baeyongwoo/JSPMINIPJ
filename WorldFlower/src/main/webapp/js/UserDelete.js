var selectedUserId = null;
var selectedCheckbox = null;

// 모달 창 표시 함수
function showPasswordModal(checkbox, userId) {
    selectedUserId = userId;
    selectedCheckbox = checkbox;
    $('#passwordModal').modal('show');
}

// 비밀번호 제출 함수
function submitPassword() {
    var password = document.getElementById('passwordInput').value;
    if (password) {
        console.log('아이디:', selectedUserId, '비밀번호:', password);
      //servlet으로
      $.ajax({
                    type:'post',
                    async:false, 
                    url:'http://localhost:8383/deleteUser.do',
                    dataType:'text',
                    data:{userid:selectedUserId, pwd:password},
                    success: function(data, textStatus) {
                     console.log('data' + data);
                     if(data==='success'){
                        alert('성공적으로 삭제되었습니다');
                        cancelModal();
                        location.reload();
                     }else{
                        alert('하루비밀번호 초과이거나, 비밀번호가 틀립니다.');
                        cancelModal();
                     }
                    },
                    error:function (data, textStatus) {
                     alert('비밀번호가 틀립니다.');
                     cancelModal();
                        console.log('error');
                    }
                })
      
        $('#passwordModal').modal('hide');
    } else {
        alert('비밀번호를 입력하세요.');
    }
}

// 모달 창 취소 함수
function cancelModal() {
    if (selectedCheckbox) {
        selectedCheckbox.checked = false;
    }
    $('#passwordModal').modal('hide');
}
