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
 * Servlet implementation class CheckEmailController
 */
@WebServlet("/checkEmail.do")
public class CheckEmailController extends HttpServlet {
   private static final long serialVersionUID = 1L;
       
   UserDAO dao = new UserDAO();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckEmailController() {
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
      UserDTO dto = new UserDTO();
      dto.setEmail(request.getParameter("email"));
      dao.selectEmail(dto);
      boolean checkEmail = dao.selectEmail(dto);
      HttpSession session = request.getSession();
      session.setAttribute("email", "success");
      
      if(checkEmail) {
         response.getWriter().write("useable");
         
      }else {
         response.getWriter().write("un-useable");
      }
   }

}
