package listener;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

//ServletContextListener:ServletContext의 생성/소멸 이벤트를 감시하는 리스너
@WebListener
public class MyContextListener implements ServletContextListener {
	
	public MyContextListener() {}
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			Context ctx = new InitialContext();
			DataSource source=(DataSource)ctx.lookup(sce.getServletContext().getInitParameter("JNDI-ROOT")+"/ICTUSER");
			sce.getServletContext().setAttribute("DataSource", source);
		}
		catch(NamingException e) {e.printStackTrace();}
	
	}
	
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("웹 어플리케이션이 종료 되었습니다");
	}

	
}
