package controller.myproject;

import java.awt.ItemSelectable;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.PagingUtil;
import model.bbs.BBSDao;
import model.bbs.BBSDto;
import model.member.MemberDao;
import model.member.MemberDto;

//1]사용자 요청을 받을 수 있도록 서블릿 클래스로 만들기(HttpServlet상속)즉 컨트롤러로 만들기
@WebServlet(urlPatterns = "/myproject/List.ict",initParams = {@WebInitParam(name = "PAGE-SIZE",value = "10" ),@WebInitParam(name = "BLOCK-PAGE",value = "10" )})
public class ListController extends HttpServlet {
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//가]사용자 요청을 받는다	
		//나]요청을 분석한다.
		//다]모델에서 필요한 로직 호출해서 결과값이 있으면 받기 
		//String username = req.getParameter("username");
		
		BBSDao dao = new BBSDao(getServletContext());
		
		Map map = new HashMap();
		PagingUtil.setMapForPaging(map, dao, req, this);
		int totalRecordCount=Integer.parseInt(map.get(PagingUtil.TOTAL_RECORD_COUNT).toString());
		int pageSize=Integer.parseInt(map.get(PagingUtil.PAGE_SIZE).toString());
		int blockPage=Integer.parseInt(map.get(PagingUtil.BLOCK_PAGE).toString());
		int nowPage=Integer.parseInt(map.get(PagingUtil.NOWPAGE).toString());
		int totalPage=Integer.parseInt(map.get(PagingUtil.TOTAL_PAGE).toString());
		String pagingString=PagingUtil.pagingBootStrapStyle(totalRecordCount, pageSize, blockPage, nowPage, req.getContextPath()+"/myproject/List.ict?");
		 
		

		String total=Integer.toString(totalRecordCount- (((nowPage - 1) * pageSize)));
		
		//dto.setUsername(username);

		List<BBSDto> records= dao.selectList(map);
		dao.close();
		
		//라]결과값이 있으면 리퀘스트 영역에 저장
		req.setAttribute("records", records);
		req.setAttribute("paging", pagingString);
		req.setAttribute("nowPage", nowPage);
		req.setAttribute("pageSize", pageSize);
		req.setAttribute("total", total);
		//HttpSession session = req.getSession(true);
		//if (session != null) {
		//    session.setAttribute("username", username);
		//}
		//마]결과값을 뿌려줄 뷰(JSP페이지) 선택후 포워딩 
		//뷰선택]
		RequestDispatcher dispatcher= req.getRequestDispatcher("/project/ListPage.jsp");
		//포워딩]
		dispatcher.forward(req, resp);
		

	}
}
