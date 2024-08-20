package com.worldflower.usercontroller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.worldflower.userdao.UserDAO;

//정관수 
/* 전체작성
* 
*/
@WebServlet("/logout.do")
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public LogoutController() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그아웃이 아닌 인터넷 창을 끄거나 시간이 만료됬을 때의 n값 변경 처리 어떻게
		HttpSession loginSession=request.getSession();
		String userId=(String)loginSession.getAttribute("userId");
		String prevUrl=request.getHeader("Referer");
		String checkRole="";
		
		if(userId!=null) {
			UserDAO dao=new UserDAO();
			dao.updateLoginN(userId);
	        checkRole=dao.checkRole(userId);
			loginSession.invalidate();
		}
//		System.out.println("aaaaaaaa"+prevUrl);
		response.setContentType("text/html;charset=UTF-8"); // 한글인코딩
		PrintWriter writer = response.getWriter();
		if(checkRole.equals("admin")) {
			String script = ""
					+ "<script>"
					+ "		alert('"+ "(admin)성공적으로 로그아웃 되었습니다." +"');"
					+"      location.href='/index.do';"
					+ "</script>";
			writer.print(script); 
		} else {
			String script = ""
					+ "<script>"
					+ "		alert('"+ "성공적으로 로그아웃 되었습니다." +"');"
					+"      location.href='"+prevUrl+"';"
					+ "</script>";
			writer.print(script); 
		}
		

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
