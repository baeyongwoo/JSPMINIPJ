package com.worldflower.boarddao;

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

import com.worldflower.boarddto.BoardDTO;
import com.worldflower.common.DBConnection;
import com.worldflower.userdto.UserDTO;

public class NationDAO {
    private Connection conn;

    public NationDAO() {
        try {
            this.conn = DBConnection.getInstance().getConnection();
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }
    }

  
    public List<BoardDTO> getNationList(){
        final String sql = "select * from nationinfo";
          List<BoardDTO> nationList = new ArrayList<>();
          try (PreparedStatement psmt = conn.prepareStatement(sql);
             
               ResultSet rs = psmt.executeQuery()) {
              while (rs.next()) {
                 BoardDTO dto = new BoardDTO();
                 
                 dto.setNname(rs.getString(1));
                 dto.setNimgname(rs.getString(3));
                 dto.setNlocation(rs.getString(4));
                 dto.setNinfo(rs.getString(5));
                  
                  nationList.add(dto);
              }
          } catch (SQLException e) {
              System.out.println("SQL 에러");
              e.printStackTrace();
          }

      
          return nationList;
    }
    
    public BoardDTO selectNation(BoardDTO dto) {
        final String sql = "select * from nationinfo where nname = ?";
        try {
          PreparedStatement psmt = conn.prepareStatement(sql);
          psmt.setString(1, dto.getNname());
          ResultSet rs = psmt.executeQuery();
          
          
          if(rs.next()) {
             dto.setNname(rs.getString(1));
             dto.setNimgsrc(rs.getString(2));
             dto.setNimgname(rs.getString(3));
             dto.setNlocation(rs.getString(4));
             dto.setNinfo(rs.getString(5));
            
          }
          
          
       } catch (Exception e) {
          System.out.println("selectUser sql Err");
          e.printStackTrace();
          
       }
        return dto;
        
     }//selectUser end
    
    public void inserNationInfo(BoardDTO dto) {
        final String sql = "INSERT INTO nationinfo "
              + " values(?, ?, ?, ?, ?)";
        
        try {
           PreparedStatement psmt = conn.prepareStatement(sql);
           if(dto.getNimgname() == null && dto.getNimgsrc() == null) {
               psmt.setString(2, "null");
               psmt.setString(3, "해당이미지없음");
           }else {
        	   psmt.setString(2, dto.getNimgsrc());
               psmt.setString(3, dto.getNimgname());
           }
          psmt.setString(1, dto.getNname());
          psmt.setString(4, dto.getNlocation());
          psmt.setString(5, dto.getNinfo());
          
          psmt.executeUpdate();
          
          
       } catch (Exception e) {
          e.printStackTrace();
          System.out.println("nation Inster  Err");
       }
        
        
     }//insertNation info end
    
    
    
    
}
