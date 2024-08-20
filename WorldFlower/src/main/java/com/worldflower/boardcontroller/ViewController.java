package com.worldflower.boardcontroller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.worldflower.boarddao.BoardDAO;
import com.worldflower.boarddto.BoardDTO;

/**
 * Servlet implementation class ViewController
 */
@WebServlet("/view.do")
public class ViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardDAO dao = new BoardDAO(); // dao생성
		String bno = request.getParameter("bno"); // 글번호
		dao.updateViewCount(bno); // 조회수증가
		BoardDTO dto = dao.selectView(bno); // 상세정보
		dao.close();
		
		request.setAttribute("dto", dto); // attribute name "dto"에 dto를 저장
		request.getRequestDispatcher("/board/View.jsp").forward(request, response); // 포워딩. 주소변경안됨.
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
