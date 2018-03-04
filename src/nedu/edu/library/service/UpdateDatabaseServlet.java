package nedu.edu.library.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nedu.edu.library.util.DailyTask;

public class UpdateDatabaseServlet extends HttpServlet {

	public UpdateDatabaseServlet() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
	}

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int resCode = DailyTask.dailyTask();
		req.setAttribute("resCode", resCode);
		req.getRequestDispatcher("jsp/admin/resPage.jsp").forward(req, resp);
	}
	
}
