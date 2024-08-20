package com.worldflower.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

public class FileUtil {
	//파일업로드처리
	public static MultipartRequest uploadFile(HttpServletRequest request, String saveDirectory, int maxPostSize) {
		try {
			return new MultipartRequest(request, saveDirectory, maxPostSize, "UTF-8");
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//다운로드처리
	public static void download(HttpServletRequest request, HttpServletResponse response, String directory, String sfileName, String ofileName) {
		String sDirectory = request.getServletContext().getRealPath(directory);
		try {
			File file = new File(sDirectory, sfileName);
			InputStream iStream = new FileInputStream(file);
			
			ofileName = new String(ofileName.getBytes("UTF-8"), "ISO-8859-1");
			
			response.reset();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=\""+ofileName+"\"");
			response.setHeader("Content-Length", "" + file.length() );
			
			OutputStream oStream = response.getOutputStream();
			byte b[] = new byte[1024];
			int readBuffer = 0;
			while((readBuffer=iStream.read(b)) > 0) {
				oStream.write(b, 0, readBuffer);
			}
			
			iStream.close();
			oStream.close();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// 삭제부분
	public static void deleteFile(HttpServletRequest request, String directory, String filename) {
		String sDirectory = request.getServletContext().getRealPath(directory);
		File file = new File(sDirectory + File.separator + filename);
		if(file.exists()) {
			file.delete();
		}
	}
}
