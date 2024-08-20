package com.worldflower.boarddto;

import java.sql.Date;

public class BoardDTO {
   // 게시판에 관한것.
   private String bno;
   private String userId;
   private String title;
   private String content;
   private Date postdate;
   private Date updatedate;
   private int viewcount;
   private String fid;
   private String userNickName;
   
   // 꽃에 관한것
   private String fname;
   private Date fopendate;
   private String fimgname;
   private String fcode;
   private String fimgsrc;
   
   //나라에 관한것
   private String nname;
   private String nimgsrc;
   private String nimgname;
   private String nlocation;
   private String ninfo;
   


   public String getUserNickName() {
      return userNickName;
   }
   public void setUserNickName(String userNickName) {
      this.userNickName = userNickName;
   }
   public String getFname() {
      return fname;
   }
   public void setFname(String fname) {
      this.fname = fname;
   }
   public Date getFopendate() {
      return fopendate;
   }
   public void setFopendate(Date fopendate) {
      this.fopendate = fopendate;
   }
   public String getFimgname() {
      return fimgname;
   }
   public void setFimgname(String fimgname) {
      this.fimgname = fimgname;
   }
   public String getNinfo() {
      return ninfo;
   }
   public void setNinfo(String ninfo) {
      this.ninfo = ninfo;
   }
   public String getBno() {
      return bno;
   }
   public void setBno(String bno) {
      this.bno = bno;
   }
   public String getUserId() {
      return userId;
   }
   public void setUserId(String userId) {
      this.userId = userId;
   }
   public String getTitle() {
      return title;
   }
   public void setTitle(String title) {
      this.title = title;
   }
   public String getContent() {
      return content;
   }
   public void setContent(String content) {
      this.content = content;
   }
   public Date getPostdate() {
      return postdate;
   }
   public void setPostdate(Date postdate) {
      this.postdate = postdate;
   }
   public Date getUpdatedate() {
      return updatedate;
   }
   public void setUpdatedate(Date updatedate) {
      this.updatedate = updatedate;
   }
   public int getViewcount() {
      return viewcount;
   }
   public void setViewcount(int viewcount) {
      this.viewcount = viewcount;
   }
   public String getFid() {
      return fid;
   }
   public void setFid(String fid) {
      this.fid = fid;
   }
   public String getNname() {
      return nname;
   }
   public void setNname(String nname) {
      this.nname = nname;
   }
   public String getFcode() {
      return fcode;
   }
   public void setFcode(String fcode) {
      this.fcode = fcode;
   }
   public String getFimgsrc() {
      return fimgsrc;
   }
   public void setFimgsrc(String fimgsrc) {
      this.fimgsrc = fimgsrc;
   }
   public String getNimgsrc() {
      return nimgsrc;
   }
   public void setNimgsrc(String nimgsrc) {
      this.nimgsrc = nimgsrc;
   }
   public String getNimgname() {
      return nimgname;
   }
   public void setNimgname(String nimgname) {
      this.nimgname = nimgname;
   }
   public String getNlocation() {
      return nlocation;
   }
   public void setNlocation(String nlocation) {
      this.nlocation = nlocation;
   }
   @Override
   public String toString() {
      return "BoardDTO [bno=" + bno + ", userId=" + userId + ", title=" + title + ", content=" + content
            + ", postdate=" + postdate + ", updatedate=" + updatedate + ", viewcount=" + viewcount + ", fid=" + fid
            + ", userNickName=" + userNickName + ", fname=" + fname + ", fopendate=" + fopendate + ", fimgname="
            + fimgname + ", fcode=" + fcode + ", fimgsrc=" + fimgsrc + ", nname=" + nname + ", nimgsrc=" + nimgsrc
            + ", nimgname=" + nimgname + ", nlocation=" + nlocation + ", ninfo=" + ninfo + "]";
   }
   
   
}
