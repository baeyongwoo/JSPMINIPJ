package com.worldflower.usercontroller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.worldflower.boarddao.BoardDAO;
import com.worldflower.boarddto.BoardDTO;
import com.worldflower.userdao.UserDAO;

/**
 * Servlet implementation class AdminPageController
 */
@WebServlet("/adminPage.do")
public class AdminPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminPageController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//admin 페이지로 보여주기
		UserDAO dao = new UserDAO();
		System.out.println("dao" + dao.selectUserAll());
		BoardDAO bdao =new BoardDAO();
		UserDAO udao =new UserDAO();
		List<BoardDTO> dateList = new ArrayList<>();
		List<BoardDTO> viewList = new ArrayList<>();
		List<String> loginUser = new ArrayList<>();
		
		dateList=bdao.indexDate();
		viewList=bdao.indexView();
		loginUser=udao.findLogin();
		
		request.setAttribute("dateList", dateList);
		request.setAttribute("viewList", viewList);
		request.setAttribute("userNickName", loginUser);
		request.setAttribute("userList", dao.selectUserAll());
		
		request.getRequestDispatcher("/admin/info.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
