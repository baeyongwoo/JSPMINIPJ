package com.worldflower.userdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import com.worldflower.boarddto.BoardDTO;
import com.worldflower.common.DBConnection;
import com.worldflower.userdto.UserDTO;
import com.worldflower.utils.createVerificationCode;
import com.worldflower.utils.emailAuth;

public class UserDAO {
    private Connection conn;

    public UserDAO() {
        try {
            this.conn = DBConnection.getInstance().getConnection();
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }
    }
    public List<UserDTO> selectUserAll() {
        final String sql = "select ui.userid, ui.usernickname, ui.userregdate, ui.userupdatedate, ui.loginTime, ui.checkresign, ui.role,um.email\r\n"
              + "             from userinfo ui \r\n"
              + "             full join useremail um on ui.userid= um.userid";
        List<UserDTO> userList = new ArrayList<>();
        try (PreparedStatement psmt = conn.prepareStatement(sql);
           
             ResultSet rs = psmt.executeQuery()) {
            while (rs.next()) {
                UserDTO dto = new UserDTO();
                dto.setUserId(rs.getString(1));
                dto.setUserNickName(rs.getString(2));
                dto.setUserRegdate(rs.getTimestamp("userRegdate"));
                dto.setUserUpdatedate(rs.getTimestamp("userUpdatedate"));
                dto.setloginTime(rs.getTimestamp("loginTime"));
                dto.setCheckResign(rs.getString(6));
                dto.setRole(rs.getString(7));
                dto.setEmail(rs.getString(8));
                userList.add(dto);
            }
        } catch (SQLException e) {
            System.out.println("SQL 에러");
            e.printStackTrace();
        }

    
        return userList;
    }//selectUserALl end
    
    //userid 입력받아 정보 출력
    public UserDTO selectUser(UserDTO dto) {
       final String sql = "select ui.userid, ui.usernickname, ui.userregdate, ui.userupdatedate, ui.loginTime, ui.checkresign, ui.role,um.email\r\n"
             + "from userinfo ui \r\n"
             + "full join useremail um on ui.userid= um.userid\r\n"
             + "where ui.userid = ? ";
       try {
         PreparedStatement psmt = conn.prepareStatement(sql);
         psmt.setString(1, dto.getUserId());
         ResultSet rs = psmt.executeQuery();
         
         
         if(rs.next()) {
            dto.setUserId(rs.getString(1));
            dto.setUserNickName(rs.getString(2));
            dto.setUserRegdate(rs.getTimestamp("userregdate"));
            dto.setUserUpdatedate(rs.getTimestamp("userupdatedate"));
            dto.setloginTime(rs.getTimestamp("loginTime"));
            dto.setCheckResign(rs.getString(6));
            dto.setRole(rs.getString(7));
            dto.setEmail(rs.getString(8));
            
         }
         
         
      } catch (Exception e) {
         System.out.println("selectUser sql Err");
         e.printStackTrace();
         
      }
       return dto;
       
    }//selectUser end
    
    //email 중복 체크
    public Boolean selectEmail(UserDTO dto) {
       final String sql = "SELECT * FROM useremail WHERE email= ?";
       try {
         PreparedStatement psmt = conn.prepareStatement(sql);
         
         psmt.setString(1, dto.getEmail());
         ResultSet rs = psmt.executeQuery();
         
         rs.next();
         String result =  rs.getString(1);
         
         return false;
         
         
      } catch (Exception e) {
         System.out.println("selectEmail sql Err");
         e.printStackTrace();
      }
          return true; 
          
      } //end selectEmail
    
    //userid 중복검사
    public Boolean selectUserId(UserDTO dto) {
       final String sql = "SELECT * FROM userinfo WHERE userid= ?";
       try {
         PreparedStatement psmt = conn.prepareStatement(sql);
         psmt.setString(1, dto.getUserId());
         ResultSet rs = psmt.executeQuery();
         
         rs.next();
         String result =  rs.getString(1);
         
         return false;
         
      } catch (Exception e) {
         System.out.println("selectUserid sql Err");
      }
          return true;
      } //selectUserid end
    
    //email transfer 
    public String sendEmailAuth(UserDTO dto) {
       emailAuth auth = new emailAuth();
       
       createVerificationCode createCode = new createVerificationCode();
       String getCode = createCode.createVerificationCodeGet(8);
       
       
       auth.sendEmail(dto.getEmail(), "WorldFlower 인증메시지입니다.", getCode);
       return getCode;
    }//email transfer end
    
