package model.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.sql.DataSource;

import jakarta.servlet.ServletContext;
import model.PagingUtil;
import service.DaoService;

public class MemberDao implements DaoService<MemberDto> {
	
	//멤버변수(속성들)
	private Connection conn;
	private ResultSet rs;
	private PreparedStatement psmt;
	private final String DATA_SOURCE="DataSource";
	//생성자
	public MemberDao(ServletContext context) {
		try {
			//커넥션 풀 사용.즉 커넥션 풀에서 커넥션 객체 가져오기
			//(리스너에서 컨텍스트 영역에 저장한 데이타소스 가져오기)
			DataSource source=(DataSource)context.getAttribute(DATA_SOURCE);
			conn=source.getConnection();
		}
		catch(SQLException e) {e.printStackTrace();}
	}//////////////
	//자원반납용-사용한 커넥션 객체를 다시 풀에 반납
	@Override
	public void close() {
		try {
			//메모리 해제
			if(rs !=null) rs.close();
			if(psmt !=null) psmt.close();
			//커넥션 풀에 커넥션 객체 반납-메모리 해제 아님]
			if(conn !=null) conn.close();
		}
		catch(SQLException e) {}
		
	}/////////////
	//전체 목록용]
	/*
	 * 페이징 순서
	 * 1. 전체 목록용 쿼리를 구간쿼리로 변경
	 * 2. 전체 레코드수 얻기용 메소드 추가
	 * 3. 페이징 로직을 리스트 컨트롤러에 추가
	 * 4. 리스트.JSP페이지에 결과값 출력
	 */
	
	@Override
	public List<MemberDto> selectList(Map map) {
		List<MemberDto> records= new Vector<>();
		//페이징 적용 전 쿼리
		
		String sql="SELECT *"
				+ "		FROM ("
				+ "		  SELECT D.*,"
				+ "				 RANK() OVER (ORDER BY no DESC) AS no_rank"
				+ "		  FROM myproject D "				
				+ ") "
				+ " WHERE no_rank BETWEEN ? AND ? ";
		
		try {
			psmt = conn.prepareStatement(sql);
			//페이징을 위한 시작 및 종료 Rank설정
			psmt.setString(1, map.get(PagingUtil.START).toString());
			psmt.setString(2, map.get(PagingUtil.END).toString());
			rs=psmt.executeQuery();
			while(rs.next()) {
				MemberDto dto = new MemberDto();
				dto.setSelfintroduce(rs.getString(6));
				dto.setEducation(rs.getString(5));
				dto.setGender(rs.getNString(3));
				dto.setPassword(rs.getString(2));
				dto.setUsername(rs.getString(1));
				dto.setPassword(rs.getString(3));
				dto.setRegidate(rs.getDate(7));
				dto.setInters(rs.getString(4));
				records.add(dto);
			}
		}
		catch(SQLException e) {e.printStackTrace();}
		return records;
	}///////////////////////////////////////
	
	

	@Override
	public MemberDto selectOne(String... params) {
		MemberDto dto=null;
		try {
			String sql="SELECT * FROM member WHERE username=?";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, params[0]);
			rs= psmt.executeQuery();
			if(rs.next()) {
				dto=new MemberDto();
				dto.setSelfintroduce(rs.getString(6));
				dto.setEducation(rs.getString(5));
				//dto.setRegidate(rs.getString(7));
				dto.setPassword(rs.getString(2));
				dto.setUsername(rs.getString(1));
				dto.setGender(rs.getString(3));
				dto.setInters(rs.getString(4));
			}
		}
		catch(SQLException e) {e.printStackTrace();}
		return dto;
	}/////////////////////

	@Override
	public int getTotalRecordCount(Map map) {
		int totalCount=0;
		String sql="SELECT COUNT(*) FROM member ";
		
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			rs.next();
			totalCount= rs.getInt(1);
		}
		catch(SQLException e) {e.printStackTrace();}
		return totalCount;
	}

	@Override
	public int insert(MemberDto dto) {
		int affected=0;
		String sql="INSERT INTO MEMBER VALUES(?,?,?,?,?,?,DEFAULT)";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getUsername());
			psmt.setString(2, dto.getPassword());
			psmt.setString(3, dto.getGender());
			psmt.setString(4, dto.getInters());
			psmt.setString(5, dto.getEducation());			
			psmt.setString(6, dto.getSelfintroduce());			
			affected=psmt.executeUpdate(); 
		}
		catch(SQLException e) {e.printStackTrace();}		
		return affected;
	}/////

	@Override
	public int update(MemberDto dto) {
		int affected=0;
		String sql="UPDATE Member SET password=?,gender=?,inters=?,education=?,selfintroduce=? WHERE username=?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getPassword());
			psmt.setString(2, dto.getGender());
			psmt.setString(3, dto.getInters());
			psmt.setString(4, dto.getEducation());	
			psmt.setString(5, dto.getSelfintroduce());
			psmt.setString(6, dto.getUsername());
			affected=psmt.executeUpdate(); 
		}
		catch(SQLException e) {e.printStackTrace();}		
		return affected;
	}
	//삭제용]

	public int Mdelete(String username) {
		int affected=0;
		String sql="DELETE Member WHERE username=?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, username);			
			affected=psmt.executeUpdate(); 
		}
		catch(SQLException e) {e.printStackTrace();}		
		return affected;
	}
	//회원 확인용]
	public boolean isMember(String username,String password) {
		String sql="SELECT COUNT(*) FROM member WHERE username=? AND password=?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, username);
			psmt.setString(2, password);
			rs = psmt.executeQuery();
			rs.next();
			if(rs.getInt(1)==0) return false;
		}
		catch(SQLException e) {e.printStackTrace();return false;}
		return true;
	}
	public void updateDownCount(String username) {
		
		String sql="UPDATE myproject SET visitcount=visitcount+1 WHERE username=?";
		try {
			psmt = conn.prepareStatement(sql);			
			psmt.setString(1, username);	
			psmt.executeUpdate(); 
		}
		catch(SQLException e) {e.printStackTrace();}	
		
		
	}
	@Override
	public int delete(MemberDto dto) {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
