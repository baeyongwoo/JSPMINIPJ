package com.worldflower.usercontroller;

import java.io.IOException;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.worldflower.userdao.UserDAO;
import com.worldflower.userdto.UserDTO;

/**
 * Servlet implementation class SendEmailAuth
 */
@WebServlet("/SendEmailAuth.do")
public class SendEmailAuth extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendEmailAuth() {
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
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
		UserDTO dto = new UserDTO();
		dto.setEmail(request.getParameter("email"));
		String codeauth = dao.sendEmailAuth(dto);
		
		HttpSession session = request.getSession();
		session.setAttribute("codeAuth", codeauth);
		
		response.getWriter().write("success");
		
		
		
	}

}
