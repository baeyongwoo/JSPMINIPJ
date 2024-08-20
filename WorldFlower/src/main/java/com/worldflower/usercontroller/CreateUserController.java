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
import com.worldflower.userdto.UserDTO;

//배용우 
// 08.06 작업내용 : 전체복사

/**
 * Servlet implementation class CreateUserServlet
 */
@WebServlet("/join.do")
public class CreateUserController extends HttpServlet {
   private static final long serialVersionUID = 1L;
    
   UserDAO dao = new UserDAO();
   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateUserController() {
        super();
        // TODO Auto-generated constructor stub
    }

   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        System.out.println("doget " + session.getAttribute("userId"));
        
        if (session.getAttribute("userId") != null && !session.getAttribute("userId").equals("null")) {
            response.sendRedirect("/index.jsp");
            return;
            //request.getRequestDispatcher("/index.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/user/UserJoin.jsp").forward(request, response);
        }
    }


   /**
    * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      request.setCharacterEncoding("utf-8");
      response.setContentType("text/html; charset=utf-8");
      UserDTO dto =  new UserDTO();
      
      dto.setUserId(request.getParameter("userid"));
      dto.setUserNickName(request.getParameter("nickname"));
      dto.setPwd(request.getParameter("pwd"));
      dto.setEmail(request.getParameter("email"));
      
      HttpSession session = request.getSession();
      String checkEmailAuth = (String) session.getAttribute("checkEmail");
      dto.setEmailCheck(checkEmailAuth);
      
      int insertUI=0;
      int insertUP=0;
      int insertUE=0;
      //이메일이 인증되었을경우
      if(checkEmailAuth.equals("check")) {
         dto.setRole("member");
         System.out.println("email인증완료");
         insertUI=dao.insertUserInfo(dto);         
         insertUP=dao.insertUserPwd(dto);
         insertUE=dao.insertUserEmail(dto);
         
      }else { //이메일 인증 안되었을경우
         dto.setRole("nMember");
         insertUI=dao.insertUserInfo(dto);
         insertUP=dao.insertUserPwd(dto);
         insertUE=1;
      }
      
      PrintWriter writer = response.getWriter();
      if(insertUI!=0 && insertUP!=0 && insertUE !=0) {
    	  String script = ""
    			  + "<script>"
    			  + "	  alert('정상적으로 회원가입이 되었습니다.');"
    			  +"      location.href='/login.do';"
    			  + "</script>";
    	  writer.print(script); 
      } else {
    	  String script = ""
    			  + "<script>"
    			  + "	  alert('회원가입에 문제가 생겼습니다. 관리자에게 문의해 보세요.');"
    			  +"      location.href='/join.do';"
    			  + "</script>";
    	  writer.print(script); 
      }      
      
   }

}
