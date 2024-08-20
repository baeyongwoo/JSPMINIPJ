<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시물 삭제 결과</title>
    <script type="text/javascript">
        function showAlertAndRedirect() {
            var isDeleted = <%= request.getAttribute("isDeleted") %>;
            var bno = "<%= request.getAttribute("bno") %>";
            var message = isDeleted ? "게시물이 성공적으로 삭제되었습니다." : "게시물 삭제에 실패했습니다.";
            
            alert(message);
            
            if (isDeleted) {
                // 삭제가 성공적으로 이루어진 경우
                setTimeout(function() {
                    window.location.href = '/list.do';
                }, 100); // 100ms 대기 후 리다이렉션
            } else {
                // 삭제가 실패한 경우
                // 이 경우에는 사용자가 수동으로 리스트 페이지로 돌아가게 할 수 있습니다.
                document.write('<p>게시물 삭제에 실패했습니다. <a href="/list.do">목록으로 돌아가기</a></p>');
            }
        }
        // 페이지가 로드된 후 알림을 표시합니다.
        window.onload = showAlertAndRedirect;
    </script>
</head>
<body>
</body>
</html>