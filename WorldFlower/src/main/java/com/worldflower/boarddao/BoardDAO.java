package com.worldflower.boarddao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import com.worldflower.boarddto.BoardDTO;
import com.worldflower.common.DBConnection;

public class BoardDAO {
public PreparedStatement psmt;
public Connection conn;
public ResultSet rs;
public Statement stmt;

public BoardDAO() {
    try {
        this.conn = DBConnection.getInstance().getConnection();
    } catch (NamingException | SQLException e) {
        e.printStackTrace();
    }
}	
//보름씨

//전제글수
	public int selectCount(Map<String,Object> map) {
		int totalCount=0;
		String query="select count(*) from flowerboard fb join userInfo ui on fb.userId=ui.userId join flowerInfo fin on fb.fid=fin.fid";
		if(map.get("searchWord")!=null) {
			if(map.get("searchField").equals("Writer")) {
				query+="		where ui.userNickName like '%"+map.get("searchWord")+"%'";
			} else if (map.get("searchField").equals("Flower")) {
				query+="		where fin.fName like '%"+map.get("searchWord")+"%'";
			} else if (map.get("searchField").equals("Nation")) {
				query+="		where fb.nname like '%"+map.get("searchWord")+"%'";
			} else {
				query+="		where "+map.get("searchField")+" like '%"+map.get("searchWord")+"%'";
			}
		}
		try {
			stmt=conn.createStatement();
			rs=stmt.executeQuery(query);
			if(rs.next()) {
				totalCount=rs.getInt(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return totalCount;
	}
	
	//목록페이징
	public List<BoardDTO> selectListPage(Map<String,Object> map){
		List<BoardDTO> board=new ArrayList<BoardDTO>();
		String query="select * from ( "
				+ "		select tb.*, rownum rnum from ( "
				+ "			select fb.bno, ui.userNickName, fb.title, fb.content, fb.postdate, fb.updatedate, fb.viewcount, fin.fname, ni.nimgname, ni.nlocation, nf.fopendate, fi.fimgsrc, fb.nname "
				+ " from flowerboard fb join userInfo ui on fb.userid=ui.userid join flowerInfo fin on fin.fid=fb.fid join nationInfo ni on ni.nname=fb.nname join nation_flower nf on nf.nname=ni.nname"
				+ "						left join flowerimg fi on fb.fcode=fi.fcode" ;
		//검색조건추가
		if(map.get("searchWord")!=null) {
			if(map.get("searchField").equals("Writer")) {
				query+="		where ui.userNickName like '%"+map.get("searchWord")+"%'";
			} else if (map.get("searchField").equals("Flower")) {
				query+="		where fin.fName like '%"+map.get("searchWord")+"%'";
			} else if (map.get("searchField").equals("Nation")) {
				query+="		where fb.nname like '%"+map.get("searchWord")+"%'";
			} else {
				query+="		where "+map.get("searchField")+" like '%"+map.get("searchWord")+"%'";
			}
		}

		query+="			order by bno desc "
				+ "		) tb "
				+ " ) "
				+ " where rnum between ? and ?";
		try {
			psmt=conn.prepareStatement(query);
			psmt.setInt(1, Integer.parseInt(map.get("start").toString()));
			psmt.setInt(2, Integer.parseInt(map.get("end").toString()));
			rs=psmt.executeQuery();
			while(rs.next()) {
				BoardDTO dto=new BoardDTO();
				dto.setBno(rs.getString("bno"));
				dto.setUserNickName(rs.getString("userNickName"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setUpdatedate(rs.getDate("updatedate"));
				dto.setViewcount(rs.getInt("viewcount"));
				dto.setFname(rs.getString("fname"));
				dto.setNimgname(rs.getString("nimgname"));
				dto.setNlocation(rs.getString("nlocation"));
				dto.setFopendate(rs.getDate("fopendate"));
				dto.setFimgsrc(rs.getString("fimgsrc"));
				dto.setNname(rs.getString("nname"));
				board.add(dto);
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		return board;
	}
	public BoardDTO selectView(String bno) {
		BoardDTO dto = new BoardDTO();
		String query = "select * "
				+ " from flowerboard fb left join flowerimg fi "
				+ " on fb.fcode=fi.fcode "
				+ " join flowerinfo fin "
				+ " on fb.fid = fin.fid "
				+ " where bno=?";
		try {
			psmt=conn.prepareStatement(query);
			psmt.setString(1, bno);
			rs=psmt.executeQuery();
			if(rs.next()) {
				dto.setBno(rs.getString(1));
				dto.setUserId(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setPostdate(rs.getDate(5));
				dto.setUpdatedate(rs.getDate(6));
				dto.setViewcount(rs.getInt(7));
				dto.setFid(rs.getString(8));
				dto.setNname(rs.getString(9));
				dto.setFcode(rs.getString(10));
				dto.setFimgsrc(rs.getString(12));
				dto.setFimgname(rs.getString(13));
				dto.setFname(rs.getString(15));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return dto;
	}
	
	//조회수증가
		public void updateViewCount(String bno) {		
			String sql = "update flowerboard set "
					+ " viewcount=viewcount+1 "
					+ " where bno=?";
			
			try {
				psmt=conn.prepareStatement(sql);
				psmt.setString(1, bno);
				psmt.executeQuery();
			}
			catch(Exception e) {
				System.out.println("게시물 조회수 증가 중 예외 발생");
				e.printStackTrace();
			}
		}
		
	//삭제
	public int deletePost(String bno) {
		int result = 0;
		try {
			String query = "delete flowerboard where bno=?";
			
			psmt=conn.prepareStatement(query);
			psmt.setString(1, bno);
			result = psmt.executeUpdate();
		}
		catch(Exception e) {
			System.out.println("삭제문제발생");
			e.printStackTrace();
		}
		return result;
	}
	
	//게시글 제목, 수정
	public int updatePost(BoardDTO dto) {
		int result=0;
		try {
			String boardquery = " update flowerboard set title=?, nname=?, content=? "
					+ " where bno=? " ;
			
			
			psmt=conn.prepareStatement(boardquery);
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getNname());
			psmt.setString(3, dto.getContent());
			psmt.setString(4, dto.getBno());
			
			result = psmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	//게시글 사진수정
	public int updatePost2(BoardDTO dto) {
		int result = 0;
		try {
			String infoquery = " insert into flowerimg values (seq_fCode.nextval, ?, ? )";
			
			psmt=conn.prepareStatement(infoquery);
			psmt.setString(1, dto.getFimgsrc());
			psmt.setString(2, dto.getFimgname());
			
			result = psmt.executeUpdate();
			
			String getFcode=" select max(fCode) from flowerimg where fimgname=? and fimgsrc=? ";
			
			psmt=conn.prepareStatement(getFcode);
			psmt.setString(1, dto.getFimgname());
			psmt.setString(2, dto.getFimgsrc());	
			ResultSet rs2= psmt.executeQuery();
			while(rs2.next()) {
				dto.setFcode(rs2.getString("max(fCode)"));
			}
			
			String updateSQL="update flowerboard set fCode=? where bno=?";
			psmt=conn.prepareStatement(updateSQL);
			psmt.setString(1, dto.getFcode());
			psmt.setString(2,dto.getBno());
			psmt.executeQuery();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
}
		
	//글쓰기 정관수
	//글쓰기
		public int insertWrite(BoardDTO dto) {
			PreparedStatement psmt=null;
			ResultSet rs=null;
			int result=0;
			
			try {
				// fid 찾기(꽃이름)
				String findFid="select fid from flowerInfo where fname=?";
				psmt=conn.prepareStatement(findFid);
				psmt.setString(1, dto.getFname());
				rs=psmt.executeQuery();
				String fid=null;
				if(rs.next()) {
					fid=rs.getString("fid");
				}
				
				// 이미지 업로드 했을 때 flowerImg 테이블 insert
				String fimgsrc=null;
				String fimgname=null;
				String writeSql=null;
				if(dto.getFimgname()!=null) {
					String imgSql="insert into flowerImg values (seq_fcode.nextval, ?, ?)";
					psmt=conn.prepareStatement(imgSql);
					fimgsrc=dto.getFimgsrc();
					fimgname=dto.getFimgname();
					psmt.setString(1,fimgsrc);
					psmt.setString(2,fimgname);
					psmt.executeUpdate();

					// flowerboard에 insert (flowerImg 있을경우)
					writeSql="insert into flowerboard (bno, userId, title, content, fid, nname, fCode)"
							+ " values(seq_fboard.nextval, ?,?,?,'"+fid+"',?, (select max(fcode) from flowerImg where fimgsrc='"+fimgsrc+"'and fimgname='"+fimgname+"'))";
				} else {
					// flowerboard에 insert (flowerImg 없을경우)
					writeSql="insert into flowerboard (bno, userId, title, content, fid, nname)"
							+ " values(seq_fboard.nextval, ?,?,?,'"+fid+"',?)";
				}
				System.out.println(writeSql);
				psmt=conn.prepareStatement(writeSql);
				psmt.setString(1, dto.getUserId());
				psmt.setString(2, dto.getTitle());
				psmt.setString(3, dto.getContent());
				psmt.setString(4, dto.getNname());
				result=psmt.executeUpdate();
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
			return result;
		} // 글쓰기 끝
		
		
		// id로 이름 가져오기
		public String findNickName (String userId){
			PreparedStatement psmt=null;
			ResultSet rs=null;
			String nickname="";
			
			String findSql="select userNickName from userInfo where userId=?";
			try {
				psmt=conn.prepareStatement(findSql);
				psmt.setString(1, userId);
				rs=psmt.executeQuery();
				if(rs.next()) {
					nickname=rs.getString("userNickName");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} 		
			return nickname;
		} // id로 이름 가져오기 끝
		
		// bno 찾기 (작성후 작성한 view로 가기 위함)
		public String findBno(BoardDTO dto){
			PreparedStatement psmt=null;
			ResultSet rs=null;
			String bno=null;
			
			try {
				String findFidSql="select max(bno) from flowerBoard where userId=? and title=? and content=? and nname=?";
				psmt=conn.prepareStatement(findFidSql);
				psmt.setString(1, dto.getUserId());
				psmt.setString(2, dto.getTitle());
				psmt.setString(3, dto.getContent());
				psmt.setString(4, dto.getNname());

				rs=psmt.executeQuery();
				if(rs.next()) {
					bno=rs.getString("max(bno)");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} 		
			return bno;
		} // bno 찾기 끝
		
		// 꽃에 해당하는 국가 찾기
		public String getNationList (String fname){
			String nation = null;
			ArrayList<String> arr = new ArrayList<>();
			PreparedStatement psmt=null;
			ResultSet rs=null;
			
			try {
				String findFidSql="select fid from flowerInfo where fname=?";
				psmt=conn.prepareStatement(findFidSql);
				System.out.println("fname "+fname);
				psmt.setString(1, fname);
				rs=psmt.executeQuery();
				String fid=null;
				if(rs.next()) {
					fid=rs.getString("fid");
				}
				System.out.println("fid "+fid);
				
				String findNationSql="select nname from nation_flower where fid=?";
				psmt=conn.prepareStatement(findNationSql);
				psmt.setString(1, fid);
				rs=psmt.executeQuery();
				String nname=null;
				while(rs.next()) {
					nname=rs.getString("nname");
					arr.add(nname);
				}
				
				for(String val : arr) {
					
					System.out.println("국가:"+val);
				}
				nation = arr.toString();
				nation=nation.replace("[", "\"");
				nation=nation.replace("]", "\"");
				
			} catch (Exception e) {
				e.getStackTrace();
			} 
			return nation;
		} // 꽃에 해당하는 국가 찾기 끝
			
		// check role
		public String checkRole(String userId) {
			String role=null;
			PreparedStatement psmt=null;
			ResultSet rs=null;
			
			String countSql="select role from userInfo where userId=?";
			try {
				psmt=conn.prepareStatement(countSql);
				psmt.setString(1, userId);
				rs=psmt.executeQuery();
				if(rs.next()) {
					role=rs.getString("role");
				}
			} catch(Exception e) {
				e.printStackTrace();
			} 
			return role;
		} //check role 끝	
		
	// db커넥션 종료
	public void close() {
		try {            
			if (rs != null) rs.close();
			if (stmt != null) stmt.close();
			if (psmt != null) psmt.close();
			if (conn != null) conn.close();  // 자동으로 커넥션 풀로 반납됨
			
			System.out.println("DB 커넥션 풀 자원 반납");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 최신순 index
		public List<BoardDTO> indexDate(){
			List<BoardDTO> arr= new ArrayList<>();
			String query="select * from ( "
					+ "		select tb.*, rownum rnum from ( "
					+ "			select fb.bno, ui.userNickName, fb.title, fb.postdate, fb.updatedate, fb.viewcount, fin.fname, fi.fimgsrc "
					+ " from flowerboard fb join userInfo ui on fb.userid=ui.userid join flowerInfo fin on fin.fid=fb.fid"
					+ "						left join flowerimg fi on fb.fcode=fi.fcode" 
					+ "		order by fb.updatedate desc "
					+ "		) tb "
					+ " ) "
					+ " where rnum between 1 and ?";
			try {
				psmt=conn.prepareStatement(query);
				psmt.setString(1, "2");
				rs=psmt.executeQuery();
				while(rs.next()) {
					BoardDTO dto=new BoardDTO();
					dto.setBno(rs.getString("bno"));
					dto.setUserNickName(rs.getString("userNickName"));
					dto.setTitle(rs.getString("title"));
					dto.setPostdate(rs.getDate("postdate"));
					dto.setUpdatedate(rs.getDate("updatedate"));
					dto.setViewcount(rs.getInt("viewcount"));
					dto.setFname(rs.getString("fname"));
					dto.setFimgsrc(rs.getString("fimgsrc"));
					arr.add(dto);
				}

			}catch(Exception e) {
				e.printStackTrace();
			}
			
			return arr;
		};
		
		// 조회수 index
		public List<BoardDTO> indexView(){
			List<BoardDTO> arr= new ArrayList<>();
			String query="select * from ( "
					+ "		select tb.*, rownum rnum from ( "
					+ "			select fb.bno, ui.userNickName, fb.title, fb.postdate, fb.updatedate, fb.viewcount, fin.fname, fi.fimgsrc "
					+ " from flowerboard fb join userInfo ui on fb.userid=ui.userid join flowerInfo fin on fin.fid=fb.fid"
					+ "						left join flowerimg fi on fb.fcode=fi.fcode" 
					+ "		order by fb.viewcount desc "
					+ "		) tb "
					+ " ) "
					+ " where rnum between 1 and ?";
			try {
				psmt=conn.prepareStatement(query);
				psmt.setString(1, "2");
				rs=psmt.executeQuery();
				while(rs.next()) {
					BoardDTO dto=new BoardDTO();
					dto.setBno(rs.getString("bno"));
					dto.setUserNickName(rs.getString("userNickName"));
					dto.setTitle(rs.getString("title"));
					dto.setPostdate(rs.getDate("postdate"));
					dto.setUpdatedate(rs.getDate("updatedate"));
					dto.setViewcount(rs.getInt("viewcount"));
					dto.setFname(rs.getString("fname"));
					dto.setFimgsrc(rs.getString("fimgsrc"));
					arr.add(dto);
				}

			}catch(Exception e) {
				e.printStackTrace();
			}
			
			return arr;
		};


}