    //회원가입
    public int insertUserInfo(UserDTO dto) {
        int rows=0;
    	System.out.println("userinsert dto" + dto);
        final String sql = "INSERT INTO userinfo (userid, usernickname, userregdate, userupdatedate, role)"
              + " values(?, ?, ?, ?, ?)";
        
        try {
           PreparedStatement psmt = conn.prepareStatement(sql);
          psmt.setString(1, dto.getUserId());
          psmt.setString(2, dto.getUserNickName());
          Timestamp timestemp = Timestamp.valueOf(LocalDateTime.now());
          psmt.setTimestamp(3, timestemp);
          psmt.setTimestamp(4, timestemp);
          psmt.setString(5, dto.getRole());
          
          rows = psmt.executeUpdate();
          
          if(rows > 0) System.out.println("회원가입성공");
          
       } catch (Exception e) {
          e.printStackTrace();
          System.out.println("inserUser Err");
       }
       return rows;
    }
    
  //이메일 테이블 값 넣기
    public int insertUserEmail(UserDTO dto) {
    	int rows=0;
    	final String sql = "INSERT INTO useremail (userid, email, emailauthtime, emailcheck)"
             + " values(?, ?, ?, ?)";
       
       try {
          PreparedStatement psmt = conn.prepareStatement(sql);
         psmt.setString(1, dto.getUserId());
         psmt.setString(2, dto.getEmail());
         Timestamp timestemp = Timestamp.valueOf(LocalDateTime.now());
         psmt.setTimestamp(3, timestemp);
         psmt.setString(4, dto.getEmailCheck());
         
         rows = psmt.executeUpdate();
         
         if(rows > 0) System.out.println("이메일 성공");
         
      } catch (Exception e) {
         e.printStackTrace();
         System.out.println("email Err");
      }
      return rows;
       
    }//이메일 테이블 끝
    
    //이메일 테이블 값 넣기
    public int insertUserPwd(UserDTO dto) {
       int rows=0;
    	final String sql = "INSERT INTO userpwd (userid, pwd,pupdatedate)"
             + " values(?, ?,?)";
       
       try {
          PreparedStatement psmt = conn.prepareStatement(sql);
         psmt.setString(1, dto.getUserId());
         psmt.setString(2, dto.getPwd());
         Timestamp timestemp = Timestamp.valueOf(LocalDateTime.now());
         psmt.setTimestamp(3, timestemp);
         
         rows = psmt.executeUpdate();
         
         if(rows > 0) System.out.println("패스워드 성공");
         
      } catch (Exception e) {
         e.printStackTrace();
         System.out.println("pwd Err");
      }
       return rows;
       
    }//이메일 테이블 끝
    
     
    
  //유저 로그인 정관수
    public int[] login(String userId, String pwd) {
       int result=-1;
       int pCount=0;
      PreparedStatement psmt=null;
      ResultSet rs=null;
      
      try {
         String checkCountSql="select pCount, pUpdatedate from userPwd where userId=?";
         psmt=conn.prepareStatement(checkCountSql);
         psmt.setString(1, userId);
         rs=psmt.executeQuery();
         Timestamp pUpdatedate=null;
         boolean canLogin=false;
   
         Timestamp currentTime= new Timestamp(System.currentTimeMillis());
         
         while(rs.next()) {
            pCount =rs.getInt("pCount");
            pUpdatedate=rs.getTimestamp("pUpdatedate");
         }
         
         // pUpdatedate
         if(pUpdatedate!=null&&currentTime.before(pUpdatedate)) {
            System.out.println("5회 이상 비밀번호 틀렸으므로 하루동안 로그인이 불가");
         } else {
            
            // pCount
            if(pCount>=5) {
               System.out.println(pCount+"이상 비밀번호 틀렸으므로 하루동안 로그인이 불가");
               
            } else if (pCount==3){
               System.out.println(pCount+"비밀번호틀림, 비밀번호 바꾸실래요");
               canLogin=true;
            } else {
               canLogin=true;
            }
            
            // 로그인
            if(canLogin) {
               String checkUserSql="select pwd from userPwd where userId=?";
               psmt=conn.prepareStatement(checkUserSql);
               psmt.setString(1, userId);
               rs=psmt.executeQuery();
               while(rs.next()) {
                  String storedPwd = rs.getString("pwd");
                  
                  if(storedPwd!=null&&storedPwd.equals(pwd)) {
                     result=1;
                     pCount=0;
                  } else {
                     result=0;
                     pCount=pCount+1;
                  }
               }
            }
            
            // pUpdatedate 변경
            if(pCount>=5) {
               long changeTime=System.currentTimeMillis();
               int day=60*60*24;
               Timestamp timestamp=new Timestamp(changeTime);
               Calendar cal= Calendar.getInstance();
               cal.setTimeInMillis(timestamp.getTime());
               cal.add(Calendar.SECOND, day);
               pUpdatedate=new Timestamp(cal.getTime().getTime());
            }      
            
            String updatePwdSql="update userPwd set pCount="+pCount+", pUpdatedate='"+pUpdatedate+"' where userId=?";
            psmt=conn.prepareStatement(updatePwdSql);
            psmt.setString(1,userId);
            psmt.executeUpdate();
            
         }
      } catch(SQLException e) {
         System.out.println("SQL 에러");
            e.printStackTrace();
      } 
      int[] output= {result, pCount};
      return output;
    } // login end

      
      
