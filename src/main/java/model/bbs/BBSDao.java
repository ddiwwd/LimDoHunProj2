package model.bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import jakarta.servlet.ServletContext;
import model.PagingUtil;
import service.DaoService;

/*
DAO(Data Access Object):데이타에 접근해서 CRUD작업을
                        수행하는 업무처리 로직을 갖고 있는 객체

*/
public class BBSDao implements DaoService<BBSDto> {
	//멤버변수(속성들)
	private Connection conn;
	private ResultSet rs;
	private PreparedStatement psmt;
	//생성자
	public BBSDao(ServletContext context) {
		try {
			//드라이버 로딩]
			Class.forName(context.getInitParameter("ORACLE-DRIVER"));
			/*
			//방법1]ResourceBundle(java.util패키지) API 사용
			//.properties파일은 반드시 클래스 패스인 src/main/java에 위치시키자
			ResourceBundle resource = ResourceBundle.getBundle("/resources/database");
			String username = resource.getString("username");
			String password = resource.getString("password");
			*/
			//방법2]ServletContext의 getResourceAsStream()메소드와 Properties API 사용
			/*InputStream is= context.getResourceAsStream("/resources/database.properties");
			Properties props = new Properties();
			props.load(is);
			String username= props.getProperty("username");
			String password= props.getProperty("password");*/
			
			//데이타베이스 연결]
			//방법1 및 방법2일때
			//conn = DriverManager.getConnection(context.getInitParameter("ORACLE-URL"), username, password);
			
			//커넥션 풀로 변경
			//Context ctx = new InitialContext();
			//DataSource source= (DataSource)ctx.lookup("java:comp/env/jsp");			
			//conn = source.getConnection();
			//컨텍트 리스너(MyContextListener)에 데이타 소스를 얻는 코드 추가후
			DataSource source= (DataSource)context.getAttribute("DataSource");
			conn = source.getConnection();
		}
		catch(Exception e) {e.printStackTrace();}
	}////////////////
	//자원반납용
	@Override
	public void close() {
		try {
			if(rs !=null) rs.close();
			if(psmt !=null) psmt.close();
			if(conn !=null) conn.close();
		}
		catch(SQLException e) {}
		
	}//////////////////
	//전체 목록 가져오기]
	/*
	 * 페이징 로직 추가하기
	 * DAO에서 할일
	 * 1. 전체 목록 쿼리를 구간 쿼리로 변경
	 * 2. 총 레코드수 구하는 메소드 추가	
	 * 3. List.jsp에 페이징관련 코드 추가
	 */
	@Override
	public List<BBSDto> selectList(Map map) {
		List<BBSDto> items = new Vector<>();
		//페이징 적용 前 쿼리- 전체 목록 쿼리
		/*
		String sql="SELECT b.*,username FROM bbs b JOIN member m ON b.username=m.username ";
		//검색시 추가 시작
		if(map.get("searchColumn") !=null) {
			sql+=" WHERE "+map.get("searchColumn") + " LIKE '%"+map.get("searchWord")+"%'";
		}
		sql+= " ORDER BY no DESC";
		*/
		//페이징 적용 쿼리
		String sql="SELECT *"
				+ "		FROM ("
				+ "		  SELECT b.*,"
				+ "				 RANK() OVER (ORDER BY no DESC) AS no_rank"
				+ "		  FROM bbs b JOIN member m ON b.username=m.username ";
				//검색시 추가 시작
				if(map.get("searchColumn") !=null) {
					sql+=" AND "+map.get("searchColumn") + " LIKE '%"+map.get("searchWord")+"%'";
				}
				sql+= ") "
				+ "		WHERE no_rank BETWEEN ? AND ? ";
		
	
		try {
			psmt = conn.prepareStatement(sql);
			//페이징을 위한 시작 및 종료 Rank설정
			psmt.setString(1, map.get(PagingUtil.START).toString());
			psmt.setString(2, map.get(PagingUtil.END).toString());
			rs=psmt.executeQuery();
			while(rs.next()) {
				BBSDto dto = new BBSDto();
				dto.setContent(rs.getString(4));
				dto.setVisitcount(rs.getString(5));
				dto.setUsername(rs.getString(2));
				dto.setNo(rs.getString(1));
				dto.setPostDate(rs.getDate(6));
				dto.setTitle(rs.getString(3));
				
				items.add(dto);
			}
		}
		catch(SQLException e) {e.printStackTrace();}
		return items;
	}//////////////////////
	//상세보기용-레코드 하나 조회
	@Override
	public BBSDto selectOne(String... params) {
		BBSDto dto=null;
		try {
			
			//목록에서 넘어온 경우에만 조회수 증가
			
				psmt = conn.prepareStatement("UPDATE bbs SET  visitcount=visitcount+1 WHERE no=?");
				psmt .setString(1, params[0]);
				psmt.executeUpdate();
			
			String sql="SELECT b.* FROM bbs b JOIN member m ON b.username=m.username WHERE no=?";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, params[0]);
			rs = psmt.executeQuery();
			if(rs.next()) {
				dto = new BBSDto();
				dto.setContent(rs.getString(4));
				dto.setVisitcount(rs.getString(5));
				dto.setUsername(rs.getString(2));
				dto.setNo(rs.getString(1));
				dto.setPostDate(rs.getDate(6));
				dto.setTitle(rs.getString(3));

			}
		}
		catch(SQLException e) {e.printStackTrace();}
		
