package nedu.edu.library.service;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nedu.edu.library.dao.impl.AdviseInfoImpl;
import nedu.edu.library.entity.AdviseInfo;

public class AdviseInfoServlet extends HttpServlet {

	public AdviseInfoServlet() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//http://localhost:8080/NEDULibrary/AdviseInfoServlet?u_id=5&advise=66666&email=666@qq
		//http://localhost:8080/NEDULibrary/AdviseInfoServlet?u_id=1&way=1&pageNow=1
		doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		//1.获取u_id 判断类型
		int u_id = Integer.parseInt(req.getParameter("u_id"));
		
		AdviseInfoImpl adviseInfoImpl = new AdviseInfoImpl();
		
		if (u_id == 1) {
			//u_id = 1为管理员 则为查看建议信息
			int way = Integer.parseInt(req.getParameter("way"));
			boolean check = false;
			check = Boolean.parseBoolean(req.getParameter("check"));
			//way=1 全表搜索 way=2 搜索未查看 way=3 搜索已查看
			if (!check) {
				int pageNow = Integer.parseInt(req.getParameter("pageNow"));
				int pageSize = 2;
				
				ArrayList<AdviseInfo> getList = adviseInfoImpl.getAdviseInfoList(way);
				ArrayList<AdviseInfo> adviseList = new ArrayList<AdviseInfo>();
				int itemNum = getList.size();
				//System.out.println(pageNow);
				int pageNum = (itemNum + pageSize - 1) / pageSize;
				for (int i = (pageNow - 1) * pageSize; i < pageNow * pageSize && i < itemNum; i++) {
					adviseList.add(getList.get(i));
				}
				
				req.setAttribute("adviseList", adviseList);
				req.setAttribute("pageNum", pageNum);
				req.setAttribute("pageNow", pageNow);
				req.setAttribute("way", way);
				req.getRequestDispatcher("jsp/admin/adviseinfoList.jsp").forward(req, resp);
			} else {
				int id = Integer.parseInt(req.getParameter("id"));
				int pageNow = Integer.parseInt(req.getParameter("pageNow"));
				adviseInfoImpl.updateAdviseInfo(id);
				//刷新数据
				req.getRequestDispatcher("AdviseInfoServlet?u_id=1&way=" + way + "&pageNow=" + pageNow + "&check=false").forward(req, resp);
			}
		} else {
			//判断是否插入成功
			boolean flag = true;
			//u_id != 1 则为插入建议信息
			String advise = req.getParameter("advise");
			String email = req.getParameter("email");
			
			AdviseInfo adviseInfo = new AdviseInfo();
			adviseInfo.setU_id(u_id);
			adviseInfo.setAdvise(advise);
			adviseInfo.setEmail(email);
			
			flag = adviseInfoImpl.addAdviseInfo(adviseInfo);
			
			resp.getOutputStream().write(String.valueOf(flag).toString().getBytes());	
		}
		
		
	}
	
	

}