      // loginTime을 로그인시 입력
      public void updateLoginY (HttpSession session) {
      	String userId=(String) session.getAttribute("userId");
  		PreparedStatement psmt=null;
  		Timestamp currentTime= new Timestamp(System.currentTimeMillis());
//      	System.out.println(userId);
//  		System.out.println(currentTime);
      	String loginUpdate="update userInfo set loginTime='"+currentTime+"' where userId=?";
  		try {
  			if(userId!=null) {
  				conn = DBConnection.getInstance().getConnection();
  				psmt=conn.prepareStatement(loginUpdate);
  				psmt.setString(1, userId);
  				psmt.executeUpdate();
  			}	else {
  				System.out.println("checkLogin 갱신실패");
  			}
  		} catch(Exception e) {
  			System.out.println("SQL 에러");
              e.printStackTrace();
  		} 
      }  // checkLogin값을 로그인시 입력 끝
      
      // 로그아웃시 loginTime을 null로(제거)
      public void updateLoginN (String userId) {
      	PreparedStatement psmt=null;
      	System.out.println("로그아웃");
      	String loginUpdate="update userInfo set loginTime="+null+" where userId=?";
      	try {
  			psmt=conn.prepareStatement(loginUpdate);
  			psmt.setString(1, userId);
  			psmt.executeUpdate();

  		} catch(Exception e) {
  			System.out.println("SQL 에러");
              e.printStackTrace();
  		} 
      } // 로그아웃시 checkLogin값을 null로(제거) 끝
    
    
  //회원탈퇴
    public void deleteUserData(UserDTO dto) {
       final String sql = "DELETE FROM USERINFO where userid = ?";
           
           try {
              PreparedStatement psmt = conn.prepareStatement(sql);
              psmt.setString(1, dto.getUserId());
              psmt.executeQuery();
             
          } catch (Exception e) {
             e.printStackTrace();
             System.out.println("UserData delete Err");
          }
    }
    
  //회원정보 닉네임 수정
  	public int updateUser(UserDTO dto) {
  		int result = -1;
  		String nicknamesql = " update userinfo set usernickname=? where userid=?";
  		try {
  			PreparedStatement psmt = conn.prepareStatement(nicknamesql);
  			psmt.setString(1, dto.getUserNickName());
  			psmt.setString(2, dto.getUserId());

  			result = psmt.executeUpdate();

  		}
  		catch(Exception e) {
  			System.out.println("회원정보수정중 문제발생");
  			e.printStackTrace();
  		}
	
  		return result;
  	}
    
  //회원정보 패스워드 수정
  	public int updatePwd(UserDTO dto) {
  		int result = -1;
  		String pwdsql = " update userpwd set pwd=? where userid=?";
  		try {
  			PreparedStatement psmt = conn.prepareStatement(pwdsql);
  			psmt.setString(1, dto.getPwd());
  			psmt.setString(2, dto.getUserId());
  			
  			result = psmt.executeUpdate();

  		}
  		catch(Exception e) {
  			System.out.println("비밀번호수정중 문제발생");
  			e.printStackTrace();
  		}

  		return result;
  	}
  	
	//접속중인 회원찾기
  	public List<String> findLogin(){
  		List<String> userList = new ArrayList<>();
  		ResultSet rs=null;
  		String findSql = "select * from (select tb.*, rownum rnum from " 
  					+"(select userNickName from userInfo where loginTime is not null order by loginTime desc ) tb ) where rnum between 1 and 5 ";
  		try {
  			PreparedStatement psmt = conn.prepareStatement(findSql);
  			rs=psmt.executeQuery();
  			while(rs.next()) {
  				String uName = rs.getString("userNickName");
  				userList.add(uName);
  			}
  			
  		}
  		catch(Exception e) {
  			System.out.println("비밀번호수정중 문제발생");
  			e.printStackTrace();
  		}
  		return userList;
  	}
  	
  	
  	// role 체크
  	public String checkRole(String userId) {
  		String result="";
  		ResultSet rs=null;
  		String adminSql="select role from userInfo where userid=?";
  		try {
  			PreparedStatement psmt = conn.prepareStatement(adminSql);
  			psmt.setString(1, userId);
  			rs=psmt.executeQuery();
  			while(rs.next()) {
  				result = rs.getString("role");
  				System.out.println("권한체크"+result);
  			}
  			
  		}
  		catch(Exception e) {
  			System.out.println("권한체크중 문제발생");
  			e.printStackTrace();
  		}
  		return result;
  	}
  
}
