package com.worldflower.boardcontroller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.worldflower.boarddao.BoardDAO;
import com.worldflower.boarddto.BoardDTO;
import com.worldflower.userdao.UserDAO;
import com.worldflower.utils.FileUtil;

/**
 * Servlet implementation class EditController
 */
@WebServlet("/edit.do")
public class EditController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session=request.getSession();
		String userId = (String)session.getAttribute("userId");
		if(userId==null) {
			response.sendRedirect("/login.do");
		} else {
			String bno = request.getParameter("bno");// 글번호
			BoardDAO dao = new BoardDAO(); // dao 생성
			BoardDTO dto = dao.selectView(bno); // 상세정보 dto에 저장
		    String postAuthor = dto.getUserId(); // 게시물 작성자
		    if (!userId.equals(postAuthor)) {
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
			
			request.setAttribute("dto", dto); // "dto"라는 attribute name으로 전달
			request.getRequestDispatcher("/board/Edit.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session=request.getSession();
		String userId = (String)session.getAttribute("userId");
		if(userId==null) {
			response.sendRedirect("/login.do");
			System.out.println("로그인인증실패");
		} else {
			System.out.println("로그인인증성공");

		// 파일업로드
		String saveDirectory = request.getServletContext().getRealPath("/upload");
		System.out.println(saveDirectory);
		int maxPostSize = 10 * 1024 * 1024;

		MultipartRequest mr = FileUtil.uploadFile(request, saveDirectory, maxPostSize);

		if (mr == null) {
			try {
				response.setContentType("text/html;charset=UTF8");
				PrintWriter writer = response.getWriter();
				String script = " "
						+ "<script>"
						+ "		alert('" + "첨부파일이 너무 큽니다." + "');"
						+ "		history.back();"
						+ "</script>";
				writer.print(script);
			}
			catch(Exception e) {}
			return;
		}

		// 폼에 입력한 값 저장.
		String bno = mr.getParameter("bno");
		String prevOfile = mr.getParameter("prevOfile");
		String prevSfile = mr.getParameter("prevSfile");

		String userid = mr.getParameter("userId");
		String nname = mr.getParameter("nname");
		String title = mr.getParameter("title");
		String content = mr.getParameter("content");
		
		String fimgname = mr.getParameter("fimgname");
		String fimgsrc = mr.getParameter("fimgsrc");
		String fcode = mr.getParameter("fcode");
		

		BoardDTO dto = new BoardDTO();
		dto.setBno(bno);
		dto.setUserId(userid);
		dto.setNname(nname);
		dto.setTitle(title);
		dto.setContent(content);
		
		dto.setFimgname(fimgname);
		dto.setFimgsrc(fimgsrc);
		dto.setFcode(fcode);

		String fileName = mr.getFilesystemName("fimgname");
		if (fileName != null) {
			String now = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
			String ext = fileName.substring(fileName.lastIndexOf("."));
			String newFileName = now + ext;

			File oldFile = new File(saveDirectory + File.separator + fileName);
			File newFile = new File(saveDirectory + File.separator + newFileName);
			oldFile.renameTo(newFile);

			dto.setFimgname(fileName);
			dto.setFimgsrc(newFileName);

			FileUtil.deleteFile(request, "/upload", prevSfile);
		} else {
			dto.setFimgname(prevOfile);
			dto.setFimgsrc(prevSfile);
		}

		BoardDAO dao = new BoardDAO();
		int result = dao.updatePost(dto);
		if(dto.getFimgname()!=null) {
			
			int result2 = dao.updatePost2(dto);
			System.out.println(result2);
		}
		
		dao.close();

		if (result == 1) {
			response.sendRedirect("/view.do?bno=" + bno);
			
//			if(result2 == 1) {
//				response.sendRedirect("/view.do?bno=" + bno);
			} else {
				try {
					response.setContentType("text/html;charset=UTF-8"); // 한글인코딩
					PrintWriter writer = response.getWriter();
					String script = ""
									+ "<script>"
									+ "		alert('"+ "파일 업로드중 오류 발생" +"');"
									+ "		location.href='"+"/view.do?bno="+ bno +"';"
									+ "</script>";
					writer.print(script); // js코드를 클라이언트 브라우저로 전송해서 실행함.
				
				} catch(Exception e) {
					
				}
//			}
		}
	}
	}
}
