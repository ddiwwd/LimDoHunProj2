package model.bbs;

import java.sql.Date;

/*
DTO(Data Tranfer Object):데이타를 전송하는 객체로
                         테이블의 레코드 하나를 저장할 수 
                         있는 자료구조


*/
public class BBSDto {	
	private String no;
	private String username;
	private String title;
	private String content;
	private String visitcount;
	private Date postDate;

	
	
	//생성자]
	public BBSDto() {}	
	public BBSDto(String no, String username, String title, String content, String visitcount, Date postDate) {
		this.no = no;
		this.username = username;
		this.title = title;
		this.content = content;
		this.visitcount = visitcount;
		this.postDate = postDate;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public String getVisitcount() {
		return visitcount;
	}
	public void setVisitcount(String visitcount) {
		this.visitcount = visitcount;
	}
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	@Override
	public String toString() {
		return "BBSDto [no=" + no + ", username=" + username + ", title=" + title + ", content=" + content
				+ ", visitcount=" + visitcount + ", postDate=" + postDate + "]";
	}
	public void setPassword(String password) {
		// TODO Auto-generated method stub
		
	}

	
	
	
}
