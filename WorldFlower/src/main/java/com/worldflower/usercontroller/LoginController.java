package com.worldflower.usercontroller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.worldflower.userdao.UserDAO;
import com.worldflower.userdto.UserDTO;

// 정관수 
/* 용우씨가 한거 통합+수정
 * 덮어씌워주세요
 */
@WebServlet("/login.do")
public class LoginController extends HttpServlet {
   private static final long serialVersionUID = 1L;
       
    public LoginController() {
        super();
    }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   String prevUrl=request.getHeader("Referer");
	   request.setAttribute("prevUrl", prevUrl);
	   request.getRequestDispatcher("/user/Login.jsp").forward(request, response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String userId=request.getParameter("userId");
      String pwd=request.getParameter("pwd");
      String prevUrl=request.getParameter("prevUrl");
//    System.out.println("bbbbbbbb"+prevUrl);
      UserDAO dao = new UserDAO();
      UserDTO dto = new UserDTO();
      int[] output=dao.login(userId, pwd);
      
      if(output[0]==1) {
         HttpSession session=request.getSession();
         session.setAttribute("userId", userId);
         session.setMaxInactiveInterval(60*60); // 세션 1시간
         
         System.out.println("session 발급");
         dto.setUserId(userId);
         UserDAO dao2 = new UserDAO();
         dao2.selectUser(dto);

         dao2.updateLoginY(session);
         
         if(dto.getRole().equals("admin")) {
            System.out.println("admin 접속");
            response.sendRedirect("/adminPage.do");
            return;
            
         }
         System.out.println("abbbb"+prevUrl);
         if(prevUrl.equals("http://localhost:8383/join.do")) {
        	 response.sendRedirect("/index.do");
         } else if(prevUrl.equals("http://localhost:8383/login.do")){
        	 response.sendRedirect("/index.do");
         } else if(prevUrl.contains("http://localhost:8383/userUpdate.do")){
        	 response.sendRedirect("/index.do");
        	 
         } else if(prevUrl=="") {
        	 response.sendRedirect("/index.do");
         } else {
        	 response.sendRedirect(prevUrl);
        	 System.out.println("aaaaaaaaaaaa"+prevUrl);
         }
         
      } else {
         request.setAttribute("message","ID 또는 비밀번호가 잘못되었습니다. ID와 비밀번호를 다시 확인 해 주세요.");
         System.out.println(output[1]);
         request.setAttribute("pCount", output[1]);
         request.getRequestDispatcher("/user/Login.jsp").forward(request, response);
      }
      //index
   }

}
