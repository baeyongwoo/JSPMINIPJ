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
import com.worldflower.utils.FileUtil;

//정관수 
/*
* 
*/
@WebServlet("/write.do")
public class WriteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public WriteController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 글쓰기 페이지로 이동할 때 로그인이 안됬으면 로그인 페이지로, 아니면 글쓰기 페이지로(닉네임정보포함) 
		HttpSession session=request.getSession();
		String userId = (String)session.getAttribute("userId");
		if(userId==null) {
			response.setContentType("text/html;charset=UTF-8"); // 한글인코딩
			PrintWriter writer = response.getWriter();
			String script = ""
							+ "<script>"
							+ "		alert('"+ "게시물을 작성하시려면 로그인해야합니다." +"');"
							+"      location.href='/login.do';"
							+ "</script>";
			writer.print(script); 
		} else {
			BoardDAO dao = new BoardDAO();
			if(dao.checkRole(userId).equals("nMember")) {
				dao.close();
				System.out.println("권한부족");
		        response.setContentType("text/html; charset=utf-8");
		        PrintWriter w = response.getWriter();
		        w.write("<script>alert('글쓰기를 하려면 이메일 인증이 필요합니다.'); location.href='/index.do'; </script>");
		        w.flush();
		        w.close();
//		        // 일단은 메인페이지로 이동
		        
			} else {
				String userNickName= dao.findNickName(userId);
				dao.close();
				request.setAttribute("userNickName", userNickName);
				request.getRequestDispatcher("/board/Write.jsp").forward(request, response);
				System.out.println(userId);
			}
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 업로드 폴더 따로 작성 필요(/upload 로 임시 작성), 로그인 후 이전 페이지 어떻게 갈지 생각중 
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session=request.getSession();
		String userId = (String)session.getAttribute("userId");
		if(userId==null) {
			response.sendRedirect("/login.do");
			System.out.println("로그인인증실패");
		} else {
			System.out.println("로그인인증성공");	
			
			//파일 업로드 처리
			String saveDirectory=request.getServletContext().getRealPath("/upload");
			
			int maxPostSize=5*1024*1024;
			MultipartRequest mr = FileUtil.uploadFile(request, saveDirectory, maxPostSize);
					
			BoardDTO dto=new BoardDTO();
			dto.setUserId(userId);
			dto.setTitle(mr.getParameter("title"));
			dto.setContent(mr.getParameter("content"));
			dto.setFname(mr.getParameter("fname"));
			dto.setNname(mr.getParameter("nname"));
			
			String fileName=mr.getFilesystemName("fimgname");
			if(fileName!=null) {
				String now= new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
				String ext=fileName.substring(fileName.lastIndexOf("."));
				String newFileName=now+ext;
				
				File oldFile=new File(saveDirectory+File.separator+fileName);
				File newFile=new File(saveDirectory+File.separator+newFileName);
				oldFile.renameTo(newFile);
				
				dto.setFimgname(fileName);
				dto.setFimgsrc(newFileName);
			}
			
			BoardDAO dao=new BoardDAO();
			int result=dao.insertWrite(dto);
			String bno=dao.findBno(dto);
			dao.close();
			System.out.println(bno);
			if(result==1) {
				
				response.setContentType("text/html;charset=UTF-8"); // 한글인코딩
				PrintWriter writer = response.getWriter();
				String script = ""
								+ "<script>"
								+ "		alert('"+ "게시물이 등록되었습니다" +"');"
								+ "		location.href='"+"/view.do?bno="+ bno +"';"
								+ "</script>";
				writer.print(script);
				
//				response.sendRedirect("/view.do?bno=" + bno);
				System.out.println("업로드성공");
			} else {
				response.setContentType("text/html;charset=UTF-8"); // 한글인코딩
				PrintWriter writer = response.getWriter();
				String script = ""
								+ "<script>"
								+ "		alert('"+ "게시물 등록이 실패하였습니다. 다시 시도해 주세요" +"');"
								+ "		location.href='/write.do';"
								+ "</script>";
				writer.print(script);
//				response.sendRedirect("/write.do");
			}

			
		}
	}

}
