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
 * Servlet implementation class CheckUseridController
 */
@WebServlet("/checkUserid.do")
public class CheckUseridController extends HttpServlet {
   private static final long serialVersionUID = 1L;
   UserDAO dao = new UserDAO();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckUseridController() {
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
      response.setContentType("application/json");
       response.setCharacterEncoding("UTF-8");
      UserDTO dto = new UserDTO();
      dto.setUserId(request.getParameter("userid"));
      boolean checkId = dao.selectUserId(dto);
      HttpSession session = request.getSession();
      if(checkId) {
         response.getWriter().write("useable");
         session.setAttribute("idAuth", "success");
         
      }else {
         response.getWriter().write("un-useable");
      }
      
   }

}
