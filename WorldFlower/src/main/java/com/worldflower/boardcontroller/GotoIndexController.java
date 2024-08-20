package com.worldflower.boardcontroller;

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

//정관수 
/*
* 
*/
@WebServlet("/index.do")
public class GotoIndexController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GotoIndexController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardDAO dao =new BoardDAO();
		UserDAO udao =new UserDAO();
		List<BoardDTO> dateList = new ArrayList<>();
		List<BoardDTO> viewList = new ArrayList<>();
		List<String> loginUser = new ArrayList<>();
		
		dateList=dao.indexDate();
		viewList=dao.indexView();
		loginUser=udao.findLogin();
		
		request.setAttribute("dateList", dateList);
		request.setAttribute("viewList", viewList);
		request.setAttribute("userNickName", loginUser);
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
