package controller.myproject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.bbs.BBSDao;
import model.bbs.BBSDto;
import model.member.MemberDao;
import model.member.MemberDto;

@WebServlet("/myproject/ViewOK.ict")
public class ViewOKController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //3]요청분석
        String no = req.getParameter("no");

        BBSDao dao = new BBSDao(getServletContext());
        BBSDto dto = dao.selectOne(no);
        
        dao.close();
        // 줄바꿈 처리
      
        dto.setContent(dto.getContent().replace("\r\n", "<br/>"));
        //5]필요한 값 리퀘스트 영역에 저장
        req.setAttribute("records", dto); // Send the records list to JSP
        //6]뷰 선택후 포워딩
        req.getRequestDispatcher("/project/View.jsp").forward(req, resp);
    }//////////////
}
