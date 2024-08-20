package com.worldflower.boardcontroller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.worldflower.boarddao.BoardDAO;

//정관수 
/*
* 
*/

@WebServlet("/getNationList.do")
public class GetNationListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GetNationListController() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/x-json; charset=UTF-8");

		String fname=request.getParameter("fname");
		BoardDAO dao=new BoardDAO();
		String nationList=dao.getNationList(fname);
//		System.out.println("국가리스트 "+nationList);
		response.getWriter().write(nationList);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
