/* 1부터 전체 */

package com.worldflower.boardcontroller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.worldflower.boarddao.BoardDAO;
import com.worldflower.boarddto.BoardDTO;

@WebServlet("/list.do")
public class ListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardDAO dao=new BoardDAO(); //DAO생성
		Map<String,Object> map=new HashMap<String,Object>(); //Map생성
		String searchField=request.getParameter("searchField"); //검색필드
		String searchWord=request.getParameter("searchWord");
		if(searchWord!=null) {
			map.put("searchField", searchField);
			map.put("searchWord", searchWord);
		}
		
		int totalcount=dao.selectCount(map);
		int pageSize=8;
		int blockPage=10;
		
		int pageNum=1;
		String pageTemp=request.getParameter("pageNum");
		if(pageTemp!=null&&!pageTemp.equals("")) {
			pageNum=Integer.parseInt(pageTemp);
		}
		int start=(pageNum-1)*pageSize+1;
		int end=pageNum*pageSize;
		map.put("start", start);
		map.put("end", end);
		
		List<BoardDTO> boardLists=dao.selectListPage(map);
		dao.close();

		String pagingImg=BoardPage.pagingStr(totalcount, pageSize, blockPage, pageNum, "/list.do", searchField, searchWord);
//		System.out.println(pagingImg);
		
		request.setAttribute("boardLists", boardLists);
		request.setAttribute("map", map);
		request.setAttribute("paging", pagingImg);
		request.getRequestDispatcher("/board/List.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.setCharacterEncoding("UTF-8");
//		//파일업로드 처리
//		String saveDirectory=request.getServletContext().getRealPath("/upload");
//		System.out.println(saveDirectory);

	}
}
