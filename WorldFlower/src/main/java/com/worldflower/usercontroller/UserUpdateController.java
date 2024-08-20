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
 * Servlet implementation class UserUpdateController
 */
@WebServlet("/userUpdate.do")
public class UserUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserUpdateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {			
		String userId = request.getParameter("userId");
	    if (userId != null) {
	        UserDAO dao = new UserDAO();
	        UserDTO dto = new UserDTO();
	        dto.setUserId(userId);

	        try {
	            dto = dao.selectUser(dto);

	            
	            if (dto != null) {
	                request.setAttribute("dto", dto);
	                request.getRequestDispatcher("/user/UserUpdate.jsp").forward(request, response);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        HttpSession session=request.getSession();
        
	    String userId = (String)session.getAttribute("userId"); 
	    String pwd = request.getParameter("pwd");
	    String userNickName = request.getParameter("nickname");
	    UserDTO dto = new UserDTO();
	    dto.setUserId(userId);
	    dto.setUserNickName(userNickName);
	    dto.setPwd(pwd);

	    
	    UserDAO dao = new UserDAO();
	    int result = dao.updateUser(dto);
	    int result2 = dao.updatePwd(dto);
	    
	    if (result == 1 && result2 == 1) {
	        System.out.println("회원정보수정완료");
	    	response.sendRedirect("/login.do");
	    }else {
	    	System.out.println("회원정보수정오류");
	    	response.sendRedirect("/index.do");
	    }
	}
	
}
