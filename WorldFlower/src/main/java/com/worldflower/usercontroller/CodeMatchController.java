package com.worldflower.usercontroller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class codeEquController
 */
@WebServlet("/checkCode.do")
public class CodeMatchController extends HttpServlet {
   private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CodeMatchController() {
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
      String inputCode = request.getParameter("emailCheck");
      HttpSession session = request.getSession();
      String sendCode = (String) session.getAttribute("codeAuth");
      
      if(inputCode.equals(sendCode)) {
         System.out.println("코드 일치");
         session.setAttribute("checkEmail", "check");
         response.getWriter().write("access");
      }else {
         response.getWriter().write("un-access");
      }
      
   }

}
