package com.worldflower.userdto;

import java.sql.Timestamp;


//담당자 배용우
//배용우 작업 :전체한번 복사해주세요
public class UserDTO {
   //userInfo Table
   private String userId;
   private String userNickName;
   private Timestamp userRegdate;
   private Timestamp userUpdatedate;
   private Timestamp loginTime;
   public Timestamp getloginTime() {
      return loginTime;
   }
   public void setloginTime(Timestamp loginTime) {
      this.loginTime = loginTime;
   }
   private String checkResign;
   private String role;
   
   //userPwd Table
   private String pwd;
   private int pcount;
   private Timestamp pupdatedate;
   
   //user
   private String email;
   private Timestamp emailAuthTime;
   private String emailCheck;
   
   
   
   public String getRole() {
      return role;
   }
   public void setRole(String role) {
      this.role = role;
   }
   public Timestamp getEmailAuthTime() {
      return emailAuthTime;
   }
   public void setEmailAuthTime(Timestamp emailAuthTime) {
      this.emailAuthTime = emailAuthTime;
   }
   public String getEmailCheck() {
      return emailCheck;
   }
   public void setEmailCheck(String emailCheck) {
      this.emailCheck = emailCheck;
   }
   public String getUserId() {
      return userId;
   }
   public void setUserId(String userId) {
      this.userId = userId;
   }
   public String getUserNickName() {
      return userNickName;
   }
   public void setUserNickName(String userNickName) {
      this.userNickName = userNickName;
   }
   public Timestamp getUserRegdate() {
      return userRegdate;
   }
   public void setUserRegdate(Timestamp userRegdate) {
      this.userRegdate = userRegdate;
   }
   public Timestamp getUserUpdatedate() {
      return userUpdatedate;
   }
   public void setUserUpdatedate(Timestamp userUpdatedate) {
      this.userUpdatedate = userUpdatedate;
   }

   public String getCheckResign() {
      return checkResign;
   }
   public void setCheckResign(String checkResign) {
      this.checkResign = checkResign;
   }
   public String getPwd() {
      return pwd;
   }
   public void setPwd(String pwd) {
      this.pwd = pwd;
   }
   public int getPcount() {
      return pcount;
   }
   public void setPcount(int pcount) {
      this.pcount = pcount;
   }
   public Timestamp getPupdatedate() {
      return pupdatedate;
   }
   public void setPupdatedate(Timestamp pupdatedate) {
      this.pupdatedate = pupdatedate;
   }
   public String getEmail() {
      return email;
   }
   public void setEmail(String email) {
      this.email = email;
   }
   @Override
   public String toString() {
      return "UserDTO [userId=" + userId + ", userNickName=" + userNickName + ", userRegdate=" + userRegdate
            + ", userUpdatedate=" + userUpdatedate + ", loginTime=" + loginTime + ", checkResign=" + checkResign
            + ", role=" + role + ", pwd=" + pwd + ", pcount=" + pcount + ", pupdatedate=" + pupdatedate + ", email="
            + email + ", emailAuthTime=" + emailAuthTime + ", emailCheck=" + emailCheck + "]";
   }
   
   
   
   


   
   
   
}
