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

/**
 * Servlet implementation class UserDeleteController
 */
@WebServlet("/deleteUser.do")
public class UserDeleteController extends HttpServlet {
   private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserDeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   }

   /**
    * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      UserDAO dao = new UserDAO();
      UserDTO dto = new UserDTO();
      
      
      HttpSession session = request.getSession();
      String sessionId = (String) session.getAttribute("userId");
      String sessionPwd = request.getParameter("pwd");
      
      System.out.println("test" + sessionId + "," + request.getParameter("pwd"));
      
      int[] output=dao.login(sessionId, sessionPwd);
      
      System.out.println("test2" + output[0]);
      //login한 사람의 패스워드가 일치할때
      if(output[0]==1) {
         System.out.println("현재 로그인");
         dto.setUserId(request.getParameter("userid"));
         dao.deleteUserData(dto);
         response.getWriter().write("success");
      }else {
         response.getWriter().write("fail");
      }
      
      
      
   }

}