		return dto;
	}///////////////////////////
	//이전글/다음글 조회
	public Map<String,BBSDto> prevNext(String currentNo){
		Map<String,BBSDto> map = new HashMap<>();
		try {
			//이전글 얻기]
			String sql="SELECT NO,TITLE FROM bbs WHERE NO=(SELECT MIN(NO) FROM bbs WHERE no > ?)";
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, currentNo);
			rs = psmt.executeQuery();
			if(rs.next()) {
				map.put("PREV", new BBSDto(rs.getString(1), null, rs.getString(2), null,null, null));
			}
			//다음글 얻기]
			sql="SELECT NO,TITLE FROM bbs WHERE NO=(SELECT MAX(NO) FROM bbs WHERE no < ?)";
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, currentNo);
			rs = psmt.executeQuery();
			if(rs.next()) {
				map.put("NEXT", new BBSDto(rs.getString(1), null, rs.getString(2), null,null, null));
			}
		}
		catch(SQLException e) {e.printStackTrace();}
		return map;
	}///////////
	//총 레코드 수 얻기용
	@Override
	public int getTotalRecordCount(Map map) {
		int totalCount=0;
		String sql="SELECT COUNT(*) FROM bbs b JOIN member m ON b.username=m.username ";
		//검색시 추가 시작
		if(map != null && map.get("searchColumn") !=null) {
			sql+=" WHERE "+map.get("searchColumn") + " LIKE '%"+map.get("searchWord")+"%'";
		}
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			rs.next();
			totalCount= rs.getInt(1);
		}
		catch(SQLException e) {e.printStackTrace();}
		return totalCount;
	}
	//입력용
	@Override
	public int insert(BBSDto dto) {
		int affected=0;
		try {
			String sql="INSERT INTO bbs VALUES(SEQ_BBS.NEXTVAL,?,?,?, DEFAULT, DEFAULT)";
			psmt = conn.prepareStatement(sql,new String[] {"no"});
			psmt.setString(1, dto.getUsername());
			psmt.setString(2, dto.getTitle());
			psmt.setString(3, dto.getContent());
			affected = psmt.executeUpdate();
			//입력된 행의 키값 가져오기	
			rs= psmt.getGeneratedKeys();
			if(rs.next()) {
				System.out.println("방금 입력된 행의 키값:"+rs.getLong(1));
			}
		}
		catch(SQLException e) {e.printStackTrace();}
		return affected;
	}//////////////////////////
	@Override
	public int update(BBSDto dto) {
		int affected=0;
		String sql="UPDATE bbs SET title=?,content=? WHERE no=?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(3, dto.getNo());
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			affected = psmt.executeUpdate();
			
		}
		catch(SQLException e) {e.printStackTrace();}
		return affected;
	}
	//삭제용
	@Override
	public int delete(BBSDto dto) {
		int affected=0;
		String sql="DELETE bbs WHERE no=?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getNo());			
			affected = psmt.executeUpdate();
			
		}
		catch(SQLException e) {e.printStackTrace();}
		return affected;
	}///////////
	
	
	
	//회원여부 판단용
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
	}/////////////
	public BBSDto getBBSByTitle(String title) {
        BBSDto dto = null;
        String sql = "SELECT * FROM bbs WHERE title = ?";

        try {
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1, title);
            ResultSet rs = psmt.executeQuery();

            if (rs.next()) {
                dto = new BBSDto();
                dto.setNo(rs.getString("no"));
                dto.setUsername(rs.getString("username"));
                dto.setTitle(rs.getString("title"));
                dto.setContent(rs.getString("content"));
                dto.setVisitcount(rs.getString("visitcount"));
                dto.setPostDate(rs.getDate("post_date"));
            }

            rs.close();
            psmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dto;
    }
	/*
	// 페이지 목록에 저장된 값 가져오기
    public List<String> getPageList() {
        List<String> pageList = new ArrayList<>();

        try {
            // 예시: 페이지 목록에 저장된 값을 가져오는 쿼리
            String sql = "SELECT DISTINCT page_name FROM page_table"; // 페이지 목록이 저장된 테이블 및 필드명으로 변경

            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();

            while (rs.next()) {
                // 페이지 목록에서 page_name 필드의 값 가져와서 리스트에 추가
                pageList.add(rs.getString("page_name")); // 페이지 목록에 저장된 필드명으로 변경
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pageList;
    } */
	@Override
	public int Mdelete(String username) {
		int affected=0;
		String sql="DELETE bbs WHERE username=?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, username);			
			affected = psmt.executeUpdate();
			
		}
		catch(SQLException e) {e.printStackTrace();}
		return affected;
	}
    
}
	

