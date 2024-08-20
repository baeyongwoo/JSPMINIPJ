package com.worldflower.usercontroller;

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
import com.worldflower.boarddao.NationDAO;
import com.worldflower.boarddto.BoardDTO;
import com.worldflower.utils.FileUtil;

/**
 * Servlet implementation class AdminNationListController
 */
@WebServlet("/nationRegister.do")
public class AdminNationRegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminNationRegisterController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		request.getRequestDispatcher("/admin/NationRegister.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 업로드 폴더 따로 작성 필요(/upload 로 임시 작성), 로그인 후 이전 페이지 어떻게 갈지 생각중 
		request.setCharacterEncoding("UTF-8");
			
			//파일 업로드 처리
			String saveDirectory=request.getServletContext().getRealPath("/upload/nation");
			
			int maxPostSize=5*1024*1024;
			MultipartRequest mr = FileUtil.uploadFile(request, saveDirectory, maxPostSize);
					
			BoardDTO dto=new BoardDTO();
			dto.setNname(mr.getParameter("nname"));
			dto.setNinfo(mr.getParameter("content"));
			dto.setNlocation(mr.getParameter("nlocation"));
			
			String fileName=mr.getFilesystemName("fimgname");
			dto.setNimgname(fileName);
			if(fileName!=null) {
				String now= new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
				String ext=fileName.substring(fileName.lastIndexOf("."));
				String newFileName=now+ext;
				
				File oldFile=new File(saveDirectory+File.separator+fileName);
				File newFile=new File(saveDirectory+File.separator+newFileName);
				oldFile.renameTo(newFile);
				
				dto.setNimgsrc(newFileName);
	
			}
			
			
			NationDAO dao=new NationDAO();
			dao.inserNationInfo(dto);
			
			
			
			response.sendRedirect("/nationboardlist.do");
			
		}
		

}
