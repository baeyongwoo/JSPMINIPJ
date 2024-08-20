package com.worldflower.boardcontroller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.worldflower.boarddao.BoardDAO;
import com.worldflower.boarddto.BoardDTO;
import com.worldflower.utils.FileUtil;

@WebServlet("/deletePost.do")
public class DeletePostController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DeletePostController() {
        super();
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("/login.do");
            return;
        }

        String bno = request.getParameter("bno");
        BoardDAO dao = new BoardDAO();
        BoardDTO dto = null;
        boolean isDeleted = false;

        try {
            dto = dao.selectView(bno);
            if (dto != null) {
            	String postAuthor = dto.getUserId(); // 게시물 작성자
                if (userId.equals(postAuthor)) {
                    // 작성자일 경우 게시물 삭제
                    isDeleted = dao.deletePost(bno) == 1;
                    if (isDeleted) {
                        String saveFileName = dto.getFimgsrc();
                        FileUtil.deleteFile(request, "/upload", saveFileName);
                    }
                } else {
                    // 작성자가 아닌 경우 alert 메시지와 리다이렉트
                    response.setContentType("text/html;charset=UTF-8");
                    PrintWriter writer = response.getWriter();
                    String script = "<script>"
                                    + "alert('작성자가 아닙니다.');"
                                    + "window.location.href = '/list.do';" // 적절한 페이지로 리다이렉트
                                    + "</script>";
                    writer.print(script);
                    writer.flush();
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("삭제 과정 오류 발생: " + e.getMessage());
        } finally {
            dao.close();
        }

        request.setAttribute("isDeleted", isDeleted);
        request.setAttribute("bno", bno);
        request.getRequestDispatcher("/board/DeleteResult.jsp").forward(request, response);
    }
}